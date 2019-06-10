package com.cred.register.dto;

import com.cred.register.model.entity.Client;
import com.cred.register.model.map.GenderMap;
import com.cred.register.model.map.MaritalStatusMap;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientDTO {

    @NotNull(message = "name.is.required")
    private String name;
    @NotNull(message = "cpf.is.required")
    private String cpf;
    @NotNull(message = "age.is.required")
    private Integer age;
    @NotNull(message = "gender.is.required")
    private GenderMap gender;
    @NotNull(message = "marital.status.is.required")
    private MaritalStatusMap maritalStatus;
    @Min(value = 0, message = "dependents.can.not.be.negative")
    private Integer dependents;
    @NotNull(message = "income.is.required")
    private BigDecimal income;
    private String state;

    public Client toClient(){
        return Client.builder()
                .name(this.name)
                .cpf(this.cpf)
                .age(this.age)
                .gender(this.gender.name())
                .maritalStatus(this.maritalStatus.name())
                .dependents(this.dependents)
                .income(this.income)
                .build();
    }
}
