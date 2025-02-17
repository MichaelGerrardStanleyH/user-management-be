package com.dika.user_management.controller;


import com.dika.user_management.dto.MemberRequestDTO;
import com.dika.user_management.dto.MemberResponseDTO;
import com.dika.user_management.entity.Member;
import com.dika.user_management.mapper.TransactionMapper;
import com.dika.user_management.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/members")
public class MemberController {

    @Autowired
    MemberService memberService;

    @GetMapping
    public ResponseEntity<?> getAllMember(){
        return ResponseEntity.status(HttpStatus.OK).body(
                TransactionMapper.mapEntityListToDtoList(this.memberService.get(), MemberResponseDTO.class)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMemberById(@PathVariable("id") Long id){
        return ResponseEntity.status(HttpStatus.OK).body(
                TransactionMapper.mapEntityToDto(this.memberService.getById(id), MemberResponseDTO.class)
        );
    }

    @PostMapping
    public ResponseEntity<?> createMember(@RequestBody MemberRequestDTO dto) throws IOException {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                TransactionMapper.mapEntityToDto(this.memberService.create(dto), MemberResponseDTO.class)
        );
    }

    @PostMapping(value = "/form")
    public ResponseEntity<?> createMemberWithForm(
            @RequestParam("name") String name,
            @RequestParam("position") String position,
            @RequestParam(value = "reportsToId", required = false) Long reportsToId
            ) throws IOException {

        MemberRequestDTO dto = MemberRequestDTO.builder()
                .name(name)
                .position(position)
                .organizationId(1L)
                .build();

        if(!Objects.isNull(reportsToId)){
            dto.setReportsToId(reportsToId);
        }




        Member member = this.memberService.create(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                TransactionMapper.mapEntityToDto(member, MemberResponseDTO.class)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateMember(@PathVariable("id") Long id, @RequestBody MemberRequestDTO dto){
        return ResponseEntity.status(HttpStatus.OK).body(
                TransactionMapper.mapEntityToDto(this.memberService.update(id, dto), MemberResponseDTO.class)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMember(@PathVariable("id") Long id){
        this.memberService.delete(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/{name}/search")
    public ResponseEntity<?> getMemberById(@PathVariable("name") String name){

        List<Member> allByNameContains = this.memberService.getAllByNameContains(name);

        return ResponseEntity.status(HttpStatus.OK).body(
                TransactionMapper.mapEntityListToDtoList(allByNameContains, MemberResponseDTO.class)
        );
    }

}


