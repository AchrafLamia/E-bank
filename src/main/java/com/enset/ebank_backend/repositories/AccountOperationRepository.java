package com.enset.ebank_backend.repositories;

import com.enset.ebank_backend.entities.AccountOperation;
import com.enset.ebank_backend.entities.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountOperationRepository extends JpaRepository<AccountOperation,Long> {

 List<AccountOperation>findByBankAccountId(String accountId);



}

