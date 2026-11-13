package org.sid.ebankingbackend.mappers;

import org.sid.ebankingbackend.dtos.CurrentAccountDTO;
import org.sid.ebankingbackend.dtos.CustomerDTO;
import org.sid.ebankingbackend.dtos.OperationDTO;
import org.sid.ebankingbackend.dtos.SavingAccountDTO;
import org.sid.ebankingbackend.entities.*;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

//MapStruct
@Service 
public class BankAccountMapperImp {

    public CustomerDTO fromCustomer(Customer customer){

        CustomerDTO customerDTO = new CustomerDTO();

        BeanUtils.copyProperties(customer, customerDTO); 

        //customerDTO.setId(customer.getId());
        //customerDTO.setName(customer.getName());
        //customerDTO.setEmail(customer.getEmail());

        return customerDTO;
    }


    public Customer fromCustomerDTO(CustomerDTO customerDTO){
        
        Customer customer = new Customer();

        BeanUtils.copyProperties(customerDTO, customer);

        return customer;
    }


    public SavingAccountDTO fromSavingAccount(SavingAccount savingAccount){

        SavingAccountDTO savingAccountDTO = new SavingAccountDTO();
        BeanUtils.copyProperties(savingAccount, savingAccountDTO);
        savingAccountDTO.setCustomerDTO(fromCustomer(savingAccount.getCustomer()));
        savingAccountDTO.setType(savingAccount.getClass().getSimpleName());

        return savingAccountDTO;
    }



    public SavingAccount fromSavingAccountDTO(SavingAccountDTO savingAccountDTO){

        SavingAccount savingAccount = new SavingAccount();
        BeanUtils.copyProperties(savingAccountDTO, savingAccount);
        savingAccount.setCustomer(fromCustomerDTO(savingAccountDTO.getCustomerDTO()));
        
        return savingAccount;
    }



    public CurrentAccountDTO fromCurrentAccount(CurrentAccount currentAccount){

        CurrentAccountDTO currentAccountDTO = new CurrentAccountDTO();
        BeanUtils.copyProperties(currentAccount, currentAccountDTO);
        currentAccountDTO.setCustomerDTO(fromCustomer(currentAccount.getCustomer()));
        currentAccountDTO.setType(currentAccount.getClass().getSimpleName());

        return currentAccountDTO;
    }


    public CurrentAccount fromCurrentAccountDTO(CurrentAccountDTO currentAccountDTO){

        CurrentAccount currentAccount = new CurrentAccount();
        BeanUtils.copyProperties(currentAccountDTO, currentAccount);
        currentAccount.setCustomer(fromCustomerDTO(currentAccountDTO.getCustomerDTO()));

        return currentAccount;
    }


    public OperationDTO fromOpeartion(Operation opeartion){

        OperationDTO opeartionDTO = new OperationDTO();
        BeanUtils.copyProperties(opeartion, opeartionDTO);

        return opeartionDTO;

    }

}
