package carsharing.model;

public class Company {
    private final int id;
    private final String name;

    public Company(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Company(String name) {
        this(-1, name);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
