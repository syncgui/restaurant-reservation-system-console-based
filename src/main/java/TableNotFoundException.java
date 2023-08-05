import java.time.LocalDateTime;

public class TableNotFoundException extends Exception {

    public TableNotFoundException(int tableNumber, LocalDateTime reservationTime) {

        super("Reservation not found for this table: "
                + tableNumber + " on this date: " + reservationTime);
    }
}
