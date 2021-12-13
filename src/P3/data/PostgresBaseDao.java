package P3.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class PostgresBaseDao {

    private static final String DB_DRIV = "oracle.jdbc.driver.OracleDriver";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/ovchip?user=postgres&password=Opnieuwinloggen25";
    private static final String DB_USER = "postgres";
    private static final String DB_PASS = "password";


    public Connection getConnection() {
        Connection result = null;
        try {
            result = DriverManager.getConnection(DB_URL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }





    /*public void CloseConnection() throws SQLException {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }*/
}
