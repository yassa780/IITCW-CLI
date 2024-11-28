package com.example.IITCW.Controller;

import com.example.IITCW.Entities.Customer;
import com.example.IITCW.Entities.Ticket;
import com.example.IITCW.Entities.Vendor;
import com.example.IITCW.Service.CustomerService;
import com.example.IITCW.Service.TicketService;
import com.example.IITCW.Service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    private VendorService vendorService;

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

    @PostMapping("/{customerId}/buy")
    public ResponseEntity<String> buyTicket(@PathVariable Long customerId) {
        // Fetch customer
        Customer customer = customerService.getCustomerById(customerId);

        // Fetch all available tickets
        List<Ticket> availableTickets = ticketService.getAllTickets()
                .stream()
                .filter(Ticket::getStatus) // Only tickets with `status = true` (available)
                .collect(Collectors.toList());

        if (availableTickets.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No available tickets");
        }

        // Purchase the first available ticket
        Ticket ticket = availableTickets.get(0); // Get the first available ticket
        ticket.setStatus(false); // Mark ticket as sold
        ticketService.saveTicket(ticket); // Save the updated ticket

        return ResponseEntity.ok("Ticket purchased successfully by " + customer.getName());
    }


}
