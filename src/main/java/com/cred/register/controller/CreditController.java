package com.cred.register.controller;

import com.cred.register.dto.ClientDTO;
import com.cred.register.dto.PagedResultDTO;
import com.cred.register.dto.ProposalDTO;
import com.cred.register.model.entity.Client;
import com.cred.register.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController()
@RequestMapping("/proposal")
public class CreditController implements NewCreditAPI{

    @Autowired
    private RegisterService registerService;

    @PostMapping()
    public ProposalDTO toRegister(@RequestBody @Valid ClientDTO clientDTO, BindingResult bindingResult){
        return this.registerService.newProposal(clientDTO, bindingResult);
    }

    @GetMapping(value = "{cpf}")
    public ProposalDTO findByCpf(@PathVariable(value = "cpf") String cpf){
        return this.registerService.findByCpf(cpf);
    }

    @GetMapping()
    public PagedResultDTO<ProposalDTO> getAll(@RequestParam(value = "page", defaultValue = "0") int page,
                                    @RequestParam(value = "limit", defaultValue = "30") int limit,
                                    @RequestParam(value = "order", defaultValue = "id") String order,
                                    @RequestParam(value = "direction", defaultValue = "ASC") Sort.Direction direction){
        PageRequest pageRequest = PageRequest.of(page, limit, new Sort(direction, order));
        return mapToDTO(this.registerService.findAll(pageRequest));
    }

    public PagedResultDTO<ProposalDTO> mapToDTO(Page<Client> entitiesPage) {
        List<ProposalDTO> proposalDTOList = entitiesPage
                .stream()
                .map(ProposalDTO::new)
                .collect(Collectors.toList());
        return new PagedResultDTO<>(proposalDTOList, entitiesPage.getTotalElements(), entitiesPage.getNumber(), entitiesPage.getNumberOfElements());
    }
}
