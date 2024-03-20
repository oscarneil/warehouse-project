package gjt.usblab.Event;
import javax.servlet.*;             // Tomcat 9
import javax.servlet.http.*;        // Tomcat 9
import javax.servlet.annotation.*;  // Tomcat 9

public class onShutDown implements ServletContextListener {
    
    public void contextDestroyed(ServletContextEvent event) {
        // Place here the code to run just before the application goes down
    }
}
