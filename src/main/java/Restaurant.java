import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

public class Restaurant {

    Logger logger = Logger.getLogger(this.getClass().getName());

    UserScanner userScanner = UserScanner.getInstance();

    List<Reservation> reservationList = new ArrayList<>();

    // ****************************
    // ******  MAIN METHODS  ******
    // ****************************

    // Method for make a new Reservation
    public void makeReservation(Reservation reservation) {
        reservationList.add(reservation);
        System.out.println("Reservation made!");
    }

    // Method for cancel a Reservation
    public void cancelReservation() {
        Reservation reservation = findReservationToModifyOrCancel(userScanner.askForTableNumber(), userScanner.askForReservationTime());
        reservationList.remove(reservation);
    }

    // Method for modify a Reservation
    public void modifyReservation() {
        Reservation reservation = findReservationToModifyOrCancel(userScanner.askForTableNumber(), userScanner.askForReservationTime());
        System.out.print("""
                1 - Customer name
                2 - Reservation time
                3 - Number of guests
                4 - Table number
                5+ - Return to main menu
                Choose the info you want to modify:
                """);

        int choice = userScanner.readInt();

        switch (choice) {
            case 1 -> { // modify Customer name
                String newCustomerName = userScanner.askForCustomerName();

                reservation.setCustomerName(newCustomerName);
            }
            case 2 -> {
                // modify Reservation time
                LocalDateTime newReservationTime = userScanner.askForReservationTime();

                modifyTableReservation(newReservationTime, reservation);
            }
            case 3 -> { // modify Number of guests
                int newNumberOfGuests = userScanner.askForNumberOfGuests();

                reservation.setNumberOfGuests(newNumberOfGuests);
            }
            case 4 -> { // modify Table number
                int newTableNumber = userScanner.askForTableNumber();

                modifyTableReservation(newTableNumber, reservation);
            }
            default -> {
                System.out.println("Returning to the main menu...");
                return;
            }
        }
        System.out.println("Reservation modified!");
    }


    // Method for list all Reservations by Time
    public void getReservationsByTime() {
        reservationList.sort(Comparator.comparing(Reservation::getReservationTime));
        System.out.println("All reservations by time: ");

        for (Reservation reservation : reservationList) {
            System.out.println(reservation);
        }
    }

    // Method for search Reservations by the Customer Name
    public List<Reservation> getReservationByCustomerName(String customerName) {
        List<Reservation> result = new ArrayList<>();

        for (Reservation reservation : reservationList) {
            if (reservation.getCustomerName().equalsIgnoreCase(customerName)) {
                result.add(reservation);
            }
        }
        return result;
    }

    // Method for search Reservations by the Table Number
    public List<Reservation> getReservationByTableNumber(int tableNumber) {
        List<Reservation> result = new ArrayList<>();

        for (Reservation reservation : reservationList) {
            if (reservation.getTableNumber() == tableNumber) {
                result.add(reservation);
            }
        }
        return result;
    }

    // Method for ask the user which kind of search to do
    public void getByCustomerNameOrTableNumber() {
        System.out.print("""
                1 - Search by customer name
                2 - Search by table number
                3+ - Return to main menu
                Choose the search type:
                """);

        int innerChoice = userScanner.readInt();

        switch (innerChoice) {
            case 1 -> {
                String customerName = userScanner.askForCustomerName();
                List<Reservation> reservations = getReservationByCustomerName(customerName);

                while (printResults(reservations)) {
                    customerName = userScanner.askForCustomerName();
                    reservations = getReservationByCustomerName(customerName);
                }
            }
            case 2 -> {
                int tableNumber = userScanner.askForTableNumber();
                List<Reservation> reservations = getReservationByTableNumber(tableNumber);

                while (printResults(reservations)) {
                    tableNumber = userScanner.askForTableNumber();
                    reservations = getReservationByTableNumber(tableNumber);
                }
            }
            default -> System.out.println("Returning to the main menu...");
        }
    }

    // *******************************
    // ****** AUXILIARY METHODS ******
    // *******************************

    // Methods for printing results of the provided list if it is not empty
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

