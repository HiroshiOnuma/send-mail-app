package model;
import java.sql.*;

public abstract class BaseDAO {
    protected Connection getConnection() {
        DBConnection dbConnection = new DBConnection();
        return dbConnection.createConnection();
    }

}
