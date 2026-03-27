import java.util.*;

class RoomInventory {
    private Map<String, Integer> rooms;

    public RoomInventory() {
        rooms = new HashMap<>();
        rooms.put("Single", 5);
        rooms.put("Double", 3);
        rooms.put("Suite", 2);
    }

    public synchronized boolean bookRoom(String type) {
        if (!rooms.containsKey(type) || rooms.get(type) <= 0) {
            return false;
        }
        rooms.put(type, rooms.get(type) - 1);
        return true;
    }

    public void displayRooms() {
        System.out.println("Final Room Availability:");
        for (Map.Entry<String, Integer> entry : rooms.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}

class BookingRequest {
    String guestName;
    String roomType;

    public BookingRequest(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

class BookingProcessor extends Thread {
    private Queue<BookingRequest> queue;
    private RoomInventory inventory;

    public BookingProcessor(Queue<BookingRequest> queue, RoomInventory inventory) {
        this.queue = queue;
        this.inventory = inventory;
    }

    public void run() {
        while (true) {
            BookingRequest request;
            synchronized (queue) {
                if (queue.isEmpty()) {
                    break;
                }
                request = queue.poll();
            }
            boolean success = inventory.bookRoom(request.roomType);
            if (success) {
                System.out.println(request.guestName + " booked " + request.roomType);
            } else {
                System.out.println(request.guestName + " failed to book " + request.roomType);
            }
        }
    }
}

public class BOOKMYSTAYAPP {
    public static void main(String[] args) {
        RoomInventory inventory = new RoomInventory();
        Queue<BookingRequest> queue = new LinkedList<>();

        queue.add(new BookingRequest("Guest1", "Single"));
        queue.add(new BookingRequest("Guest2", "Single"));
        queue.add(new BookingRequest("Guest3", "Single"));
        queue.add(new BookingRequest("Guest4", "Single"));
        queue.add(new BookingRequest("Guest5", "Single"));
        queue.add(new BookingRequest("Guest6", "Single"));
        queue.add(new BookingRequest("Guest7", "Double"));
        queue.add(new BookingRequest("Guest8", "Double"));
        queue.add(new BookingRequest("Guest9", "Suite"));
        queue.add(new BookingRequest("Guest10", "Suite"));

        BookingProcessor t1 = new BookingProcessor(queue, inventory);
        BookingProcessor t2 = new BookingProcessor(queue, inventory);
        BookingProcessor t3 = new BookingProcessor(queue, inventory);

        t1.start();
        t2.start();
        t3.start();

        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (Exception e) {
        }

        inventory.displayRooms();
    }
}