package com.enset.ebank_backend.mappers;

import com.enset.ebank_backend.dtos.CurrentBankAccountDTO;
import com.enset.ebank_backend.dtos.CustomerDTO;
import com.enset.ebank_backend.dtos.SavingBankAccountDTO;
import com.enset.ebank_backend.entities.CurrentAccount;
import com.enset.ebank_backend.entities.Customer;
import com.enset.ebank_backend.entities.SavingAccount;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class BAnkAccountMApperImpl {
    public CustomerDTO fromCustomer(Customer customer){
        CustomerDTO customerDTO=new CustomerDTO();
        BeanUtils.copyProperties(customer,customerDTO);
       // customerDTO.setId(customer.getId());
        //customerDTO.setName(customer.getName());
       // customerDTO.setEmail(customer.getEmail());
        return customerDTO;
    }
    public Customer fromCustomerDTO(CustomerDTO customerDTO){
        Customer customer=new Customer();
        BeanUtils.copyProperties(customerDTO,customer);
        return customer;
    }
    public SavingBankAccountDTO fromSavingBankAccount(SavingAccount savingAccount){
       SavingBankAccountDTO savingBankAccountDTO=new SavingBankAccountDTO();
       BeanUtils.copyProperties(savingAccount,savingBankAccountDTO);
       savingBankAccountDTO.setCustomerDTO(fromCustomer(savingAccount.getCustomer()));
       savingBankAccountDTO.setType(savingAccount.getClass().getSimpleName());
       return  savingBankAccountDTO;
    }
    public SavingAccount fromSavingBAnkAccountDTO(SavingBankAccountDTO savingBankAccountDTO){
    SavingAccount savingAccount=new SavingAccount();
    BeanUtils.copyProperties(savingBankAccountDTO,savingAccount);
    savingAccount.setCustomer(fromCustomerDTO(savingBankAccountDTO.getCustomerDTO()));
    return savingAccount;
    }
    public CurrentBankAccountDTO fromCurrentBankAccount(CurrentAccount currentAccount){
     CurrentBankAccountDTO currentBankAccountDTO=new CurrentBankAccountDTO();
     BeanUtils.copyProperties(currentAccount,currentBankAccountDTO);
     currentBankAccountDTO.setCustomerDTO(fromCustomer(currentAccount.getCustomer()));
     currentBankAccountDTO.setType(currentAccount.getClass().getSimpleName());

        return currentBankAccountDTO;
    }
    public CurrentAccount fromCurrentBAnkAccountDTO(CurrentBankAccountDTO currentBankAccountDTO){
       CurrentAccount currentAccount=new CurrentAccount();
       BeanUtils.copyProperties(currentBankAccountDTO,currentAccount);
       currentAccount.setCustomer(fromCustomerDTO(currentBankAccountDTO.getCustomerDTO()));
    return currentAccount;
    }
}