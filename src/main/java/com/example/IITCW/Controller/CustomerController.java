package com.example.IITCW.Controller;

import com.example.IITCW.Entities.Customer;
import com.example.IITCW.Entities.Ticket;
import com.example.IITCW.Service.CustomerService;
import com.example.IITCW.Service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*We dont make the methods static in springboot or else we wont be able to use Spring dependency injection
 * always use non static methods because its preferred in Spring for dependency injection and better
 * testability*/

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Autowired
    private TicketService ticketService;

    //Save multiple customers
    @PostMapping
    public List<Customer> saveCustomers(@RequestBody List<Customer> customers){
        return customerService.saveCustomers(customers);
    }
    @GetMapping
    public List<Customer> getAllCustomers(){
        return customerService.getAllCustomers();
    }

    //Retrieve a customer by the specific ID
    @GetMapping("/{id}")
    public Customer getCustomerById(@PathVariable Long id){
        return customerService.getCustomerById(id);
    }

    @PostMapping("/{customerId}/buy/{ticketId}")
    public ResponseEntity<String> buyTicket(@PathVariable Long customerId, @PathVariable Long ticketId){
        Ticket ticket = ticketService.getTicketById(ticketId);

        if (!ticket.getStatus()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Ticket is already sold");
        }

        ticket.setStatus(false);
        ticketService.saveTicket(ticket);

        return ResponseEntity.ok("Ticket purchased successfully");
    }
}
