package com.dika.user_management.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberRequestDTO {

    private String name;

    private String position;

    private Long reportsToId;

    private Long organizationId;

    private MultipartFile image;

}


