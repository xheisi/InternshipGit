public class ParkingSpot {
    int spotId;
    client car; // null = empty

    public ParkingSpot(int spotId) {
        this.spotId = spotId;
    }

    public boolean isFree() {
        return car == null;
    }

    public void park(client car) {
        this.car = car;
    }

    public void removeCar() {
        this.car = null;
    }
}