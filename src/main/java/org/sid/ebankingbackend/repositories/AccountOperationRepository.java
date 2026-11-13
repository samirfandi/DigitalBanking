package org.sid.ebankingbackend.repositories;

import java.util.List;

import org.sid.ebankingbackend.entities.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountOperationRepository extends JpaRepository<Operation, Long>{

   public List<Operation> findByBankAccountId(String accountId);
   public Page<Operation> findByBankAccountId(String accountId, Pageable pageable); 

}
