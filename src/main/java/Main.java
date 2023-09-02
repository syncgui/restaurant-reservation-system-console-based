public class Main {
    public static void main(String[] args) {

        Restaurant restaurant = new Restaurant();

        int choice = 0;

        while (choice != 6) {

            choice = restaurant.showOptions();

            switch (choice) {
                case 1 -> { // make a new reservation
                    String customerName = restaurant.askForCustomerName();

                    int numberOfGuests = restaurant.askForNumberOfGuests();

                    TableReservation tableReservation = restaurant.tableReservation();

                    Reservation reservation = new Reservation(customerName, numberOfGuests, tableReservation);

                    restaurant.makeReservation(reservation);
                }
                case 2 -> // cancel a reservation
                        restaurant.cancelReservation();
                case 3 -> // modify a reservation
                        restaurant.modifyReservation();
                case 4 -> // get all reservations by time
                        restaurant.getReservationsByTime();
                case 5 -> // search by customer name or table number
                        restaurant.getByCustomerNameOrTableNumber();
            }
        }
        System.out.println("Closing program...");
    }
}