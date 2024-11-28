package com.example.IITCW.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //Unique ID for each ticket

    private double price;
    private int quantity;
    private boolean status; // "Available" or "Sold"


    @ManyToOne
    @JoinColumn(name = "vendor_id") // Foreign key column
    @JsonIgnoreProperties("tickets") // Prevent recursion when serializing Vendor
    private Vendor vendor;

    public Ticket (){

    }

    public Ticket(Long id, double price, int quantity, boolean status, Vendor vendor) {
        this.id = id;
        this.price = price;
        this.quantity = quantity;
        this.status = status;
        this.vendor = vendor;
    }

    public Vendor getVendor(){
        return vendor;
    }

    public void setVendor(Vendor vendor){
        this.vendor = vendor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", status='" + status + '\'' +
                '}';
    }

}