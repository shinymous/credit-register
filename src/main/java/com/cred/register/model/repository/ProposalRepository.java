package com.cred.register.model.repository;

import com.cred.register.model.entity.Proposal;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProposalRepository extends CrudRepository<Proposal, Long>, JpaSpecificationExecutor {

}
