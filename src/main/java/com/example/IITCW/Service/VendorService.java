package com.example.IITCW.Service;
import com.example.IITCW.Entities.Vendor;
import com.example.IITCW.Entities.Ticket;

import com.example.IITCW.Repository.TicketRepository;
import com.example.IITCW.Repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VendorService {

    @Autowired
    private VendorRepository vendorRepository;

    @Autowired
    private TicketRepository ticketRepository;

    //Save a vendor
    public Vendor saveVendor(Vendor vendor){
        return vendorRepository.save(vendor);
    }

    //Retrieve all the vendors
    public List<Vendor> getAllVendors() {
        return vendorRepository.findAll();
    }

    //Add tickets for a vendor
    public Ticket addTickets (Long vendorId, Ticket ticket){
        if (ticket.getPrice() <= 0){
            throw new IllegalArgumentException("The ticket price must be greater tha zero");
        }
        Vendor vendor = vendorRepository.findById(vendorId).orElseThrow(()-> new RuntimeException("Vendor not found"));
        ticket.setVendor(vendor);
        return ticketRepository.save(ticket);
    }

    public Vendor getVendorById(Long id){
        return vendorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vendor not found with ID: " + id));
    }

}
