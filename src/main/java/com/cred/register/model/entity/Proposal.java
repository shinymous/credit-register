package com.cred.register.model.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "proposal")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Proposal {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private Boolean approved;
    private String creditLimit;
}
