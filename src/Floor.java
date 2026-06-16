import java.util.List;
import java.util.ArrayList;

public class Floor {
    int capacity;
    int floorNumber;
    List<ParkingSpot> spots= new ArrayList<>();

    public Floor(int floorNumber, int capacity) {
        this.floorNumber = floorNumber;
        for (int i = 1; i <= capacity; i++) {
            spots.add(new ParkingSpot(i));
        }
    }

    public ParkingSpot findFreeSpot() {
        for (ParkingSpot spot : spots) {
            if (spot.isFree()) {
                return spot;
            }
        }
        return null;
    }
}