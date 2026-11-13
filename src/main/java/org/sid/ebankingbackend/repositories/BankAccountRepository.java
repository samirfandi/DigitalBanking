package org.sid.ebankingbackend.repositories;

import java.util.Optional;

import org.sid.ebankingbackend.entities.BankAccount;
import org.sid.ebankingbackend.exceptions.BankAccountNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount, String> {

    //public BankAccount findById(String id) ;
    
}