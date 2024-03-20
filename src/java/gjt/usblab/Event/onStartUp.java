package gjt.usblab.Event;
import javax.servlet.*;             // Tomcat 9
import javax.servlet.http.*;        // Tomcat 9

import gjt.usblab.Main.main;

import javax.servlet.annotation.*;  // Tomcat 9

@WebListener
public class onStartUp implements ServletContextListener{
    

    public void contextInitialized(ServletContextEvent event) {
        System.out.println("system Start");
        Thread t = new Thread(new main(event.getServletContext().getRealPath("")));
        t.start();
    }

    public void contextDestroyed(ServletContextEvent event) {
        // Place here the code to run just before the application goes down
    }

}
