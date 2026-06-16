import java.util.List;
import java.util.ArrayList;

public class ParkingLot {
    List<Floor> floors = new ArrayList<>();

    public ParkingLot(){

    }

    public void addFloor(Floor floor) {
        floors.add(floor);
    }

    public boolean parkCar(client car) {
        for (Floor floor : floors) {
            ParkingSpot spot = floor.findFreeSpot();

            if (spot != null) {
                spot.park(car);
                System.out.println("Parked car " + car.getCarPlate() + " at floor " + floor.floorNumber + ", spot " + spot.spotId);
                return true;
            }
        }
        System.out.println("Parking lot is occupied, no place for: " + car.getCarPlate());
        return false; // full
    }

    public boolean removeCar(String plate) {
        for (Floor floor : floors) {
            for (ParkingSpot spot : floor.spots) {
                if (!spot.isFree() && spot.car.getCarPlate().equals(plate)) {
                    spot.removeCar();
                    System.out.println("Car " + plate + " parked at floor " + floor.floorNumber + ", spot " + spot.spotId + " left");
                    return true;
                }
            }
        }
        return false;
    }

    public void printStatus() {
        for (Floor floor : floors) {
            System.out.println("Floor " + floor.floorNumber + ":");
            for (ParkingSpot spot : floor.spots) {
                if (spot.isFree()) {
                    System.out.println("  Spot " + spot.spotId + ": FREE");
                } else {
                    System.out.println("  Spot " + spot.spotId + ": OCCUPIED by " + spot.car.getCarPlate());
                }
            }
        }
    }
}