package com.example.IITCW.Service;

import com.example.IITCW.Entities.Ticket;
import com.example.IITCW.Repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    //Save a ticket
    public Ticket saveTicket(Ticket ticket){
        return ticketRepository.save(ticket);
    }

    //Retrieving all tickets
    public List<Ticket> getAllTickets (){
        return ticketRepository.findAll();
    }

    //Get a ticket by ID
    public Ticket getTicketById(Long id){
        return ticketRepository.findById(id).orElseThrow(() -> new RuntimeException("Ticket not found"));
    }

    public void deleteTicket(Long ticketId) {
        if (!ticketRepository.existsById(ticketId)) {
            throw new RuntimeException("Ticket with ID " + ticketId + " does not exist.");
        }
        ticketRepository.deleteById(ticketId);
    }

}
