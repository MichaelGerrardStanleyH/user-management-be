package com.dika.user_management.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class OrganizationResponseDTO {

    private Long id;

    private String name;

    private List<MemberResponseDTO> members;

}
