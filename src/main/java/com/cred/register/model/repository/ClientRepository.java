package com.cred.register.model.repository;

import com.cred.register.model.entity.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends CrudRepository<Client, Long>, JpaSpecificationExecutor {

    Optional<Client> findByCpf(String cpf);

    Page<Client> findAll(Pageable pageable);
}
