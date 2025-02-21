package com.dika.user_management.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberResponseDTO {

    private Long id;

    private String name;

    private String position;

    private ReportsToResponseDTO reportsTo;

}
