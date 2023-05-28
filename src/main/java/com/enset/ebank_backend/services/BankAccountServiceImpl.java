package com.enset.ebank_backend.services;

import com.enset.ebank_backend.dtos.BankAccountDTO;
import com.enset.ebank_backend.dtos.CurrentBankAccountDTO;
import com.enset.ebank_backend.dtos.CustomerDTO;
import com.enset.ebank_backend.dtos.SavingBankAccountDTO;
import com.enset.ebank_backend.entities.*;
import com.enset.ebank_backend.enums.OperationType;
import com.enset.ebank_backend.exeption.BalanceNotSufficientException;
import com.enset.ebank_backend.exeption.BankAccountNotFoundException;
import com.enset.ebank_backend.exeption.CustomerNotFoundException;
import com.enset.ebank_backend.mappers.BAnkAccountMApperImpl;
import com.enset.ebank_backend.repositories.AccountOperationRepository;
import com.enset.ebank_backend.repositories.BankAccountRepository;
import com.enset.ebank_backend.repositories.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class BankAccountServiceImpl implements BankAccountService{
    private CustomerRepository customerRepository;
    private BankAccountRepository bankAccountRepository;
    private AccountOperationRepository accountOperationRepository;
    private BAnkAccountMApperImpl dtoMapper;
    @Override
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        log.info("Saving new customer");
        Customer customer=dtoMapper.fromCustomerDTO(customerDTO);
        Customer savedCustomer=customerRepository.save(customer);
        return dtoMapper.fromCustomer(savedCustomer);
    }

    @Override
    public CurrentBankAccountDTO saveCurrentBankAccount(double initialBAlance, double overDraft, Long customerId) throws CustomerNotFoundException {

            Customer customer=customerRepository.findById(customerId).orElse(null);
            if(customer==null)
                throw new CustomerNotFoundException("Customer not found");
            CurrentAccount currentAccount=new CurrentAccount();
            currentAccount.setId(UUID.randomUUID().toString());
            currentAccount.setCreatedAt(new Date());
            currentAccount.setBalance(initialBAlance);
            currentAccount.setOverDraft(overDraft);
            currentAccount.setCustomer(customer);
            CurrentAccount savedBankAccout=bankAccountRepository.save(currentAccount);
            return dtoMapper.fromCurrentBankAccount(savedBankAccout);
        }


    @Override
    public SavingBankAccountDTO saveSavingBankAccount(double initialBAlance, double interestRate, Long customerId) throws CustomerNotFoundException {
        Customer customer=customerRepository.findById(customerId).orElse(null);
        if(customer==null)
            throw new CustomerNotFoundException("Customer not found");
        SavingAccount savingAccount=new SavingAccount();
        savingAccount.setId(UUID.randomUUID().toString());
        savingAccount.setCreatedAt(new Date());
        savingAccount.setBalance(initialBAlance);
        savingAccount.setInterestRate(interestRate);
        savingAccount.setCustomer(customer);

        SavingAccount savedBankAccout=bankAccountRepository.save(savingAccount);
        return dtoMapper.fromSavingBankAccount(savedBankAccout);
    }


    @Override
    public List<CustomerDTO> listCostumers() {
      List<Customer>customers= customerRepository.findAll();
      /*
      List<CustomerDTO>customerDTOS=new ArrayList<>;
      for(Customer customer:customers){
         CustomerDTO customerDTO=dtoMapper.fromCustomer(customer);
         customerDtos.add(customerDTO);
       */

        List<CustomerDTO> customerdtos=customers.stream()
                .map(customer ->dtoMapper.fromCustomer(customer))
                .collect(Collectors.toList());
        return customerdtos;
    }


    @Override
    public BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFoundException {
        BankAccount bankAccount=bankAccountRepository.findById(accountId).orElseThrow(()->new BankAccountNotFoundException("BankAccount not found"));
        if(bankAccount instanceof SavingAccount){
            SavingAccount savingAccount=(SavingAccount) bankAccount;
            return dtoMapper.fromSavingBankAccount(savingAccount);

        }else {
            CurrentAccount currentAccount=(CurrentAccount) bankAccount;
            return dtoMapper.fromCurrentBankAccount(currentAccount);
        }
    }

    @Override
    public void debit(String accountId,double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficientException {
        BankAccount bankAccount=bankAccountRepository.findById(accountId).orElseThrow(()->new BankAccountNotFoundException("BankAccount not found"));
        if (bankAccount.getBalance()<amount)
            throw new BalanceNotSufficientException("Balance not sufficient");
        AccountOperation accountOperation=new AccountOperation();
        accountOperation.setType(OperationType.DEBIT);
        accountOperation.setAmount(amount);
        accountOperation.setDescription(description);
        accountOperation.setOperationDate(new Date());
        accountOperationRepository.save(accountOperation);
        bankAccount.setBalance(bankAccount.getBalance()-amount);
        bankAccountRepository.save(bankAccount);
    }

    @Override
    public void credit(String accountId,double amount, String description)  throws BankAccountNotFoundException {
        BankAccount bankAccount=bankAccountRepository.findById(accountId).orElseThrow(()->new BankAccountNotFoundException("BankAccount not found"));
        AccountOperation accountOperation=new AccountOperation();
        accountOperation.setType(OperationType.CREDIT);
        accountOperation.setAmount(amount);
        accountOperation.setDescription(description);
        accountOperation.setOperationDate(new Date());
        accountOperationRepository.save(accountOperation);
        bankAccount.setBalance(bankAccount.getBalance()+amount);
        bankAccountRepository.save(bankAccount);
    }


    @Override
    public void tranfer(String accountIdSource, String accountIdDescription, double amount) throws BankAccountNotFoundException, BalanceNotSufficientException {
        debit(accountIdSource,amount,"Transfer to"+accountIdDescription);
        credit(accountIdDescription,amount,"Transfer from  "+accountIdSource);
    }
    @Override
   public List<BankAccountDTO> bankAccountList(){
        List<BankAccount> bankAccounts=bankAccountRepository.findAll() ;
        List<BankAccountDTO> bankAccountDTOS = bankAccounts.stream().map(bankAccount -> {
            if (bankAccount instanceof SavingAccount) {
                SavingAccount savingAccount = (SavingAccount) bankAccount;
                return dtoMapper.fromSavingBankAccount(savingAccount);

            } else {
                CurrentAccount currentAccount = (CurrentAccount) bankAccount;
                return dtoMapper.fromCurrentBankAccount(currentAccount);
            }
        }).collect(Collectors.toList());
   return bankAccountDTOS;
    }
   @Override
   public CustomerDTO getCustomer(Long customerId) throws CustomerNotFoundException {
        Customer customer= customerRepository.findById(customerId).orElseThrow(()->new CustomerNotFoundException("CUstomer not found"));
            return dtoMapper.fromCustomer(customer);
   }
    @Override
    public CustomerDTO updateCustomer(CustomerDTO customerDTO) {
        log.info("Saving new customer");
        Customer customer=dtoMapper.fromCustomerDTO(customerDTO);
        Customer savedCustomer=customerRepository.save(customer);
        return dtoMapper.fromCustomer(savedCustomer);
    }
    @Override
   public void  deletCustomer(Long customerId){
        customerRepository.deleteById(customerId);
   }


}
