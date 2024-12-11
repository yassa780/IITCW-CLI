REAL TIME TICKETING EVENT SYSTEM

OVERVIEW
The Real-time Event ticketing system is a multi-threaded command-line application where vendors add tickets 
to a shared ticketpool and customers purchase tickets concurrently. And the system allows users to configure 
parameters like ticket capacity and rates, which are thensaved to a json file.

FEATURES
Concurrent Operations:
  - Vendors and customers operate concurrently using threads
   
 Dynamic Configurations
  - Customize ticket pool size, vendor and customer counts, ticket release rate, and customer retrieval rates

Parameter Persistence:
 - Saves configuration parameters to a JSON file for easy retrieval

Thread-Safe Ticket Pool:
 - Ensures synchronized access to tickets with propper locking mechanisms.

Logging:
 - Tracks key operations such as ticket addition, purchases

HOW THE SYSTEM WORKS
Configuration
 - Inputs such as total tickets, maximum ticket capacity, ticket release rate, and customer
   retrieval rate are provided by the user and saved to a JSON file

Vendors
 - Add tickets to the ticketpool at the specified release rate until the pool reaches its maximum ticket capacity,
   and it gets reduced from the total number of tickets when the vendors add tickets to the ticketpool

Customers
 - Purchase tickets from the ticket pool at the specified retrieval rate. If no tickets are available, the system waits
  until the tickets are added by the vendors

Tikcetpool 
 - A shared resource accessed by vendors and customers with thread-safe operations

Threads 
 - Vendors and customers run as seperate threads, performing their operations
  concurrently

GETTING STARTED
Prerequisites
Java 17 or higher
IDE (e.g., IntelliJ IDEA, Eclipse) or a terminal for running the program

USING TERMINAL
1.Clone the repository
   git clone https://github.com/yassa780/IITCW-CLI.git
   cd IITCW-CLI

3. Compile the program
   javac -d out src/com.example.IITCW/CLI/*.java

4. Run the application
   java -cp out com.example.IITCW.CLI.CLIApplication

USING AN IDE
1. Clone the repository
   git clone https://github.com/yassa780/IITCW-CLI.git
2. Open the project using an IDE(Mostly reccommend Intellij IDEA or Eclipse)
3. Run the file CLIApplication.java

CONTACT INFORMATION
Name - Yasindu Mallawaarachchi
Email - yasindu.20231053@iit.ac.lk
Github username - yassa780
