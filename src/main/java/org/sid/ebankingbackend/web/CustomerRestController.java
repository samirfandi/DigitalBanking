package org.sid.ebankingbackend.web;

import java.util.List;

import org.sid.ebankingbackend.dtos.CustomerDTO;
import org.sid.ebankingbackend.entities.Customer;
import org.sid.ebankingbackend.exceptions.CustomerNotFoundException;
import org.sid.ebankingbackend.services.BankAccountService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController 
@Slf4j @AllArgsConstructor 
public class CustomerRestController {

    private BankAccountService bankAccountService;


    @GetMapping ("/customers")
    public List<CustomerDTO> customers(){
        
        List<CustomerDTO> list = bankAccountService.listCustomers();
        return list;
    }

    @GetMapping ("/customers/{id}")
    public CustomerDTO getCustomer(@PathVariable (name = "id") Long id) throws CustomerNotFoundException{

        CustomerDTO customerDTO = bankAccountService.getCustomer(id);
       
        return customerDTO;
    }


    @PostMapping ("/customers")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){

        CustomerDTO customerRes = bankAccountService.saveCustomer(customerDTO);

        return customerRes;
    }

    @PutMapping ("/customers/{id}")
    public CustomerDTO updateCustomer(@PathVariable (name = "id") Long id, @RequestBody CustomerDTO customerDTO){

        customerDTO.setId(id);
        CustomerDTO customer = bankAccountService.updateCustomer(customerDTO);

        return customer;
    }

    @DeleteMapping ("/customers/{id}")
    public void deleteCustomer(@PathVariable (name = "id")Long id){

        bankAccountService.deleteCustomer(id);
        
    }
}
