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
public class SavaingAccount extends BankAccount{

    private double interestRate;

}
