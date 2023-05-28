package com.enset.ebank_backend;

import com.enset.ebank_backend.dtos.BankAccountDTO;
import com.enset.ebank_backend.dtos.CurrentBankAccountDTO;
import com.enset.ebank_backend.dtos.CustomerDTO;
import com.enset.ebank_backend.dtos.SavingBankAccountDTO;
import com.enset.ebank_backend.entities.*;
import com.enset.ebank_backend.enums.AccountStatus;
import com.enset.ebank_backend.enums.OperationType;
import com.enset.ebank_backend.exeption.BalanceNotSufficientException;
import com.enset.ebank_backend.exeption.BankAccountNotFoundException;
import com.enset.ebank_backend.exeption.CustomerNotFoundException;
import com.enset.ebank_backend.repositories.AccountOperationRepository;
import com.enset.ebank_backend.repositories.BankAccountRepository;
import com.enset.ebank_backend.repositories.CustomerRepository;
import com.enset.ebank_backend.services.BankAccountService;
import com.enset.ebank_backend.services.BankAccountServiceImpl;
import com.enset.ebank_backend.services.BankService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class EBankBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(EBankBackendApplication.class, args);
    }
    @Bean
     CommandLineRunner commandLineRunner(BankAccountService bankAccountService) {
        return args -> {
            Stream.of("hassan", "yassin", "Aicha").forEach(name -> {
                CustomerDTO customer=new CustomerDTO();
                customer.setName(name);
                customer.setEmail(name+"@gmail.com");
                bankAccountService.saveCustomer(customer);
            });
            bankAccountService.listCostumers().forEach(customer ->{
             try {
                 bankAccountService.saveCurrentBankAccount(Math.random()*900000,900000,customer.getId());
                 bankAccountService.saveSavingBankAccount(Math.random()*120000,5.5, customer.getId());
                 List<BankAccountDTO>bankAccounts=bankAccountService.bankAccountList();
                 for (BankAccountDTO bankAccount:bankAccounts){

                         for (int i=0;i<10;i++) {
                             String accountId;
                             if (bankAccount instanceof SavingBankAccountDTO){
                                 accountId=((SavingBankAccountDTO) bankAccount).getId();
                             }
                             else {
                                accountId=((CurrentBankAccountDTO)bankAccount).getId();
                             }
                             bankAccountService.credit(accountId,1000+Math.random()*120000 ,"Credit");
                             bankAccountService.debit(accountId,1000+Math.random()*90000,"Debit");
                         }
                     }

             }catch (CustomerNotFoundException  e) {
                 e.printStackTrace();
             } catch (BankAccountNotFoundException |BalanceNotSufficientException e) {
                 e.printStackTrace();   }

            } );
        };
    }

    CommandLineRunner start(CustomerRepository customerRepository, BankAccountRepository bankAccountRepository, AccountOperationRepository accountOperationRepository){
        return  args -> {
            Stream.of("hassan","yassin","Aicha").forEach(name->{
                Customer customer=new Customer();
                customer.setName(name);
                customer.setEmail(name+"@gmail.com");
                customerRepository.save(customer);
            });
            customerRepository.findAll().forEach(cust -> {
                CurrentAccount currentAccount=new CurrentAccount();
                currentAccount.setId(UUID.randomUUID().toString());
                currentAccount.setBalance(Math.random()*90000);
                currentAccount.setCreatedAt(new Date());
                currentAccount.setStatus(AccountStatus.CREATED);
                currentAccount.setCustomer(cust);
                currentAccount.setOverDraft(90000);
                bankAccountRepository.save(currentAccount);


                SavingAccount  savingAccount=new SavingAccount() ;
                savingAccount.setId(UUID.randomUUID().toString());
                savingAccount.setBalance(Math.random()*90000);
                savingAccount.setCreatedAt(new Date());
                savingAccount.setStatus(AccountStatus.CREATED);
                savingAccount.setCustomer(cust);
                savingAccount.setInterestRate(5.5);
                bankAccountRepository.save(savingAccount);
            });
            bankAccountRepository.findAll().forEach(acc->{
                for(int i=0;i<15;i++){
                AccountOperation accountOperation=new AccountOperation();
                accountOperation.setOperationDate(new Date());
                accountOperation.setAmount(Math.random()*120000);
                accountOperation.setType(Math.random()>0.5? OperationType.DEBIT:OperationType.CREDIT);
                accountOperation.setBankAccount(acc);
                accountOperationRepository.save(accountOperation);
             }
            });

        };
  }
}
