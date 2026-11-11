package org.sid.ebankingbackend.services;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.sid.ebankingbackend.entities.BankAccount;
import org.sid.ebankingbackend.entities.CurrentAccount;
import org.sid.ebankingbackend.entities.Customer;
import org.sid.ebankingbackend.entities.Operation;
import org.sid.ebankingbackend.entities.SavaingAccount;
import org.sid.ebankingbackend.enums.AccountStatus;
import org.sid.ebankingbackend.enums.OperationType;
import org.sid.ebankingbackend.exceptions.BalanceNotSufficientException;
import org.sid.ebankingbackend.exceptions.BankAccountNotFoundException;
import org.sid.ebankingbackend.exceptions.CustomerNotFoundException;
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


    @Override
    public Customer saveCustomer(Customer customer) {

        log.info("A new Customer has been created ");
        Customer customer2 = customerRepository.save(customer);
        return customer2;
       
    }


    @Override
    public CurrentAccount saveCurrentAccount(double initialBalance, double overDraft, Long customerId)
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

        return resultAccount;

    }



    @Override
    public SavaingAccount saveSavingAccount(double initialBalance, double interestRate, Long customerId)
            throws CustomerNotFoundException {
       
        Customer customer = customerRepository.findById(customerId).orElse(null);

        if (customer == null) throw new CustomerNotFoundException("Customer Not Found");

        SavaingAccount account = new SavaingAccount();

        account.setBalance(initialBalance);
        account.setCreatedAt(new Date());
        account.setCustomer(customer);
        account.setId(UUID.randomUUID().toString());
        account.setInterestRate(interestRate);
        account.setStatus(AccountStatus.CREATED);

        SavaingAccount resultAccount = bankAccountRepository.save(account);

        return resultAccount;
    }    

    @Override
    public List<Customer> listCustomers() {
       
        List<Customer> list = customerRepository.findAll();
        
        return list;
    }

    @Override
    public BankAccount getAccount(String accountId) throws BankAccountNotFoundException {
        
        BankAccount account = bankAccountRepository.findById(accountId).orElseThrow(
            ()-> new BankAccountNotFoundException("BankAccount Not Found"));
        
        return account;

    }

    @Override
    public void debit(String accountId, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficientException {
       
        BankAccount account = getAccount(accountId);

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
       
        BankAccount account = getAccount(accountId);


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
    public List<BankAccount> bankAccountList(){

        List<BankAccount> list = bankAccountRepository.findAll();
        
        return list;
    }



}
