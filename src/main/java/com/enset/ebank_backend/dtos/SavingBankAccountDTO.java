package com.enset.ebank_backend.dtos;

import com.enset.ebank_backend.enums.AccountStatus;
import lombok.Data;
import javax.persistence.*;
import java.util.Date;


@Data

public class SavingBankAccountDTO extends BankAccountDTO {
    @Id
    private String id;
    private double balance;
    private Date createdAt;

    private AccountStatus status;
    private CustomerDTO customerDTO;
    private double interestRate;
}
