import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class dbconn {
    private static final String URL = "jdbc:mysql://localhost:3306/salle_sport";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
