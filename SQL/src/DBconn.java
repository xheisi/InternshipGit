import java.sql.*;
import javax.sql.*;

public class DBconn {
    private static final String HOST = "localhost";
    private static final String PORT = "3306";
    private static final String DATABASE = "internship-java";
    private static final String USER     = "root";
    private static final String PASSWORD = "";
    private static final String URL      = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE;

    //private static final String URL      = "jdbc:mysql://localhost:3306/internship-java";
    //private static Connection connection = null;

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
