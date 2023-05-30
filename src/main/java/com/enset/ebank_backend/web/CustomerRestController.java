package com.enset.ebank_backend.web;

import com.enset.ebank_backend.dtos.CustomerDTO;
import com.enset.ebank_backend.entities.Customer;
import com.enset.ebank_backend.exeption.CustomerNotFoundException;
import com.enset.ebank_backend.repositories.BankAccountRepository;
import com.enset.ebank_backend.services.BankAccountService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j


public class CustomerRestController {
    private BankAccountService bankAccountService;
   @GetMapping("/customers")
    public List<CustomerDTO> customers(){
        return bankAccountService.listCustomers();
    }
    @GetMapping("/customers/{id}")
    public  CustomerDTO getCustomer(@PathVariable(name = "id") Long customerID) throws CustomerNotFoundException {
   return bankAccountService.getCustomer(customerID);

    }
    @PostMapping("/customers")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
     return bankAccountService.saveCustomer(customerDTO);
    }
    @PutMapping("/customers/{customerId}")
    public CustomerDTO updateCUstomer(@PathVariable Long customerId,@RequestBody CustomerDTO customerDTO){
       customerDTO.setId(customerId);
       return bankAccountService.updateCustomer(customerDTO);
    }
    @DeleteMapping ("/customers/{id}")
    public void deletCustomer(@PathVariable Long id){

       bankAccountService.deleteCustomer(id);
    }

}
