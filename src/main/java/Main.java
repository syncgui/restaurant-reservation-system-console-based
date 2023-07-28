import java.time.LocalDateTime;
import java.util.Scanner;

public class Main {

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        Restaurant restaurant = new Restaurant();

        int choice = 0;

        while (choice != 6) {

            System.out.print("""
                    *** Welcome to the Restaurant Reservation System ***
                                    
                    1 - Make reservation
                    2 - Cancel a reservation
                    3 - Modify a reservation
                    4 - Show reservations by time
                    5 - Search reservations by customer name or table number
                    6 - Close system
                                    
                    Choose a operation:
                    """);
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> { // make a new reservation
                    String customerName = askForCustomerName();
                    scanner.nextLine();

                    String reservationTimeStr = askForReservationTime();
                    LocalDateTime reservationTime = restaurant.formatReservationTimeStr(reservationTimeStr);

                    System.out.print("Enter the number of guests: ");
                    int numberOfGuests = scanner.nextInt();

                    int tableNumber = askForTableNumber();
                    while (restaurant.checkIfTableIsTakenAndSameDate(tableNumber, reservationTime)) {
                        System.out.println("Table is already taken at the same date!");

                        tableNumber = askForTableNumber();
                    }

                    Reservation reservation = new Reservation(customerName, reservationTime,
                            numberOfGuests, tableNumber);

                    restaurant.makeReservation(reservation);
                }
                case 2 -> { // cancel a reservation
                    String reservationTimeStr = askForReservationTime();
                    LocalDateTime reservationTime = restaurant.formatReservationTimeStr(reservationTimeStr);

                    int tableNumber = askForTableNumber();
                    scanner.nextLine();

                    while (!restaurant.cancelReservation(tableNumber, reservationTime)) {
                        tableNumber = askForTableNumber();
                    }
                }
                case 3 -> { // modify a reservation
                    String reservationTimeStr = askForReservationTime();
                    LocalDateTime reservationTime = restaurant.formatReservationTimeStr(reservationTimeStr);
                    int tableNumber = askForTableNumber();
                    scanner.nextLine();

                    while (!restaurant.checkIfTableIsTakenAndSameDateTime(tableNumber, reservationTime)) {
                        System.out.println("There are no reservations for this table: "
                                + tableNumber + " on this date: " + reservationTime);

                        reservationTimeStr = askForReservationTime();
                        reservationTime = restaurant.formatReservationTimeStr(reservationTimeStr);
                        tableNumber = askForTableNumber();
                        scanner.nextLine();
                    }

                    restaurant.modifyReservation(tableNumber, reservationTime, scanner);
                }
                case 4 -> // get all reservations by time
                        restaurant.getReservationsByTime();
                case 5 -> // search by customer name or table number
                        restaurant.getByCustomerNameOrTableNumber(scanner);
            }
        }
        System.out.println("Closing program...");
        scanner.close();
    }

    private static String askForReservationTime() {
        System.out.print("Enter the reservation date (yyyy-MM-dd HH:mm): ");
        return scanner.nextLine();
    }

    private static String askForCustomerName() {
        System.out.print("Enter customer name: ");
        return scanner.next();
    }

    private static int askForTableNumber() {
        System.out.print("Enter the table number: ");
        return scanner.nextInt();
    }

}