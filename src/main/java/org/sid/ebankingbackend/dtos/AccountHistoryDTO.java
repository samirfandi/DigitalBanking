package org.sid.ebankingbackend.dtos;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor 
public class AccountHistoryDTO {

    private String accountId;
    private double balance;
    private int currentPage;
    private int totalPages;
    private int sizeOfPage;
    private List<OperationDTO> operationsDTO;

}
