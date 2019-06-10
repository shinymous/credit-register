package com.cred.register.model.repository;

import com.cred.register.model.entity.State;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StateRepository extends CrudRepository<State, Long>, JpaSpecificationExecutor {

    @Query(value = "select s from State s where s.initials = ?1 " +
            "or s.description = ?1 ")
    Optional<State> findByDescriptionOrInitials(String param);
}
