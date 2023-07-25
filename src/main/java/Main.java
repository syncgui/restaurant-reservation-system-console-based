import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        Restaurant restaurant = new Restaurant();

        int choise = 0;

        while (choise != 6) {

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
            choise = scanner.nextInt();

            switch (choise) {
                case 1 -> { // make a new reservation
                    String customerName = askForCustomerName();
                    scanner.nextLine();

                    // precisa verificar se não tem horário igual
                    System.out.print("Enter the reservation date (yyyy-MM-dd HH:mm): ");
                    String reservationTimeStr = scanner.nextLine();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                    LocalDateTime reservationTime = LocalDateTime.parse(reservationTimeStr, formatter);

                    System.out.print("Enter the number of guests: ");
                    int numberOfGuests = scanner.nextInt();

                    int tableNumber = askForTableNumber();
                    while (restaurant.checkIfTableIsTaken(tableNumber)) {
                        System.out.println("Table is already taken!");
                        tableNumber = askForTableNumber();
                    }

                    Reservation reservation = new Reservation(customerName, reservationTime,
                            numberOfGuests, tableNumber);

                    restaurant.makeReservation(reservation);
                }
                case 2 -> { // cancel a reservation
                    int tableNumber = askForTableNumber();
                    while (!restaurant.cancelReservation(tableNumber)) {
                        tableNumber = askForTableNumber();
                    }
                }
                case 3 -> { // modify a reservation
                    int tableNumber = askForTableNumber();
                    while (!restaurant.checkIfTableIsTaken(tableNumber)) {
                        System.out.println("There are no reservation for this table: " + tableNumber);
                        tableNumber = askForTableNumber();
                    }

                    restaurant.modifyReservation(tableNumber, scanner);
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

    private static String askForCustomerName() {
        System.out.print("Enter customer name: ");
        return scanner.next();
    }

    private static int askForTableNumber() {
        System.out.print("Enter the table number: ");
        return scanner.nextInt();
    }
}