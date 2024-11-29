package com.example.IITCW.Controller;

import com.example.IITCW.Entities.Ticket;
import com.example.IITCW.Entities.Vendor;
import com.example.IITCW.Service.TicketService;
import com.example.IITCW.Service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vendors")
public class VendorController {

    @Autowired
    private VendorService vendorService;

    @Autowired
    private TicketService ticketService;

    @Autowired
    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }/*Its a constructor injection
     */

    //Save a new Vendor
    @PostMapping
    public Vendor saveVendor(@RequestBody Vendor vendor){
        return vendorService.saveVendor(vendor);
    }

    //Retrive all the vendors
    @GetMapping
    public List<Vendor> getAllVendors(){
        return vendorService.getAllVendors();
    }

    //Get a single vendor by ID
    @GetMapping("/{id}")
    public Vendor getVendorById(@PathVariable Long id){
        return vendorService.getVendorById(id);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteVendorById(@PathVariable Long id){
        vendorService.deleteVendor(id);
        return ResponseEntity.ok("Vendor with ID " + id + " deleted successfully");
    }

    @PostMapping("/{vendorId}/tickets")
    public ResponseEntity<Ticket> addTickets(@PathVariable Long vendorId, @RequestBody Ticket ticket) {
        Ticket addedTicket = vendorService.addTickets(vendorId, ticket);
        return ResponseEntity.ok(addedTicket); // Return the full ticket with vendor details
    }


    @GetMapping("/{vendorId}/tickets")
    public List<Ticket> getTicketsByVendor(@PathVariable Long vendorId) {
        Vendor vendor = vendorService.getVendorById(vendorId);
        return vendor.getTickets();
    }

    // Delete a specific ticket by its ID
    @DeleteMapping("/{vendorId}/tickets/{ticketId}")
    public ResponseEntity<String> deleteTicket(@PathVariable Long vendorId, @PathVariable Long ticketId) {
        Vendor vendor = vendorService.getVendorById(vendorId);
        Ticket ticket = ticketService.getTicketById(ticketId);

        if (!ticket.getVendor().getId().equals(vendorId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Ticket does not belong to this vendor.");
        }

        ticketService.deleteTicket(ticketId);
        return ResponseEntity.ok("Ticket with ID " + ticketId + " has been deleted.");
    }
}
/*Path variable is used to uniquely identify parts of the path*/
/*When a client sends data from the frontend Request body tells Spring to map
* that data.*/
