package com.dika.user_management.controller;


import com.dika.user_management.dto.MemberResponseDTO;
import com.dika.user_management.dto.OrganizationRequestDTO;
import com.dika.user_management.dto.OrganizationResponseDTO;
import com.dika.user_management.dto.ReportsToResponseDTO;
import com.dika.user_management.entity.Organization;
import com.dika.user_management.mapper.TransactionMapper;
import com.dika.user_management.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("organizations")
public class OrganizationController {

    @Autowired
    OrganizationService organizationService;

    @GetMapping
    public ResponseEntity<?> getAllOrganization(){
        List<Organization> organizations = this.organizationService.get();
        List<OrganizationResponseDTO> organizationResponseDTOS = TransactionMapper.mapEntityListToDtoList(organizations, OrganizationResponseDTO.class);

        return ResponseEntity.status(HttpStatus.OK).body(
               organizationResponseDTOS
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrganizationById(@PathVariable("id") Long id){

        Organization organization = this.organizationService.getById(id);

        List<MemberResponseDTO> list = organization.getMembers().stream().map(member -> new MemberResponseDTO(
                member.getId(), member.getName(), member.getPosition(),
                new ReportsToResponseDTO(
                        member.getReportsTo().getId(),
                        member.getReportsTo().getName(),
                        member.getReportsTo().getPosition()
                )
        )).toList();

        OrganizationResponseDTO organizationResponseDTO = new OrganizationResponseDTO(
                organization.getId(), organization.getName(), list
        );

        return ResponseEntity.status(HttpStatus.OK).body(
                organizationResponseDTO
        );
    }

    @PostMapping
    public ResponseEntity<?> createOrganization(@RequestBody OrganizationRequestDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(
                TransactionMapper.mapEntityToDto(this.organizationService.create(dto), OrganizationResponseDTO.class)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrganization(@PathVariable("id") Long id, @RequestBody OrganizationRequestDTO dto){
        return ResponseEntity.status(HttpStatus.OK).body(
                TransactionMapper
                        .mapEntityToDto(this.organizationService.update(id, dto), OrganizationResponseDTO.class)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrganization(@PathVariable("id") Long id){
        this.organizationService.delete(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}



