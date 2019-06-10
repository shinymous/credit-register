package com.cred.register.service;

import com.cred.register.dto.ClientDTO;
import com.cred.register.dto.ProposalDTO;
import com.cred.register.exception.ClientNotFoundException;
import com.cred.register.exception.InvalidDataException;
import com.cred.register.exception.ProposalAlreadyRegisteredException;
import com.cred.register.model.entity.Client;
import com.cred.register.model.entity.Proposal;
import com.cred.register.model.repository.ClientRepository;
import com.cred.register.model.repository.ProposalRepository;
import com.cred.register.model.repository.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import java.util.Optional;

import static com.cred.register.util.CpfUtil.isCpfValid;
import static com.cred.register.util.CpfUtil.removeSpecialCharacters;
import static org.apache.logging.log4j.util.Strings.isBlank;
import static org.apache.logging.log4j.util.Strings.isNotBlank;

@Service
public class RegisterService extends RootService{

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private StateRepository stateRepository;
    @Autowired
    private ProposalRepository proposalRepository;
    @Value("${engine.base.url}")
    private String engineBaseUrl;
    @Autowired
    DtoToEntityService dtoToEntityService;

    @Transactional
    public ProposalDTO newProposal(ClientDTO clientDTO, BindingResult bindingResult){
        this.validateData(clientDTO, bindingResult);
        ProposalDTO proposalDTO = (ProposalDTO) super.request(clientDTO, String.format("%s/%s", engineBaseUrl, "process"),
                HttpMethod.POST, ProposalDTO.class).getBody();

        Client client = this.dtoToEntityService.clientDtoToClient(clientDTO);

        proposalDTO.setUf(client.getState().getInitials());
        Proposal proposal = proposalDTO.toProposal();
        proposalRepository.save(proposal);

        client.setProposal(proposal);
        clientRepository.save(client);

        return proposalDTO;
    }

    public void validateData(ClientDTO clientDTO, BindingResult bindingResult) throws InvalidDataException {
        if(bindingResult.hasErrors()){
            StringBuilder message = new StringBuilder();
            bindingResult.getAllErrors().forEach((e) -> message.append(String.format("%s%s", super.getMessageFromUserLocale(e.getDefaultMessage()), " / ")));
            throw new InvalidDataException(message.toString());
        }
        if(clientDTO.getAge() < 16){
            throw new InvalidDataException(super.getMessageFromUserLocale("client.is.not.old.enough"), clientDTO.getCpf());
        }

        if(!isCpfValid(clientDTO.getCpf())){
            throw new InvalidDataException(super.getMessageFromUserLocale("invalid.cpf"), clientDTO.getCpf());
        }
        clientDTO.setCpf(removeSpecialCharacters(clientDTO.getCpf()));
        Optional<Client> clientOpt = this.clientRepository.findByCpf(clientDTO.getCpf());
        if(clientOpt.isPresent()){
            throw new ProposalAlreadyRegisteredException(
                    super.getMessageFromUserLocale("proposal.already.registered.on", clientDTO.getCpf()));
        }
        if(isNotBlank(clientDTO.getState())){
            this.stateRepository.findByDescriptionOrInitials(clientDTO.getState())
                    .orElseThrow(() -> new InvalidDataException(super.getMessageFromUserLocale("state.does.not.exist", clientDTO.getState())));
        }
    }

    public ProposalDTO findByCpf(String cpf){
        if(isBlank(cpf) || !isCpfValid(cpf)){
            throw new InvalidDataException(super.getMessageFromUserLocale("invalid.cpf"), cpf);
        }
        Optional<Client> clientOpt = this.clientRepository.findByCpf(removeSpecialCharacters(cpf));
        if(!clientOpt.isPresent()) {
            throw new ClientNotFoundException(super.getMessageFromUserLocale("client.not.found.cpf", cpf));
        }
        return new ProposalDTO(clientOpt.get());
    }

    public Page<Client> findAll(PageRequest pageRequest){
        return this.clientRepository.findAll(pageRequest);
    }
}
