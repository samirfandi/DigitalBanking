package org.sid.ebankingbackend.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity 
@DiscriminatorValue ("SA")
@Data @AllArgsConstructor @NoArgsConstructor 
public class SavaingAccount extends BankAccount{

    private double interestRate;

}
