package org.sid.ebankingbackend.services;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.sid.ebankingbackend.dtos.BankAccountDTO;
import org.sid.ebankingbackend.dtos.CurrentAccountDTO;
import org.sid.ebankingbackend.dtos.CustomerDTO;
import org.sid.ebankingbackend.dtos.SavingAccountDTO;
import org.sid.ebankingbackend.entities.BankAccount;
import org.sid.ebankingbackend.entities.CurrentAccount;
import org.sid.ebankingbackend.entities.Customer;
import org.sid.ebankingbackend.entities.Operation;
import org.sid.ebankingbackend.entities.SavingAccount;
import org.sid.ebankingbackend.enums.AccountStatus;
import org.sid.ebankingbackend.enums.OperationType;
import org.sid.ebankingbackend.exceptions.BalanceNotSufficientException;
import org.sid.ebankingbackend.exceptions.BankAccountNotFoundException;
import org.sid.ebankingbackend.exceptions.CustomerNotFoundException;
import org.sid.ebankingbackend.mappers.BankAccountMapperImp;
import org.sid.ebankingbackend.repositories.AccountOperationRepository;
import org.sid.ebankingbackend.repositories.BankAccountRepository;
import org.sid.ebankingbackend.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service 
@AllArgsConstructor @Slf4j 
public class BankAccountServiceImp implements BankAccountService{

    private CustomerRepository customerRepository;
    private BankAccountRepository bankAccountRepository;
    private AccountOperationRepository accountOperationRepository;
    private BankAccountMapperImp bankAccountMapperImp;


    @Override
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {

        log.info("Saving a new customer");

        Customer customer = bankAccountMapperImp.fromCustomerDTO(customerDTO);
        Customer savedCustomer = customerRepository.save(customer);
        CustomerDTO res = bankAccountMapperImp.fromCustomer(savedCustomer);

        return res;
    }



    @Override
    public CustomerDTO updateCustomer(CustomerDTO customerDTO) {

        log.info("Update a customer's informations");

        Customer customer = bankAccountMapperImp.fromCustomerDTO(customerDTO);
        Customer savedCustomer = customerRepository.save(customer);
        CustomerDTO res = bankAccountMapperImp.fromCustomer(savedCustomer);

        return res;
    }


    @Override
    public void deleteCustomer(Long customerId) {

        log.info("Delete a customer");
        
        customerRepository.deleteById(customerId);
    }


    @Override
    public CurrentAccountDTO saveCurrentAccount(double initialBalance, double overDraft, Long customerId)
            throws CustomerNotFoundException {
       
        Customer customer = customerRepository.findById(customerId).orElse(null);

        if (customer == null) throw new CustomerNotFoundException("Customer Not Found");

        CurrentAccount account = new CurrentAccount();

        account.setBalance(initialBalance);
        account.setCreatedAt(new Date());
        account.setCustomer(customer);
        account.setId(UUID.randomUUID().toString());
        account.setOverDraft(overDraft);
        account.setStatus(AccountStatus.CREATED);

        CurrentAccount resultAccount = bankAccountRepository.save(account);

        CurrentAccountDTO res = bankAccountMapperImp.fromCurrentAccount(resultAccount);
        return res;

    }



    @Override
    public SavingAccountDTO saveSavingAccount(double initialBalance, double interestRate, Long customerId)
            throws CustomerNotFoundException {
       
        Customer customer = customerRepository.findById(customerId).orElse(null);

        if (customer == null) throw new CustomerNotFoundException("Customer Not Found");

        SavingAccount account = new SavingAccount();

        account.setBalance(initialBalance);
        account.setCreatedAt(new Date());
        account.setCustomer(customer);
        account.setId(UUID.randomUUID().toString());
        account.setInterestRate(interestRate);
        account.setStatus(AccountStatus.CREATED);

        SavingAccount resultAccount = bankAccountRepository.save(account);

        SavingAccountDTO res = bankAccountMapperImp.fromSavingAccount(resultAccount);

        return res;
    }    

    @Override
    public List<CustomerDTO> listCustomers() {
       
        List<Customer> listCustomers = customerRepository.findAll();

        List<CustomerDTO> listCustomerDTO = listCustomers.stream()
                            .map(customer -> bankAccountMapperImp.fromCustomer(customer))
                            .collect(Collectors.toList());

        return listCustomerDTO;
    }

    @Override
    public BankAccountDTO getAccount(String accountId) throws BankAccountNotFoundException {
        
        BankAccount account = bankAccountRepository.findById(accountId).orElseThrow(
            ()-> new BankAccountNotFoundException("BankAccount Not Found"));
        
        BankAccountDTO res;
        if (account instanceof SavingAccount){
            res = bankAccountMapperImp.fromSavingAccount((SavingAccount)account);
        }else {
            res = bankAccountMapperImp.fromCurrentAccount((CurrentAccount)account);
        }

        return res;

    }

    @Override
    public void debit(String accountId, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficientException {
       
        BankAccount account = bankAccountRepository.findById(accountId).orElseThrow(
            ()-> new BankAccountNotFoundException("BankAccount Not Found"));

        if (account.getBalance() < amount) throw new BalanceNotSufficientException("Balance Not Sufficient");

        Operation operation = new Operation();
        operation.setAmount(amount);
        operation.setBankAccount(account);
        operation.setDescription(description);
        operation.setOperationDate(new Date());
        operation.setType(OperationType.DEBIT);
        accountOperationRepository.save(operation);

        account.setBalance(account.getBalance()-amount);

        bankAccountRepository.save(account);
    }

    @Override
    public void credit(String accountId, double amount, String description) throws BankAccountNotFoundException {
       
         BankAccount account = bankAccountRepository.findById(accountId).orElseThrow(
            ()-> new BankAccountNotFoundException("BankAccount Not Found"));


        Operation operation = new Operation();
        operation.setAmount(amount);
        operation.setBankAccount(account);
        operation.setDescription(description);
        operation.setOperationDate(new Date());
        operation.setType(OperationType.CREDIT);
        accountOperationRepository.save(operation);

        account.setBalance(account.getBalance()+amount);

        bankAccountRepository.save(account);
    }

    @Override
    public void transfer(String accountIdSource, String accountIdDestination, double amount) throws BankAccountNotFoundException, BalanceNotSufficientException {
       
        debit(accountIdSource, amount, "Transfer to "+accountIdDestination);

        credit(accountIdDestination, amount, "Transfer from "+accountIdDestination);
    }


    @Override 
    public List<BankAccountDTO> bankAccountList(){

        List<BankAccount> list = bankAccountRepository.findAll();

        List<BankAccountDTO> res = list.stream().map(account -> {
            if (account instanceof SavingAccount){
                SavingAccount savingAccount = (SavingAccount) account;
                return bankAccountMapperImp.fromSavingAccount(savingAccount);
            }else{
                CurrentAccount currentAccount = (CurrentAccount) account;
                return bankAccountMapperImp.fromCurrentAccount(currentAccount);
            }
        }).collect(Collectors.toList());

        return res;
    }


    @Override 
    public CustomerDTO getCustomer(Long customerId) throws CustomerNotFoundException{

        Customer customer = customerRepository.findById(customerId)
                        .orElseThrow(()-> new CustomerNotFoundException("Customer not found"));

        CustomerDTO customerDTO = bankAccountMapperImp.fromCustomer(customer);
        
        return customerDTO;
    }



}
