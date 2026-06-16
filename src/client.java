public class client{
    private String plate;
    private String name;

    public client(String id, String name){
        this.plate = id;
        this.name = name;
    }

    public String getCarPlate() {
        return plate;
    }

    public void setCarPlate(String carPlate) {
        this.plate = carPlate;
    }

    public String getFullName() {
        return name;
    }
    public void setFullName(String fullName) {
        this.name = fullName;
    }

    @Override
    public String toString() {
        return "Client{" +
                "plate='" + plate + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}