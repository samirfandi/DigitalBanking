package org.sid.ebankingbackend.web;

import java.util.List;

import org.sid.ebankingbackend.dtos.AccountHistoryDTO;
import org.sid.ebankingbackend.dtos.BankAccountDTO;
import org.sid.ebankingbackend.dtos.OperationDTO;
import org.sid.ebankingbackend.exceptions.BankAccountNotFoundException;
import org.sid.ebankingbackend.services.BankAccountService;
import org.sid.ebankingbackend.services.BankAccountServiceImp;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@RestController
@Data @AllArgsConstructor  
public class BankAccountRestAPI {

    private BankAccountService bankAccountServiceImp ;


    @GetMapping ("/accounts/{id}")
    public BankAccountDTO getAccount(@PathVariable (name = "id") String accountId) throws BankAccountNotFoundException{

        BankAccountDTO account = bankAccountServiceImp.getAccount(accountId);
        return account;
    }

    @GetMapping ("/accounts")
    public List<BankAccountDTO> getAccounts(){

        List<BankAccountDTO> accounts = bankAccountServiceImp.bankAccountList();
        return accounts;
    }



    @GetMapping ("/accounts/{accountId}/operations")
    public List<OperationDTO> getAccountHistory(@PathVariable String accountId){

        List<OperationDTO> list = bankAccountServiceImp.accountHistory(accountId);

        return list;
    }

    @GetMapping ("/accounts/{accountId}/operationsPage")
    public AccountHistoryDTO getAccountHistory(@PathVariable String accountId,
                                                @RequestParam (name = "page", defaultValue = "0") int page,
                                                @RequestParam (name = "size", defaultValue = "3") int size   ) throws BankAccountNotFoundException{

        AccountHistoryDTO res = bankAccountServiceImp.accountHistoryPage(accountId, page, size);

        return res;
    }




    


}
