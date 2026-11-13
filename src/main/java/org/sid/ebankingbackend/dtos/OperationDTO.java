package org.sid.ebankingbackend.dtos;

import java.util.Date;

import org.sid.ebankingbackend.enums.OperationType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data @NoArgsConstructor @AllArgsConstructor 
public class OperationDTO {


   
    private Long id;
    private Date operationDate;
    private double amount;
    private OperationType type;
    private String description;

}
