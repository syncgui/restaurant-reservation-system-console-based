import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.logging.Logger;

public class UserScanner {

    Logger logger = Logger.getLogger(this.getClass().getName());

    private static UserScanner userScanner;

    private final Scanner scanner;

    private UserScanner() {
        scanner = new Scanner(System.in);
    }

    /**
     * Method to get UserScanner Singleton
     *
     * @return unique instance of UserScanner
     */
    public static UserScanner getInstance() {
        if (userScanner == null) {
            userScanner = new UserScanner();
        }
        return userScanner;
    }

    public int askForIntegerChoice() {
        try {
            String number = userScanner.scanner.nextLine();
            return Integer.parseInt(number);
        } catch (NumberFormatException e) {
            System.out.println();
            System.out.print("Invalid input. Please enter a valid number: ");
            return askForIntegerChoice();
        }
    }

    // Method to scanner int values
    public int readInt() {
        return askForIntegerChoice();
    }

    // Method to format String to LocalDateTime
    public LocalDateTime formatReservationTimeStr(String reservationTimeStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.parse(reservationTimeStr, formatter);
    }

    // Method for ask the user for the Reservation Time
    public LocalDateTime askForReservationTime() {
        try {
            System.out.print("Enter the reservation date (yyyy-MM-dd HH:mm): ");
            String reservationDateStr = userScanner.scanner.next();
            String reservationTimeStr = userScanner.scanner.next();
            userScanner.scanner.nextLine();
            return formatReservationTimeStr(reservationDateStr + " " + reservationTimeStr);
        } catch (DateTimeParseException e) {
            logger.severe(e.getMessage());
            System.out.println();
            return askForReservationTime();
        }
    }

    // Method for ask the user for the Customer Name
    public String askForCustomerName() {
        System.out.print("Enter customer name: ");
        return userScanner.scanner.nextLine();
    }

    // Method for ask the user for the Table Number
    public int askForTableNumber() {
        System.out.print("Enter the table number: ");
        return askForIntegerChoice();
    }

    // Method for ask the user for the Number of Guests
    public int askForNumberOfGuests() {
        System.out.print("Enter the number of guests: ");
        return askForIntegerChoice();
    }
}
