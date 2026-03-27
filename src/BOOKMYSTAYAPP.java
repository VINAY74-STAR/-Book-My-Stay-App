import java.util.*;
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
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
    public void validateRoomType(String type) throws InvalidBookingException {
        if (!rooms.containsKey(type)) {
            throw new InvalidBookingException("Invalid room type: " + type);
        }
    }
    public void checkAvailability(String type, int count) throws InvalidBookingException {
        int available = rooms.get(type);
        if (count <= 0) {
            throw new InvalidBookingException("Booking count must be greater than 0");
        }
        if (available < count) {
            throw new InvalidBookingException("Not enough rooms available. Available: " + available);
        }
    }
    public void bookRoom(String type, int count) {
        rooms.put(type, rooms.get(type) - count);
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
    public BookingSystem() {
        inventory = new RoomInventory();
    }
    public void processBooking(String type, int count) {
        try {
            inventory.validateRoomType(type);
            inventory.checkAvailability(type, count);
            inventory.bookRoom(type, count);
            System.out.println("Booking successful for " + count + " " + type + " room(s).");
        } catch (InvalidBookingException e) {
            System.out.println("Booking Failed: " + e.getMessage());
        }
    }
    public void showInventory() {
        inventory.displayRooms();
    }
}
public class BOOKMYSTAYAPP {
    public static void main(String[] args) {
        BookingSystem system = new BookingSystem();
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n1. Show Rooms\n2. Book Room\n3. Exit");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    system.showInventory();
                    break;
                case 2:
                    System.out.print("Enter room type (Single/Double/Suite): ");
                    String type = sc.next();
                    System.out.print("Enter number of rooms: ");
                    int count = sc.nextInt();
                    system.processBooking(type, count);
                    break;
                case 3:
                    System.out.println("Exiting...");
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}