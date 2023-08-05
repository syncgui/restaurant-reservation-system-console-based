import java.time.LocalDateTime;

public class TableReservation {

    private int tableNumber;
    private LocalDateTime reservationTime;

    public TableReservation(int tableNumber, LocalDateTime reservationTime) {
        this.tableNumber = tableNumber;
        this.reservationTime = reservationTime;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    public LocalDateTime getReservationTime() {
        return reservationTime;
    }

    public void setReservationTime(LocalDateTime reservationTime) {
        this.reservationTime = reservationTime;
    }

    @Override
    public String toString() {
        return "TableReservation{" +
                "tableNumber=" + tableNumber +
                ", reservationTime=" + reservationTime +
                '}';
    }
}
