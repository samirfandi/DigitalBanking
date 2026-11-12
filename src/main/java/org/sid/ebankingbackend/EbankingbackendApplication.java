package org.sid.ebankingbackend;


import java.util.stream.Stream;

import org.sid.ebankingbackend.dtos.CustomerDTO;
import org.sid.ebankingbackend.exceptions.BalanceNotSufficientException;
import org.sid.ebankingbackend.exceptions.BankAccountNotFoundException;
import org.sid.ebankingbackend.exceptions.CustomerNotFoundException;
import org.sid.ebankingbackend.services.BankAccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class EbankingbackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(EbankingbackendApplication.class, args);
	}


	//@Bean 
	CommandLineRunner start(BankAccountService bankAccountService){
		return args -> {

			Stream.of("Fatima", "Mohamed", "Issa").forEach(name -> {
				CustomerDTO customer = new CustomerDTO();
				customer.setName(name);
				customer.setEmail(name+"@gmail.com");
				bankAccountService.saveCustomer(customer);
			});


			bankAccountService.listCustomers().forEach(customer -> {
				try {
					bankAccountService.saveCurrentAccount(Math.random()*10000, 1000*Math.random(), customer.getId());
				} catch (CustomerNotFoundException e) {
					e.printStackTrace();
				}
				try {
					bankAccountService.saveSavingAccount(Math.random()*50000, 100*Math.random(), customer.getId());
				} catch (CustomerNotFoundException e) {
					e.printStackTrace();
				}
			});



			/** 
			bankAccountService.bankAccountList().forEach(bankAccount -> {

				for(int i=0; i<3; i++){
					try {
						bankAccountService.credit(bankAccount.getId(), 1000+Math.random()*1000, "Credit");
					} catch (BankAccountNotFoundException e) {
						e.printStackTrace();
					}
					try {
						bankAccountService.debit(bankAccount.getId(), 500+Math.random()*500, "Debit");
					} catch (BankAccountNotFoundException | BalanceNotSufficientException e) {
						e.printStackTrace();
					}
				}
				
			});
			*/

		};
	}

}
