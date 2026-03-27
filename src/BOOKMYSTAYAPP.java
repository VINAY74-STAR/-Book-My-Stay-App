import java.io.*;
import java.util.*;

class RoomInventory implements Serializable {
    private Map<String, Integer> rooms;

    public RoomInventory() {
        rooms = new HashMap<>();
        rooms.put("Single", 5);
        rooms.put("Double", 3);
        rooms.put("Suite", 2);
    }

    public Map<String, Integer> getRooms() {
        return rooms;
    }

    public void setRooms(Map<String, Integer> rooms) {
        this.rooms = rooms;
    }

    public boolean bookRoom(String type) {
        if (!rooms.containsKey(type) || rooms.get(type) <= 0) {
            return false;
        }
        rooms.put(type, rooms.get(type) - 1);
        return true;
    }

    public void displayRooms() {
        System.out.println("Room Availability:");
        for (Map.Entry<String, Integer> entry : rooms.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}

class BookingSystem implements Serializable {
    private RoomInventory inventory;
    private Map<String, String> bookings;
    private int counter;

    public BookingSystem() {
        inventory = new RoomInventory();
        bookings = new HashMap<>();
        counter = 1;
    }

    public void book(String type) {
        if (inventory.bookRoom(type)) {
            String id = "B" + counter++;
            bookings.put(id, type);
            System.out.println("Booked " + type + " with ID " + id);
        } else {
            System.out.println("Booking failed for " + type);
        }
    }

    public void showBookings() {
        System.out.println("Bookings:");
        for (Map.Entry<String, String> e : bookings.entrySet()) {
            System.out.println(e.getKey() + " -> " + e.getValue());
        }
    }

    public void showInventory() {
        inventory.displayRooms();
    }
}

class PersistenceService {
    private static final String FILE = "data.ser";

    public static void save(BookingSystem system) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE));
            out.writeObject(system);
            out.close();
            System.out.println("Data saved");
        } catch (Exception e) {
            System.out.println("Save failed");
        }
    }

    public static BookingSystem load() {
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE));
            BookingSystem system = (BookingSystem) in.readObject();
            in.close();
            System.out.println("Data loaded");
            return system;
        } catch (Exception e) {
            System.out.println("No previous data found, starting fresh");
            return new BookingSystem();
        }
    }
}

public class BOOKMYSTAYAPP {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        BookingSystem system = PersistenceService.load();

        while (true) {
            System.out.println("\n1.Book\n2.Show Inventory\n3.Show Bookings\n4.Save & Exit");
            int ch = sc.nextInt();

            switch (ch) {
                case 1:
                    System.out.print("Enter room type: ");
                    String type = sc.next();
                    system.book(type);
                    break;
                case 2:
                    system.showInventory();
                    break;
                case 3:
                    system.showBookings();
                    break;
                case 4:
                    PersistenceService.save(system);
                    System.out.println("Exiting...");
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid");
            }
        }
    }
}