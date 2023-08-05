import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class UserScanner {

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

    // Method to scanner int values
    public int readInt() {
        return userScanner.scanner.nextInt();
    }

    // Method to format String to LocalDateTime
    public LocalDateTime formatReservationTimeStr(String reservationTimeStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.parse(reservationTimeStr, formatter);
    }

    // Method for ask the user for the Reservation Time
    public LocalDateTime askForReservationTime() {
        userScanner.scanner.nextLine();
        System.out.print("Enter the reservation date (yyyy-MM-dd HH:mm): ");
        String reservationDateStr = userScanner.scanner.next();
        String reservationTimeStr = userScanner.scanner.next();
        return formatReservationTimeStr(reservationDateStr + " " + reservationTimeStr);
    }

    // Method for ask the user for the Customer Name
    public String askForCustomerName() {
        System.out.print("Enter customer name: ");
        return userScanner.scanner.next();
    }

    // Method for ask the user for the Table Number
    public int askForTableNumber() {
        System.out.print("Enter the table number: ");
        return userScanner.scanner.nextInt();
    }

    // Method for ask the user for the Number of Guests
    public int askForNumberOfGuests() {
        System.out.print("Enter the number of guests: ");
        return userScanner.scanner.nextInt();
    }
}
