package org.sid.ebankingbackend.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity 
@Data @AllArgsConstructor @NoArgsConstructor 
public class Customer {

    @Id @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    @OneToMany (mappedBy = "customer", fetch = FetchType.LAZY)
    //@JsonProperty (access = JsonProperty.Access.WRITE_ONLY)
    private List<BankAccount> bankAccounts;

}
