import java.util.HashMap;
import java.util.Map;

abstract class Room {
    protected String type;
    protected int beds;
    protected int size;
    protected double price;

    public Room(String type, int beds, int size, double price) {
        this.type = type;
        this.beds = beds;
        this.size = size;
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public int getBeds() {
        return beds;
    }

    public int getSize() {
        return size;
    }

    public double getPrice() {
        return price;
    }
}

class SingleRoom extends Room {
    public SingleRoom() {
        super("Single",1,250,1500.0);
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double",2,400,2500.0);
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite",3,750,5000.0);
    }
}

class RoomInventory {
    private Map<String,Integer> roomAvailability;

    public RoomInventory() {
        roomAvailability = new HashMap<>();
        initializeInventory();
    }

    private void initializeInventory() {
        roomAvailability.put("Single",5);
        roomAvailability.put("Double",3);
        roomAvailability.put("Suite",2);
    }

    public Map<String,Integer> getRoomAvailability() {
        return roomAvailability;
    }
}

class RoomSearchService {

    public void searchAvailableRooms(
            RoomInventory inventory,
            Room singleRoom,
            Room doubleRoom,
            Room suiteRoom) {

        Map<String,Integer> availability = inventory.getRoomAvailability();

        System.out.println("Room Search");

        if (availability.get("Single") > 0) {
            System.out.println("Single Room:");
            System.out.println("Beds: " + singleRoom.getBeds());
            System.out.println("Size: " + singleRoom.getSize() + " sqft");
            System.out.println("Price per night: " + singleRoom.getPrice());
            System.out.println("Available: " + availability.get("Single"));
            System.out.println();
        }

        if (availability.get("Double") > 0) {
            System.out.println("Double Room:");
            System.out.println("Beds: " + doubleRoom.getBeds());
            System.out.println("Size: " + doubleRoom.getSize() + " sqft");
            System.out.println("Price per night: " + doubleRoom.getPrice());
            System.out.println("Available: " + availability.get("Double"));
            System.out.println();
        }

        if (availability.get("Suite") > 0) {
            System.out.println("Suite Room:");
            System.out.println("Beds: " + suiteRoom.getBeds());
            System.out.println("Size: " + suiteRoom.getSize() + " sqft");
            System.out.println("Price per night: " + suiteRoom.getPrice());
            System.out.println("Available: " + availability.get("Suite"));
        }
    }
}

public class HOTELBOOKINGAPP{

    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();

        Room singleRoom = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suiteRoom = new SuiteRoom();

        RoomSearchService service = new RoomSearchService();

        service.searchAvailableRooms(
                inventory,
                singleRoom,
                doubleRoom,
                suiteRoom);
    }
}