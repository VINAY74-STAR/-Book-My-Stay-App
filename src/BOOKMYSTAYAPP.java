import java.util.*;

class InvalidCancellationException extends Exception {
    public InvalidCancellationException(String message) {
        super(message);
    }
}

class RoomInventory {
    private Map<String, Integer> rooms;

    public RoomInventory() {
        rooms = new HashMap<>();
        rooms.put("Single", 5);
        rooms.put("Double", 3);
        rooms.put("Suite", 2);
    }

    public void incrementRoom(String type) {
        rooms.put(type, rooms.get(type) + 1);
    }

    public void decrementRoom(String type) throws InvalidCancellationException {
        if (!rooms.containsKey(type) || rooms.get(type) <= 0) {
            throw new InvalidCancellationException("Invalid room operation");
        }
        rooms.put(type, rooms.get(type) - 1);
    }

    public void displayRooms() {
        System.out.println("Current Room Availability:");
        for (Map.Entry<String, Integer> entry : rooms.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}

class BookingSystem {
    private RoomInventory inventory;
    private Map<String, String> bookings;
    private Stack<String> rollbackStack;
    private int bookingCounter;

    public BookingSystem() {
        inventory = new RoomInventory();
        bookings = new HashMap<>();
        rollbackStack = new Stack<>();
        bookingCounter = 1;
    }

    public String bookRoom(String type) {
        try {
            inventory.decrementRoom(type);
            String bookingId = "B" + bookingCounter++;
            bookings.put(bookingId, type);
            rollbackStack.push(bookingId);
            System.out.println("Booking successful. Booking ID: " + bookingId);
            return bookingId;
        } catch (Exception e) {
            System.out.println("Booking Failed: " + e.getMessage());
            return null;
        }
    }

    public void cancelBooking(String bookingId) {
        try {
            if (!bookings.containsKey(bookingId)) {
                throw new InvalidCancellationException("Booking does not exist");
            }
            String type = bookings.get(bookingId);
            if (!rollbackStack.contains(bookingId)) {
                throw new InvalidCancellationException("Booking already cancelled");
            }
            rollbackStack.remove(bookingId);
            inventory.incrementRoom(type);
            bookings.remove(bookingId);
            System.out.println("Cancellation successful for Booking ID: " + bookingId);
        } catch (InvalidCancellationException e) {
            System.out.println("Cancellation Failed: " + e.getMessage());
        }
    }

    public void showInventory() {
        inventory.displayRooms();
    }

    public void showBookings() {
        System.out.println("Active Bookings:");
        for (Map.Entry<String, String> entry : bookings.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }
}

public class BOOKMYSTAYAPP {
    public static void main(String[] args) {
        BookingSystem system = new BookingSystem();
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n1. Show Rooms\n2. Book Room\n3. Cancel Booking\n4. Show Bookings\n5. Exit");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    system.showInventory();
                    break;
                case 2:
                    System.out.print("Enter room type (Single/Double/Suite): ");
                    String type = sc.next();
                    system.bookRoom(type);
                    break;
                case 3:
                    System.out.print("Enter Booking ID: ");
                    String id = sc.next();
                    system.cancelBooking(id);
                    break;
                case 4:
                    system.showBookings();
                    break;
                case 5:
                    System.out.println("Exiting...");
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}