package com.enset.ebank_backend.web;

import com.enset.ebank_backend.dtos.BankAccountDTO;
import com.enset.ebank_backend.entities.BankAccount;
import com.enset.ebank_backend.exeption.BankAccountNotFoundException;
import com.enset.ebank_backend.services.BankAccountService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BankAccountRestAPI {
    private BankAccountService bankAccountService;
    public BankAccountRestAPI(BankAccountService bankAccountService){
        this.bankAccountService=bankAccountService;
    }
  @GetMapping("/accounts/{accountId}")
   public BankAccountDTO getBankAccount(@PathVariable String accountId)throws BankAccountNotFoundException {
        return bankAccountService.getBankAccount(accountId);
   }
   @GetMapping("/accounts")
   public List<BankAccountDTO> listAccounts(){
        return bankAccountService.bankAccountList();
   }

}
