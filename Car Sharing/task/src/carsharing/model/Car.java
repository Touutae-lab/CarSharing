package carsharing.model;

public class Car {
    private final int id;
    private final String name;
    private final int companyId;

    public Car(int id, String name, int companyId) {
        this.id = id;
        this.name = name;
        this.companyId = companyId;
    }

    public Car(String name, int companyId) {
        this.id = -1;
        this.name = name;
        this.companyId = companyId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCompanyId() {
        return companyId;
    }

    @Override
    public String toString() {
        return name;
    }

}
