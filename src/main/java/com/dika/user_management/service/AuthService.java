package com.dika.user_management.service;


import com.dika.user_management.dto.SignInDTO;
import com.dika.user_management.dto.SignUpDTO;
import com.dika.user_management.entity.Member;
import com.dika.user_management.entity.Organization;
import com.dika.user_management.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private MemberService memberService;

    public void signUp(SignUpDTO dto) throws IOException {
        Optional<Member> optionalMember = this.memberRepository.findByEmail(dto.getEmail());

        if(optionalMember.isPresent()){
            throw new EntityNotFoundException("There is already member with email " + dto.getEmail());
        }


        Organization existOrganization = this.organizationService.getById(dto.getOrganizationId());

        Member member = Member.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .position(dto.getPosition())
                .password(dto.getPassword())
                .organization(existOrganization)
                .build();

        if(!Objects.isNull(dto.getReportsTo())){
            Member reportsTo = this.memberService.getById(dto.getReportsTo());
            member.setReportsTo(reportsTo);
        }

        this.memberRepository.save(member);
    }

    public Long signIn(SignInDTO dto){
        Member existMember = this.memberRepository.findByEmail(dto.getEmail()).orElseThrow(
                () -> new EntityNotFoundException("no user with email " + dto.getEmail())
        );

        if(!Objects.equals(dto.getPassword(), existMember.getPassword())){
            throw new EntityNotFoundException("wrong password");
        }

        return existMember.getId();
    }

}

