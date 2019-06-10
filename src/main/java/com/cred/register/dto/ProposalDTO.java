package com.cred.register.dto;

import com.cred.register.model.entity.Client;
import com.cred.register.model.entity.Proposal;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProposalDTO {

    private String name;
    private Integer age;
    private String gender;
    private String maritalStatus;
    private String uf;
    private Integer dependents;
    private BigDecimal income;
    private Boolean approved;
    private String limit;

    public Proposal toProposal(){
        return Proposal.builder()
                .creditLimit(this.limit)
                .approved(this.approved)
                .build();
    }

    public ProposalDTO (Client client){
        this.name = client.getName();
        this.age = client.getAge();
        this.gender = client.getGender();
        this.maritalStatus = client.getMaritalStatus();
        this.uf = client.getState().getInitials();
        this.dependents = client.getDependents();
        this.income = client.getIncome();
        this.approved = client.getProposal().getApproved();
        this.limit = client.getProposal().getCreditLimit();
    }
}
