public class Main {
    public static void main(String[] args) {

        ParkingLot parkinglot1 = new ParkingLot();

        Floor floor1 = new Floor(1, 10);
        Floor floor2 = new Floor(2, 10);

        parkinglot1.addFloor(floor1);
        parkinglot1.addFloor(floor2);

        client client1 = new client("AB744884", "Mir Tani");
        client client2 = new client("AL838470", "Rini Mir");
        client client3 = new client("AB793844", "Leo Pard");
        client client4 = new client("AT473470", "Kimi Kat");
        client client5 = new client("AT100001", "Ana Bel");
        client client6 = new client("AT100002", "Ben Doe");
        client client7 = new client("AT100003", "Cara Fox");
        client client8 = new client("AT100004", "Dan Grey");
        client client9 = new client("AT100005", "Eli Hart");
        client client10 = new client("AT100006", "Fay Iris");
        client client11 = new client("AT100007", "Gus Jay"); // 11th car, lot only has 10 spots

        parkinglot1.parkCar(client1);
        parkinglot1.parkCar(client2);
        parkinglot1.parkCar(client3);
        parkinglot1.parkCar(client4);
        parkinglot1.parkCar(client5);
        parkinglot1.parkCar(client6);
        parkinglot1.parkCar(client7);
        parkinglot1.parkCar(client8);
        parkinglot1.parkCar(client9);
        parkinglot1.parkCar(client10);
        parkinglot1.parkCar(client11);

        parkinglot1.printStatus();


    }
}