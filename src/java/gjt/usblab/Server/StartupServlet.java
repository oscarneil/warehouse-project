package gjt.usblab.Server;

import gjt.usblab.SQLConnection.SQLConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class StartupServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    public void init() throws ServletException {
        super.init();
        clearTable();
    }

    private void clearTable() {
        try {
            Server.getInstance().sqlConnection.clearNodeDevice();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
