package org.sid.ebankingbackend.dtos;

import java.util.Date;

import org.sid.ebankingbackend.enums.AccountStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data @AllArgsConstructor @NoArgsConstructor 
public class CurrentAccountDTO extends BankAccountDTO{


    
    private String id;
    private double Balance;
    private Date createdAt;
    private AccountStatus status;
    private CustomerDTO customerDTO;
    private double overDraft;
    
    

}



