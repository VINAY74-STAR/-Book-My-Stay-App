import java.util.*;

/*
 * CLASS Reservation
 * Represents a confirmed reservation
 */
class Reservation {

    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}


/*
 * CLASS BookingHistory
 * Maintains confirmed reservation records
 */
class BookingHistory {

    /* List that stores confirmed reservations */
    private List<Reservation> confirmedReservations;

    /* Constructor */
    public BookingHistory() {
        confirmedReservations = new ArrayList<>();
    }

    /* Add reservation to history */
    public void addReservation(Reservation reservation) {
        confirmedReservations.add(reservation);
    }

    /* Return stored reservations */
    public List<Reservation> getConfirmedReservations() {
        return confirmedReservations;
    }
}


/*
 * CLASS BookingReportService
 * Generates reports from booking history
 */
class BookingReportService {

    /* Display report */
    public void generateReport(BookingHistory history) {

        System.out.println("Booking History Report");

        for (Reservation r : history.getConfirmedReservations()) {
            System.out.println("Guest: " + r.getGuestName()
                    + ", Room Type: " + r.getRoomType());
        }
    }
}


/*
 * MAIN CLASS
 * Use Case 8 Booking History Report
 */
public class HOTELBOOKINGAPP {
    public static void main(String[] args) {
        System.out.println("Booking History and Reporting");
        BookingHistory history = new BookingHistory();
        Reservation r1 = new Reservation("Abhi", "Single");
        Reservation r2 = new Reservation("Subha", "Double");
        Reservation r3 = new Reservation("Vanmathi", "Suite");
        history.addReservation(r1);
        history.addReservation(r2);
        history.addReservation(r3);
        BookingReportService report = new BookingReportService();
        report.generateReport(history);
    }
}