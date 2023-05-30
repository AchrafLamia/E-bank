package com.enset.ebank_backend.dtos;

import com.enset.ebank_backend.entities.BankAccount;
import com.enset.ebank_backend.enums.OperationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data

public class AccountOperationDTO {
    private Long id;
    private Date operationDate;
    private double amount;

    private OperationType type;

    private String description;

}
