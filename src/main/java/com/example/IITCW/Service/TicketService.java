package com.example.IITCW.Service;

import com.example.IITCW.Entities.Ticket;
import com.example.IITCW.Repository.TicketRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {

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

}
