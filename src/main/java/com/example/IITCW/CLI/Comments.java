

/*public class Comments
     public class TicketPool {
    private final List<String> tickets; // Synchronized list to hold tickets
    private final int maxCapacity;
    private boolean sellingComplete = false; // Flag to indicate ticket selling is complete

    public TicketPool(int maxCapacity) {
        this.tickets = Collections.synchronizedList(new ArrayList<>()); // Use synchronizedList
        this.maxCapacity = maxCapacity;
    }

    // Method to add tickets (used by vendors)
    public void addTickets(int numberOfTicketsToAdd) {
        synchronized (tickets) { // Synchronize on the list for compound actions
            while (tickets.size() >= maxCapacity) {
                try {
                    System.out.println("Ticket pool is full. Vendor is waiting.");
                    tickets.wait(); // Wait until customers consume tickets
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }

            int ticketsToAdd = Math.min(numberOfTicketsToAdd, maxCapacity - tickets.size());
            for (int i = 0; i < ticketsToAdd; i++) {
                tickets.add("Ticket " + (tickets.size() + 1));
            }

            System.out.println(ticketsToAdd + " tickets added. Total tickets: " + tickets.size());
            tickets.notifyAll(); // Notify customers that tickets are available
        }
    }

    // Method to remove tickets (used by customers)
    public int removeTickets(int numberOfTicketsToRemove) {
        synchronized (tickets) { // Synchronize on the list for compound actions
            while (tickets.isEmpty() && !sellingComplete) {
                try {
                    System.out.println("No tickets available. Customer is waiting.");
                    tickets.wait(); // Wait for tickets to become available
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return -1; // Exit gracefully on interruption
                }
            }

            if (tickets.isEmpty() && sellingComplete) {
                return 0; // Graceful exit if no tickets are available and selling is complete
            }

            int ticketsToRemove = Math.min(numberOfTicketsToRemove, tickets.size());
            for (int i = 0; i < ticketsToRemove; i++) {
                tickets.remove(0);
            }

            System.out.println(ticketsToRemove + " tickets removed. " + tickets.size() + " tickets remaining.");
            tickets.notifyAll(); // Notify vendors that space is available
            return ticketsToRemove;
        }
    }

    // Mark ticket selling as complete
    public synchronized void setSellingComplete(boolean complete) {
        sellingComplete = complete;
        synchronized (tickets) {
            tickets.notifyAll(); // Notify all waiting threads to check conditions
        }
    }

    // Check if ticket selling is complete
    public synchronized boolean isSellingComplete() {
        return sellingComplete;
    }

    // Get the current ticket count
    public synchronized int getTicketCount() {
        synchronized (tickets) {
            return tickets.size();
        }
    }

    // Check if the pool is full
    public synchronized boolean isFull() {
        synchronized (tickets) {
            return tickets.size() >= maxCapacity;
        }
    }

    // Check if the pool is empty
    public synchronized boolean isEmpty() {
        synchronized (tickets) {
            return tickets.isEmpty();
        }
    }
}
