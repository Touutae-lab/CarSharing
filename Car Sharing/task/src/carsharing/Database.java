package carsharing;

import carsharing.util.Query;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Database {
    private final String URL;

    public Database(String[] args) {
       try {
           Class.forName ("org.h2.Driver");
       } catch (Exception ex) {
           System.out.println(ex.getMessage());
        }

       String name = "database";
       if (args.length == 2) {
           name = args[1];
       }
       URL = "jdbc:h2:./src/carsharing/db/" + name;

       try (Connection conn = DriverManager.getConnection(URL);
            Statement st = conn.createStatement()) {
            st.executeUpdate(Query.CREATE_TABLE_COMPANY);
            st.executeUpdate(Query.CREATE_TABLE_CAR);
            st.executeUpdate(Query.CREATE_TABLE_CUSTOMER);
       } catch (Exception ex) {
           ex.printStackTrace();
       }
    }

    public Connection getConnection() {
        Connection conn = null;
        try {
            Connection connection = DriverManager.getConnection(URL);
            connection.setAutoCommit(true);
            conn = connection;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return conn;
    }

    public String getURL() {
        return URL;
    }
}
