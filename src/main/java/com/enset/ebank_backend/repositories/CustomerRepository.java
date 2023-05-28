package com.enset.ebank_backend.repositories;

import com.enset.ebank_backend.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
}

