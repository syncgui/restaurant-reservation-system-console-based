import java.time.LocalDateTime;

public class Reservation {

    private String customerName;
    private LocalDateTime reservationTime;
    private int numberOfGuests;
    private int tableNumber;

    public Reservation(String customerName, LocalDateTime reservationTime, int numberOfGuests, int tableNumber) {
        this.customerName = customerName;
        this.reservationTime = reservationTime;
        this.numberOfGuests = numberOfGuests;
        this.tableNumber = tableNumber;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "customerName='" + customerName + '\'' +
                ", reservationTime=" + reservationTime +
                ", numberOfGuests=" + numberOfGuests +
                ", tableNumber=" + tableNumber +
                '}';
    }

    public LocalDateTime getReservationTime() {
        return reservationTime;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public void setReservationTime(LocalDateTime reservationTime) {
        this.reservationTime = reservationTime;
    }

    public void setNumberOfGuests(int numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
}
