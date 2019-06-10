package com.cred.register.service;

import com.cred.register.dto.ClientDTO;
import com.cred.register.model.entity.Client;
import com.cred.register.model.repository.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DtoToEntityService extends RootService{

    @Autowired
    private StateRepository stateRepository;

    public Client clientDtoToClient(ClientDTO clientDTO){
        return Client.builder()
                .name(clientDTO.getName())
                .cpf(clientDTO.getCpf())
                .age(clientDTO.getAge())
                .gender(super.getMessageFromUserLocale(clientDTO.getGender().name()))
                .maritalStatus(super.getMessageFromUserLocale(clientDTO.getMaritalStatus().name()))
                .dependents(clientDTO.getDependents())
                .income(clientDTO.getIncome())
                .state(this.stateRepository.findByDescriptionOrInitials(clientDTO.getState()).get())
                .build();
    }
}
