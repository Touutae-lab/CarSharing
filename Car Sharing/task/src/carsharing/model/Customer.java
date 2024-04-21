package carsharing.model;

public class Customer {

    private final int id;
    private final String name;
    private int rentedCarId;

    public Customer(int id, String name) {
        this(id, name, 0);
    }

    public Customer(String name) {
        this(-1, name, 0);
    }

    public Customer(int id, String name, int rentedCarId) {
        this.id = id;
        this.name = name;
        this.rentedCarId = rentedCarId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getRentedCarId() {
        return rentedCarId;
    }

    public void setRentedCarId(int rentedCarId) {
        this.rentedCarId = rentedCarId;
    }

    @Override
    public String toString() {
        return id + ". " + name;
    }
}