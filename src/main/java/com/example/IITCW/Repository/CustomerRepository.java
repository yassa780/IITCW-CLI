package com.example.IITCW.Repository;

import com.example.IITCW.Entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long > {
}
