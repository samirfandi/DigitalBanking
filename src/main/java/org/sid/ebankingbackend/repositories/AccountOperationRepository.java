package org.sid.ebankingbackend.repositories;

import org.sid.ebankingbackend.entities.Operation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountOperationRepository extends JpaRepository<Operation, Long>{

    
}
