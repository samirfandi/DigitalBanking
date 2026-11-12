package org.sid.ebankingbackend.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity 
@DiscriminatorValue ("SA")
@Getter @Setter  @AllArgsConstructor @NoArgsConstructor 
public class SavingAccount extends BankAccount{

    private double interestRate;

}
