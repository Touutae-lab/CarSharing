package carsharing.util;

public class Query {
    public final static String CREATE_TABLE_COMPANY = "CREATE TABLE IF NOT EXISTS COMPANY (" +
            "ID INT PRIMARY KEY AUTO_INCREMENT, " +
            "NAME VARCHAR(255) UNIQUE NOT NULL" +
            ")";
    public final static String CREATE_TABLE_CAR = "CREATE TABLE IF NOT EXISTS CAR " +
            "(ID INTEGER PRIMARY KEY AUTO_INCREMENT, " +
            "NAME VARCHAR(255) UNIQUE NOT NULL, " +
            "COMPANY_ID INTEGER NOT NULL, " +
            "CONSTRAINT fk_company " +
            "FOREIGN KEY (COMPANY_ID) REFERENCES COMPANY (ID))";

    public final static String CREATE_TABLE_CUSTOMER = "CREATE TABLE IF NOT EXISTS CUSTOMER " +
            "(ID INTEGER PRIMARY KEY AUTO_INCREMENT, " +
            "NAME VARCHAR(255) UNIQUE NOT NULL, " +
            "RENTED_CAR_ID INTEGER DEFAULT NULL, " +
            "CONSTRAINT fk_car " +
            "FOREIGN KEY (RENTED_CAR_ID) REFERENCES CAR (ID))";

}
