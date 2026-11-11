package org.sid.ebankingbackend.entities;

import java.util.Date;
import java.util.List;

import org.hibernate.annotations.ManyToAny;
import org.sid.ebankingbackend.enums.AccountStatus;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity 
@Inheritance (strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn (name = "TYPE" )
@Data @AllArgsConstructor @NoArgsConstructor 
public class BankAccount {

    @Id 
    private String id;
    private double Balance;
    private Date createdAt;
    @Enumerated (EnumType.STRING)
    private AccountStatus status;
    @ManyToOne 
    //@JoinColumn(name = "customer_id")
    private Customer customer;
    @OneToMany (mappedBy = "bankAccount", fetch = FetchType.LAZY) 
    private List<Operation> accountOperations;

}
