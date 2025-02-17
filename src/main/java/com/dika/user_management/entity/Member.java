package com.dika.user_management.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "member")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Member implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String password;


    private String position;

    @ManyToOne
    private Member reportsTo;

    @ManyToOne
    @JoinColumn(name = "organizationId")
    private Organization organization;

}

