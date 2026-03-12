import java.util.*;
class AddOnService {
    private String serviceName;
    private double cost;
    public AddOnService(String serviceName, double cost) {
        this.serviceName = serviceName;
        this.cost = cost;
    }
    public String getServiceName() {
        return serviceName;
    }
    public double getCost() {
        return cost;
    }
}
class AddOnServiceManager {
    private Map<String, List<AddOnService>> servicesByReservation;
    public AddOnServiceManager() {
        servicesByReservation = new HashMap<>();
    }
    public void addService(String reservationId, AddOnService service) {
        servicesByReservation
                .computeIfAbsent(reservationId, k -> new ArrayList<>())
                .add(service);
        System.out.println(service.getServiceName()
                + " added to reservation " + reservationId);
    }
    public double calculateTotalServiceCost(String reservationId) {
        List<AddOnService> services = servicesByReservation.get(reservationId);
        if (services == null) {
            return 0;
        }
        double total = 0;
        for (AddOnService service : services) {
            total += service.getCost();
        }
        return total;
    }
}
public class HOTELBOOKINGAPP {
    public static void main(String[] args) {
        System.out.println("Add-On Service Selection");
        AddOnServiceManager manager = new AddOnServiceManager();
        String reservationId = "RES101";
        AddOnService breakfast = new AddOnService("Breakfast", 250);
        AddOnService spa = new AddOnService("Spa", 1200);
        AddOnService pickup = new AddOnService("Airport Pickup", 800);
        manager.addService(reservationId, breakfast);
        manager.addService(reservationId, spa);
        manager.addService(reservationId, pickup);
        double totalCost = manager.calculateTotalServiceCost(reservationId);
        System.out.println("Total Add-On Cost: " + totalCost);
    }
}