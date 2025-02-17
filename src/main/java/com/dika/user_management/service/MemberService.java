package com.dika.user_management.service;


import com.dika.user_management.dto.MemberRequestDTO;
import com.dika.user_management.entity.Member;
import com.dika.user_management.entity.Organization;
import com.dika.user_management.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class MemberService {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    OrganizationService organizationService;

    public List<Member> get(){
        return this.memberRepository.findAll();
    }


    public Member create(MemberRequestDTO dto) throws IOException {
        Organization existOrganization = this.organizationService.getById(dto.getOrganizationId());

        Member member = Member.builder()
                .name(dto.getName())
                .position(dto.getPosition())
                .organization(existOrganization)
                .build();

        if(!Objects.isNull(dto.getReportsToId())){
            Member reportsTo = this.getById(dto.getReportsToId());
            member.setReportsTo(reportsTo);
        }

        return this.memberRepository.save(member);
    }

    @Cacheable(value = "member", key = "#id")
    public Member getById(Long id){
        Member existMember = this.memberRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("no member with id " + id)
        );

        return existMember;
    }

    public List<Member> getAllByNameContains(String name){
        List<Member> byNameContains = this.memberRepository.findByNameContains(name);

        return byNameContains;
    }

    public Optional<Member> getReportsToById(Long id){
        return this.memberRepository.findById(id);
    }


    public Member update(Long id, MemberRequestDTO dto){
        Member existMember = this.getById(id);

        Organization existOrganization = this.organizationService.getById(dto.getOrganizationId());

        existMember.setName(dto.getName());
        existMember.setPosition(dto.getPosition());
        existMember.setOrganization(existOrganization);

        if(!Objects.isNull(dto.getReportsToId())){
            Member reportsTo = this.getById(dto.getReportsToId());
            existMember.setReportsTo(reportsTo);
        }

        return this.memberRepository.save(existMember);
    }

    @CacheEvict(value = "member", key = "#id")
    public void delete(Long id){
        Member existMember = this.getById(id);

        this.memberRepository.delete(existMember);
    }

}





