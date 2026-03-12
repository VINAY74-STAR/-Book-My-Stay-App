import java.util.*;
class RoomAllocationService {
    private Set<String> allocatedRoomIds;
    private Map<String, Set<String>> assignedRoomsByType;
    public RoomAllocationService() {
        allocatedRoomIds = new HashSet<>();
        assignedRoomsByType = new HashMap<>();
    }
    public void allocateRoom(Reservation reservation, RoomInventory inventory) {
        String roomType = reservation.getRoomType();
        if (!inventory.isAvailable(roomType)) {
            System.out.println("No rooms available for " + roomType);
            return;
        }
        String roomId = generateRoomId(roomType);
        allocatedRoomIds.add(roomId);
        assignedRoomsByType
                .computeIfAbsent(roomType, k -> new HashSet<>())
                .add(roomId);
        inventory.decrement(roomType);
        System.out.println("Booking confirmed for Guest: "
                + reservation.getGuestName()
                + ", Room ID: " + roomId);
    }
    private String generateRoomId(String roomType) {
        int number = 1;
        String roomId;
        do {
            roomId = roomType + "-" + number;
            number++;
        } while (allocatedRoomIds.contains(roomId));
        return roomId;
    }
}
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
class RoomInventory {

    private Map<String, Integer> rooms = new HashMap<>();
    public void addRoomType(String type, int count) {
        rooms.put(type, count);
    }
    public boolean isAvailable(String type) {
        return rooms.getOrDefault(type, 0) > 0;
    }
    public void decrement(String type) {
        rooms.put(type, rooms.get(type) - 1);
    }
}
class BookingQueue {
    private Queue<Reservation> queue = new LinkedList<>();
    public void addReservation(Reservation r) {
        queue.offer(r);
    }
    public Reservation getNextReservation() {
        return queue.poll();
    }
    public boolean isEmpty() {
        return queue.isEmpty();
    }
}
public class HOTELBOOKINGAPP {
    public static void main(String[] args) {
        System.out.println("Room Allocation Processing");
        RoomInventory inventory = new RoomInventory();
        inventory.addRoomType("Single", 2);
        inventory.addRoomType("Suite", 1);
        BookingQueue queue = new BookingQueue();
        queue.addReservation(new Reservation("Abhi", "Single"));
        queue.addReservation(new Reservation("Subha", "Single"));
        queue.addReservation(new Reservation("Vanmathi", "Suite"));
        RoomAllocationService allocationService = new RoomAllocationService();
        while (!queue.isEmpty()) {
            Reservation r = queue.getNextReservation();
            allocationService.allocateRoom(r, inventory);
        }
    }
}