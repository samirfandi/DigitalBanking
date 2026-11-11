package org.sid.ebankingbackend.exceptions;

/**
 * BankAccountNotFoundException
 */
public class BankAccountNotFoundException extends Exception{

    public BankAccountNotFoundException(String message){
        super(message);
    }
}
