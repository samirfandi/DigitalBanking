package org.sid.ebankingbackend;

import java.util.Date;
import java.util.UUID;
import java.util.stream.Stream;

import org.sid.ebankingbackend.entities.BankAccount;
import org.sid.ebankingbackend.entities.CurrentAccount;
import org.sid.ebankingbackend.entities.Customer;
import org.sid.ebankingbackend.entities.Operation;
import org.sid.ebankingbackend.entities.SavaingAccount;
import org.sid.ebankingbackend.enums.AccountStatus;
import org.sid.ebankingbackend.enums.OperationType;
import org.sid.ebankingbackend.repositories.AccountOperationRepository;
import org.sid.ebankingbackend.repositories.BankAccountRepository;
import org.sid.ebankingbackend.repositories.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class EbankingbackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(EbankingbackendApplication.class, args);
	}


	//@Bean 
	CommandLineRunner start(CustomerRepository customerRepository, BankAccountRepository bankAccountRepository, AccountOperationRepository accountOperationRepository){
		return args -> {

			Stream.of("Samir", "Ahmed", "Aicha").forEach(name -> {
				Customer customer = new Customer();
				customer.setName(name);
				customer.setEmail(name+"@gmail.com");
				customerRepository.save(customer);
			});


			customerRepository.findAll().forEach(customer -> {
				CurrentAccount bankAccount =new CurrentAccount();
				bankAccount.setId(UUID.randomUUID().toString());
				bankAccount.setBalance(Math.random()*8757);
				bankAccount.setCreatedAt(new Date());
				bankAccount.setStatus(Math.random() > 0.5 ? AccountStatus.CREATED : AccountStatus.ACTIVATED);
				bankAccount.setCustomer(customer);
				bankAccount.setOverDraft(78*Math.random());
				bankAccountRepository.save(bankAccount);
			});

			customerRepository.findAll().forEach(customer -> {
				SavaingAccount bankAccount =new SavaingAccount();
				bankAccount.setId(UUID.randomUUID().toString());
				bankAccount.setBalance(Math.random()*8757);
				bankAccount.setCreatedAt(new Date());
				bankAccount.setStatus(Math.random() > 0.5 ? AccountStatus.CREATED : AccountStatus.ACTIVATED);
				bankAccount.setCustomer(customer);
				bankAccount.setInterestRate(4.5);
				bankAccountRepository.save(bankAccount);
			});

			bankAccountRepository.findAll().forEach(bankAccount -> {
				for(int i=0; i<2; i++){
					Operation operation = new Operation();
					operation.setAmount(Math.random()*1000);
					operation.setBankAccount(bankAccount);
					operation.setOperationDate(new Date());
					operation.setType(Math.random() > 0.5 ? OperationType.CREDIT : OperationType.DEBIT);
					accountOperationRepository.save(operation);
				}
				
			});


		};
	}

}
