package carsharing.DAO;

import carsharing.model.Car;
import carsharing.model.Company;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Optional;
import java.util.List;

public class CarDAO implements BaseDAO<Car> {
    private static final String SELECT_QUERY = "SELECT * FROM CAR";
    private static final String BY_COMPANY = " WHERE COMPANY_ID =?";
    private static final String BY_NAME= " WHERE NAME =?";
    private static final String BY_ID= " WHERE ID =?";
    private static final String INSERT_CARS_SQL = "INSERT INTO CAR" +
            " (NAME, COMPANY_ID) VALUES" +
            " (?, ?);";
    private static final String SELECT_AVAILABLE_CARS =
            "SELECT CAR.ID, CAR.NAME, CAR.COMPANY_ID" +
                    " FROM CAR LEFT JOIN CUSTOMER ON CAR.ID = CUSTOMER.RENTED_CAR_ID" +
                    " WHERE COMPANY_ID = ? AND CUSTOMER.ID IS NULL";

    private final Connection connection;

    public CarDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Optional<Car> getByName(String name) {
        Optional<Car> car = Optional.empty();
        try (PreparedStatement ps = connection.prepareStatement(SELECT_QUERY + BY_NAME)) {
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                car = Optional.of(new Car(rs.getInt("ID"), rs.getString("NAME"), rs.getInt("COMPANY_ID")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return car;
    }

    @Override
    public Optional<Car> getById(int id) {
        Optional<Car> car = Optional.empty();
        try (PreparedStatement ps = connection.prepareStatement(SELECT_QUERY + BY_ID)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                car = Optional.of(new Car(rs.getInt("ID"), rs.getString("NAME"), rs.getInt("COMPANY_ID")));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return car;
    }

    @Override
    public List<Car> getAll() {
        return null;
    }

    @Override
    public void save(Car entity) {
        try (PreparedStatement ps = connection.prepareStatement(INSERT_CARS_SQL)) {
            ps.setString(1, entity.getName());
            ps.setInt(2, entity.getCompanyId());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Car entity) {
        try (PreparedStatement ps = connection.prepareStatement("UPDATE CAR SET NAME = ? WHERE ID = ?")) {
            ps.setString(1, entity.getName());
            ps.setInt(2, entity.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Car entity) {
        try (PreparedStatement ps = connection.prepareStatement("DELETE FROM CAR WHERE ID = ?")) {
            ps.setInt(1, entity.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Car> getAllByCompany(Company company) {
        List<Car> cars = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(SELECT_QUERY + BY_COMPANY)) {
            ps.setInt(1, company.getId());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int company_id = rs.getInt("company_id");
                cars.add(new Car(id, name, company_id));
            }
        } catch (Exception e) {
            System.out.println("Err while getting All");
        }
        return cars;
    }

    public List<Car> getAllAvailable(Company company) {
        List<Car> cars = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(SELECT_AVAILABLE_CARS)) {
            ps.setInt(1, company.getId());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int company_id = rs.getInt("company_id");
                cars.add(new Car(id, name, company_id));
            }
        } catch (Exception e) {
            System.out.println("Err while getting All");
        }
        return cars;
    }
}
