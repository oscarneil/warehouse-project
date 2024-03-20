package gjt.usblab.utils;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import gjt.usblab.Socket.baseSocket;
import gjt.usblab.nodeCluster.node;


public class logger {
    public static logger instance = null;
    public List<String> logs = new ArrayList();
    private LocalDateTime getCurrent(){
        LocalDateTime localDateTime = LocalDateTime.now();
        localDateTime = localDateTime.plusHours(8);
        return localDateTime;
    }
    private HashMap<Integer,List<String>> node_logs = new HashMap();
    public static enum LogLevel{
        INFO,
        WARNING,
        ERROR;
    };

    private logger(){
        // show data at log path
        log("Logger Enabled");
    }
    public void log(String arg){
        log(arg,LogLevel.INFO);
    }


    public void node_log(String arg,node node){
        node_log(arg,LogLevel.INFO,node);
    }
    public void node_write(String arg,node node){
        node_write(arg,LogLevel.INFO,node);
    }
    public void node_write(String arg,LogLevel ll,node node){

        if (!node_logs.containsKey(node.getID())) {
            node_logs.put(node.getID(), new ArrayList());
            node_logs.get(node.getID()).add("");
        }
        String s = node_logs.get(node.getID()).get(0);

         node_logs.get(node.getID()).set(0, s+arg);
    }

    public void node_log(String arg,LogLevel ll,node node){
        //SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
        //Date date = new Date();
        String pattern = "dd/MM/yyyy HH:mm:ss"; // Change this pattern as needed

        // Create a DateTimeFormatter object using the pattern
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);

        // Format the LocalDateTime object to a string
        String timestamp = getCurrent().format(formatter);


        if (!node_logs.containsKey(node.getID())) node_logs.put(node.getID(), new ArrayList());
        if (ll.equals(LogLevel.INFO)){
            node_logs.get(node.getID()).add("["+timestamp+"] &lt;INFO&gt; "+arg);
        }else if(ll.equals(LogLevel.ERROR)){
            node_logs.get(node.getID()).add("["+timestamp+"] &lt;<ERROR&gt; "+arg);
        }
        else{
            node_logs.get(node.getID()).add("["+timestamp+"] &lt;WARNING&gt; "+arg);
        }
    }

    public void log(String arg,LogLevel ll){
        String pattern = "dd/MM/yyyy HH:mm:ss"; // Change this pattern as needed

        // Create a DateTimeFormatter object using the pattern
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);

        // Format the LocalDateTime object to a string
        String timestamp = getCurrent().format(formatter);
        if (ll.equals(LogLevel.INFO)){
            logs.add("["+timestamp+"] <INFO> "+arg);
        }else if(ll.equals(LogLevel.ERROR)){
            logs.add("["+timestamp+"] <ERROR> "+arg);
        }
        else{
            logs.add("["+timestamp+"] <WARNING> "+arg);
        }
    }

    public List<String> getLogs(){

        return logs;
    }

    public List<String> getNodeLogs(int nodeid){

        return node_logs.get(nodeid);
    }

    public List<HashMap<String,Object>> fetchlog(int from){
        List<HashMap<String,Object>> ret = new ArrayList<>();
        for (int i = from; i < logs.size();i++){
            HashMap<String,Object> r = new HashMap<>();
            r.put("text", logs.get(i));
            r.put("id", i);
            ret.add(r);
        }
        return ret;
    }
    public static logger getInstance(){
        if (instance == null) instance = new logger();
        return instance;
    }
}