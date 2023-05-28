package com.enset.ebank_backend.services;

import com.enset.ebank_backend.entities.BankAccount;
import com.enset.ebank_backend.entities.CurrentAccount;
import com.enset.ebank_backend.entities.SavingAccount;
import com.enset.ebank_backend.repositories.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class BankService {
    @Autowired
    private BankAccountRepository bankAccountRepository;
    public void consulter(){
        BankAccount bankAccount=bankAccountRepository.findById("54660ec8-c7f1-4789-815b-51c835017dda").orElse(null);
        if (bankAccount!=null){
        System.out.println("************");
        System.out.println(bankAccount.getId());
        System.out.println(bankAccount.getBalance());
        System.out.println(bankAccount.getStatus());
        System.out.println(bankAccount.getCreatedAt());
        System.out.println(bankAccount.getCustomer().getName());
        System.out.println(bankAccount.getClass().getSimpleName());
        if (bankAccount instanceof CurrentAccount) {
            System.out.println("over draft=>" + ((CurrentAccount) bankAccount).getOverDraft());
        }else if (bankAccount instanceof SavingAccount){
            System.out.println("interest rate=>"+((SavingAccount)bankAccount).getInterestRate());
        }
        bankAccount.getAccountOperations().forEach(op->{
            System.out.println(op.getType()+"\t"+op.getOperationDate()+"\t"+op.getAmount()+"\t"+op.getBankAccount());
        } );
    }
}}
