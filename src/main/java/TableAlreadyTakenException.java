import java.time.LocalDateTime;

public class TableAlreadyTakenException extends Exception {

    public TableAlreadyTakenException(int tableNumber, LocalDateTime reservationTime) {

        super("Already exists a reservation for this table: "
                + tableNumber + " on this date: " + reservationTime);
    }
}
