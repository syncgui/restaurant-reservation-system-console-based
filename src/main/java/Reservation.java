import java.time.LocalDateTime;

public class Reservation {

    private String customerName;
    private TableReservation tableReservation;
    private int numberOfGuests;

    public Reservation(String customerName, int numberOfGuests, TableReservation tableReservation) {
        this.customerName = customerName;
        this.numberOfGuests = numberOfGuests;
        this.tableReservation = tableReservation;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setNumberOfGuests(int numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
    }

    public void setReservationTime(LocalDateTime reservationTime) {
        this.tableReservation.setReservationTime(reservationTime);
    }

    public void setTableNumber(int tableNumber) {
        this.tableReservation.setTableNumber(tableNumber);
    }

    public int getTableNumber() {
        return this.tableReservation.getTableNumber();
    }

    public LocalDateTime getReservationTime() {
        return this.tableReservation.getReservationTime();
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "customerName='" + customerName + '\'' +
                ", tableReservation=" + tableReservation +
                ", numberOfGuests=" + numberOfGuests +
                '}';
    }
}
