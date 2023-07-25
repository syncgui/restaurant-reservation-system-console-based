import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Restaurant {

    List<Reservation> reservationList = new ArrayList<>();

    public void makeReservation(Reservation reservation) {
        reservationList.add(reservation);
        System.out.println("Reservation made!");
    }

    public boolean cancelReservation(int tableNumber) {
        if (reservationList.removeIf(t -> t.getTableNumber() == tableNumber)) {
            System.out.println("Reservation canceled!");
            return true;
        } else {
            System.out.println("Table " + tableNumber + " is already available!");
        }
        return false;
    }

    public void modifyReservation(int tableNumber, Scanner scanner) {
        for (Reservation reservation : reservationList) {
            if (reservation.getTableNumber() == tableNumber) {
                System.out.print("""
                        1 - Customer name
                        2 - Reservation time
                        3 - Number of guests
                        4 - Table number
                        5+ - Return to main menu
                        Choose the info you want to modify:
                        """);

                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1 -> {
                        System.out.print("Enter the new customer name: ");
                        String newCustomerName = scanner.nextLine();
                        reservation.setCustomerName(newCustomerName);
                    }
                    case 2 -> {
                        // precisa verificar se não tem horário igual
                        System.out.print("Enter new the reservation date (yyyy-MM-dd HH:mm): ");
                        String newReservationTimeStr = scanner.nextLine();
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                        LocalDateTime newReservationTime = LocalDateTime.parse(newReservationTimeStr, formatter);
                        reservation.setReservationTime(newReservationTime);
                    }
                    case 3 -> {
                        System.out.print("Enter new the number of guests: ");
                        int newNumberOfGuests = scanner.nextInt();
                        reservation.setNumberOfGuests(newNumberOfGuests);
                    }
                    case 4 -> {
                        System.out.print("Enter the new table number: ");
                        int newTableNumber = scanner.nextInt();
                        while (checkIfTableIsTaken(newTableNumber)) {
                            System.out.println("Table is already taken!");
                            System.out.print("Enter the new table number: ");
                            newTableNumber = scanner.nextInt();
                        }
                        reservation.setTableNumber(newTableNumber);
                    }
                    default -> {
                        System.out.println("Returning to the main menu...");
                        return;
                    }
                }
                System.out.println("Reservation modified!");
                return;
            }
        }
    }

    public void getReservationsByTime() {
        reservationList.sort(Comparator.comparing(Reservation::getReservationTime));
        System.out.println("All reservations by time: ");
        for (Reservation reservation : reservationList) {
            System.out.println(reservation);
        }
    }

    public List<Reservation> getReservationByCustomerName(String customerName) {
        List<Reservation> result = new ArrayList<>();
        for (Reservation reservation : reservationList) {
            if (reservation.getCustomerName().equalsIgnoreCase(customerName)) {
                result.add(reservation);
            }
        }
        return result;
    }

    public List<Reservation> getReservationByTableNumber(int tableNumber) {
        List<Reservation> result = new ArrayList<>();
        for (Reservation reservation : reservationList) {
            if (reservation.getTableNumber() == tableNumber) {
                result.add(reservation);
            }
        }
        return result;
    }


    public void getByCustomerNameOrTableNumber(Scanner scanner) {
        System.out.print("""
                1 - Search by customer name
                2 - Search by table number
                3+ - Return to main menu
                Choose the search type:
                """);

        int innerChoice = scanner.nextInt();

        switch (innerChoice) {
            case 1 -> {
                System.out.print("Enter the customer name: ");
                String customerName = scanner.next();
                List<Reservation> reservations = getReservationByCustomerName(customerName);
                while (printResults(reservations)) {
                    System.out.print("Enter the customer name: ");
                    customerName = scanner.next();
                    reservations = getReservationByCustomerName(customerName);
                }
            }
            case 2 -> {
                System.out.print("Enter the table number: ");
                int tableNumber = scanner.nextInt();
                List<Reservation> reservations = getReservationByTableNumber(tableNumber);
                while (printResults(reservations)) {
                    System.out.print("Enter the table number: ");
                    tableNumber = scanner.nextInt();
                    reservations = getReservationByTableNumber(tableNumber);
                }
            }
            default -> System.out.println("Returning to the main menu...");
        }
    }

    public boolean printResults(List<Reservation> reservations) {
        if (reservations.isEmpty()) {
            System.out.println("No reservations found!");
            return true;
        } else {
            for (Reservation reservation : reservations) {
                System.out.println(reservation);
            }
        }
        return false;
    }

    public boolean checkIfTableIsTaken(int tableNumber) {
        return reservationList.stream().anyMatch(t -> t.getTableNumber() == tableNumber);
    }
}