    // Method for verify if the Table Number and Reservation Time already exists on the list, considering only the date. Used to assist the makeReservation method.
    public void checkIfTableIsTakenAndSameDate(int tableNumber, LocalDateTime reservationTime) throws TableAlreadyTakenException {
        boolean result = reservationList.stream().filter(rt -> rt.getTableNumber() == tableNumber).anyMatch(rt -> {
            LocalDate localDate = rt.getReservationTime().toLocalDate();
            LocalDate inputDate = reservationTime.toLocalDate();
            return localDate.isEqual(inputDate);
        });
        if (result) {
            throw new TableAlreadyTakenException(tableNumber, reservationTime);
        }
    }

    // Method for verify if the Table Number and Reservation Time already exists on the list, considering date and time. Used to assist the modifyReservation method.
    public void checkIfTableIsTakenAndSameDateTime(int tableNumber, LocalDateTime reservationTime) throws TableAlreadyTakenException {
        boolean result = reservationList.stream().filter(rt -> rt.getTableNumber() == tableNumber)
                .anyMatch(rt -> rt.getReservationTime().isEqual(reservationTime));
        if (result) {
            throw new TableAlreadyTakenException(tableNumber, reservationTime);
        }
    }

    // Method for create a new TableReservation object by checking if the Table Number and Reservation Time already exists
    public TableReservation tableReservation() {
        LocalDateTime reservationTime = userScanner.askForReservationTime();
        int tableNumber = userScanner.askForTableNumber();
        try {
            checkIfTableIsTakenAndSameDate(tableNumber, reservationTime);
            return new TableReservation(tableNumber, reservationTime);
        } catch (TableAlreadyTakenException e) {
            logger.severe(e.getMessage());
            System.out.println();
            return tableReservation();
        }
    }

    // Method for modify the Table Reservation object by checking the Table Number inserted
    public void modifyTableReservation(Integer newTableNumber, Reservation reservation) {
        if (newTableNumber == null) {
            newTableNumber = userScanner.askForTableNumber();
        }

        try {
            checkIfTableIsTakenAndSameDateTime(newTableNumber, reservation.getReservationTime());
            reservation.setTableNumber(newTableNumber);
        } catch (TableAlreadyTakenException e) {
            logger.severe(e.getMessage());
            System.out.println();
            modifyTableReservation(userScanner.askForTableNumber(), reservation);
        }

    }

    // Method for modify the Table Reservation object by checking the Reservation Time  inserted
    public void modifyTableReservation(LocalDateTime newReservationTime, Reservation reservation) {
        if (newReservationTime == null) {
            newReservationTime = userScanner.askForReservationTime();
        }

        try {
            checkIfTableIsTakenAndSameDate(reservation.getTableNumber(), newReservationTime);
            reservation.setReservationTime(newReservationTime);
        } catch (TableAlreadyTakenException e) {
            logger.severe(e.getMessage());
            System.out.println();
            modifyTableReservation(userScanner.askForReservationTime(), reservation);
        }
    }

    // Method to find Reservations to modify or cancel
    public Reservation findReservationToModifyOrCancel(int tableNumber, LocalDateTime reservationTime) {
        try {
            return findReservationByTableNumberAndReservationTime(tableNumber, reservationTime);
        } catch (TableNotFoundException e) {
            logger.severe(e.getMessage());
            System.out.println();
            return findReservationToModifyOrCancel(userScanner.askForTableNumber(), userScanner.askForReservationTime());
        }
    }

    // Method to find reservations by Table Number and Reservation Time.
    public Reservation findReservationByTableNumberAndReservationTime(int tableNumber, LocalDateTime reservationTime) throws TableNotFoundException {
        for (Reservation reservation : reservationList) {
            if (reservation.getTableNumber() == tableNumber && reservation.getReservationTime().isEqual(reservationTime)) {
                return reservation;
            }
        }
        throw new TableNotFoundException(tableNumber, reservationTime);
    }

    // Delegate to userScanner
    public int askForNumberOfGuests() {
        return userScanner.askForNumberOfGuests();
    }

    // Delegate to userScanner
    public String askForCustomerName() {
        return userScanner.askForCustomerName();
    }

    // Delegate to userScanner
    public int askForChoice() {
        return userScanner.readInt();
    }
}