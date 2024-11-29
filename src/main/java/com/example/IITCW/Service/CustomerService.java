package com.example.IITCW.Service;

import com.example.IITCW.Entities.Customer;
import com.example.IITCW.Entities.Vendor;
import com.example.IITCW.Repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    //Save a customer
    public Customer saveCustomer(Customer customer){
        return customerRepository.save(customer);
    }

    //Retrieve all customers
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }
    public Customer getCustomerById(Long id){
        return customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with ID: " + id));
    }

   public void deleteCustomer(Long id) {
        if (!customerRepository.existsById(id)){
            throw new RuntimeException("Customer not found with ID: " + id);
        }
    }
}

