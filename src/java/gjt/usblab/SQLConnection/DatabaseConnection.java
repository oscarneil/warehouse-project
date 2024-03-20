package gjt.usblab.SQLConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
//    private static String url = "jdbc:mysql://172.17.0.2:3306/warehouse";
    private static String url = "jdbc:mysql://localhost:3306/warehouse";
    private static String user = "warehouse";
    private static String password = "warehouse"; // 304db
    static{
        try{
            Class.forName("com.mysql.jdbc.Driver");
        }catch(Exception e){

        }
    }
    public static Connection getConnection() throws SQLException {

        return DriverManager.getConnection(url, user, password);
    }
}