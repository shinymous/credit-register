package com.cred.register.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "client")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Client {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String cpf;
    private Integer age;
    private String gender;
    private String maritalStatus;
    private Integer dependents;
    private BigDecimal income;
    @JsonIgnore
    @JoinColumn(name = "state", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private State state;
    @JsonIgnore
    @JoinColumn(name = "proposal", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Proposal proposal;

}
