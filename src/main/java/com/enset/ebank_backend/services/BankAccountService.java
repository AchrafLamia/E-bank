package com.enset.ebank_backend.services;

import com.enset.ebank_backend.dtos.BankAccountDTO;
import com.enset.ebank_backend.dtos.CurrentBankAccountDTO;
import com.enset.ebank_backend.dtos.CustomerDTO;
import com.enset.ebank_backend.dtos.SavingBankAccountDTO;
import com.enset.ebank_backend.entities.BankAccount;
import com.enset.ebank_backend.entities.CurrentAccount;
import com.enset.ebank_backend.entities.Customer;
import com.enset.ebank_backend.entities.SavingAccount;
import com.enset.ebank_backend.exeption.BalanceNotSufficientException;
import com.enset.ebank_backend.exeption.BankAccountNotFoundException;
import com.enset.ebank_backend.exeption.CustomerNotFoundException;

import java.util.List;

public interface BankAccountService {
    CustomerDTO saveCustomer(CustomerDTO customerDTO);
    CurrentBankAccountDTO saveCurrentBankAccount(double initialBAlance, double overDraft, Long customerId) throws CustomerNotFoundException;
    SavingBankAccountDTO saveSavingBankAccount(double initialBAlance, double interestRate, Long customerId) throws CustomerNotFoundException;

    List<CustomerDTO> listCostumers();
    BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFoundException;
    void debit(String accountID,double amount,String description) throws BankAccountNotFoundException, BalanceNotSufficientException;
    void credit(String accountID,double amount,String description) throws BankAccountNotFoundException;
    void tranfer(String accountIdSource,String accountIdDescription,double amount) throws BankAccountNotFoundException, BalanceNotSufficientException;

    List<BankAccountDTO> bankAccountList();

    CustomerDTO getCustomer(Long customerId) throws CustomerNotFoundException;

    CustomerDTO updateCustomer(CustomerDTO customerDTO);

    void  deletCustomer(Long customerId);
}
