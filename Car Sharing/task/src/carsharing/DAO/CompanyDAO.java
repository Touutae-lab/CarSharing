package carsharing.DAO;

import carsharing.model.Company;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CompanyDAO implements BaseDAO<Company>{

    private final Connection connection;

    private static final String SELECT_QUERY = "SELECT * FROM COMPANY";
    private static final String BY_NAME = " WHERE NAME =?";
    private static final String BY_ID = " WHERE ID =?";
    private static final String INSERT_COMPANIES_SQL = "INSERT INTO COMPANY" +
            " (NAME) VALUES" +
            " (?);";
    public CompanyDAO(Connection connection) {
        this.connection = connection;
    }
    @Override
    public Optional<Company> getByName(String name) {
        Optional<Company> company = Optional.empty();
        try (PreparedStatement ps = connection.prepareStatement(SELECT_QUERY + BY_NAME)) {
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                company = Optional.of(new Company(rs.getInt("ID"), rs.getString("NAME")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return company;
    }

    @Override
    public Optional<Company> getById(int id) {
        Optional<Company> company = Optional.empty();
        try (PreparedStatement ps = connection.prepareStatement(SELECT_QUERY + BY_ID)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                company = Optional.of(new Company(rs.getInt("ID"), rs.getString("NAME")));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return company;
    }

    @Override
    public List<Company> getAll() {
        List<Company> companies = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement(SELECT_QUERY)) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                companies.add(new Company(rs.getInt("ID"), rs.getString("NAME")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return companies;
    }

    @Override
    public void save(Company entity) {
        try (PreparedStatement ps = connection.prepareStatement(INSERT_COMPANIES_SQL)) {
            ps.setString(1, entity.getName());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Company entity) {
        try (PreparedStatement ps = connection.prepareStatement("UPDATE COMPANY SET NAME = ? WHERE ID = ?")) {
            ps.setString(1, entity.getName());
            ps.setInt(2, entity.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Company entity) {
        try (PreparedStatement ps = connection.prepareStatement("DELETE FROM COMPANY WHERE ID = ?")) {
            ps.setInt(1, entity.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
