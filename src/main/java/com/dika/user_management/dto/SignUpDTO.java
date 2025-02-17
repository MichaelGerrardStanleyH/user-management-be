package com.dika.user_management.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignUpDTO {

    private String name;

    private String email;

    private String password;

    private String position;

    private Long organizationId;

    private Long reportsTo;

}
