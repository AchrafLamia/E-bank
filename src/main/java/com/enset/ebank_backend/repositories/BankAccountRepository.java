package com.enset.ebank_backend.repositories;

import com.enset.ebank_backend.entities.BankAccount;
import com.enset.ebank_backend.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount,String> {
}

