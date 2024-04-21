package carsharing.DAO;

import carsharing.model.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomerDAO implements BaseDAO<Customer> {
    private final Connection connection;
    private static final String SELECT_QUERY = "SELECT * FROM CUSTOMER";
    private static final String BY_CAR = " WHERE RENTED_CAR_ID =?";
    private static final String BY_NAME= " WHERE NAME =?";
    private static final String INSERT_CUSTOMERS_SQL = "INSERT INTO CUSTOMER" +
            " (NAME) VALUES" +
            " (?);";
    private static final String UPDATE_USERS_SQL =
            "UPDATE CUSTOMER SET NAME = ?, RENTED_CAR_ID = ? WHERE ID = ?;";

    public CustomerDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Optional<Customer> getByName(String name) {
        Optional<Customer> customer = Optional.empty();

        try (PreparedStatement ps = connection.prepareStatement(SELECT_QUERY + BY_NAME)) {
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                customer = Optional.of(new Customer(rs.getInt("ID"), rs.getString("NAME"), rs.getInt("RENTED_CAR_ID")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return customer;
    }

    @Override
    public Optional<Customer> getById(int id) {
        Optional<Customer> customer = Optional.empty();

        try (PreparedStatement ps = connection.prepareStatement(SELECT_QUERY + BY_CAR)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                customer = Optional.of(new Customer(rs.getInt("ID"), rs.getString("NAME"), rs.getInt("RENTED_CAR_ID")));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return customer;
    }

    @Override
    public List<Customer> getAll() {
        List<Customer> customers = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement(SELECT_QUERY)) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                customers.add(new Customer(rs.getInt("ID"), rs.getString("NAME"), rs.getInt("RENTED_CAR_ID")));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return customers;
    }

    @Override
    public void save(Customer entity) {
        try (PreparedStatement ps = connection.prepareStatement(INSERT_CUSTOMERS_SQL)) {
            ps.setString(1, entity.getName());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Customer entity) {
        try (PreparedStatement ps = connection.prepareStatement(UPDATE_USERS_SQL)) {
            ps.setString(1, entity.getName());
            if (entity.getRentedCarId() > 0) {
                ps.setInt(2, entity.getRentedCarId());
            } else {
                ps.setNull(2, java.sql.Types.INTEGER);
            }
            ps.setInt(3, entity.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Customer entity) {
        try (PreparedStatement ps = connection.prepareStatement("DELETE FROM CUSTOMER WHERE ID = ?")) {
            ps.setInt(1, entity.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
