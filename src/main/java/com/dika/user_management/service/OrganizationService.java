package com.dika.user_management.service;


import com.dika.user_management.dto.OrganizationRequestDTO;
import com.dika.user_management.entity.Organization;
import com.dika.user_management.repository.OrganizationRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrganizationService {

    @Autowired
    OrganizationRepository organizationRepository;

    public List<Organization> get(){
        return this.organizationRepository.findAll();
    }

    public Organization create(OrganizationRequestDTO dto){
        Organization organization = Organization.builder()
                .name(dto.getName())
                .build();

        return this.organizationRepository.save(organization);
    }

    @Cacheable(value = "organization", key = "#id")
    public Organization getById(Long id){
        Organization existOrganization = this.organizationRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("no organization with id " + id)
        );

        return existOrganization;
    }

    public Organization update(Long id, OrganizationRequestDTO dto){

        Organization existOrganization = this.getById(id);

        existOrganization.setName(dto.getName());

        return this.organizationRepository.save(existOrganization);
    }

    @Cacheable(value = "organization", key = "#id")
    public void delete(Long id){
        Organization existOrganization = this.getById(id);

        this.organizationRepository.delete(existOrganization);
    }
}


