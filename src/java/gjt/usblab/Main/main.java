package gjt.usblab.Main;

import java.io.File;

import gjt.usblab.Server.Server;
import gjt.usblab.utils.logger;

public class main implements Runnable{
    public static int cnt = 0;
    public static main instance = null;
    public static String applicationPath;
    public long t;
    public void onEnable(){
        t = System.currentTimeMillis();
        logger.getInstance().log("Server Start");
        Server server = Server.getInstance();
        server.setup(); 
        // executorService.scheduleAtFixedRate(new Runnable() {
        //     private final ExecutorService executor = Executors.newSingleThreadExecutor();
        //     private Future<?> lastExecution;
        //     @Override
        //     public void run() {
        //         if (lastExecution != null && !lastExecution.isDone()) {
        //             return;
        //         }
        //         lastExecution = executor.submit(task);
        //     }
        // }, 10, 1, TimeUnit.SECONDS);
        server.running();

        
    }

    public void run(){
        getInstance();
        
    }
    private main(){

    }
    public main(String base){
        main.applicationPath = base;
    }
    
    /**
     * getFilePath() + filename.xxx
     * ..../filename.xxx
     * @return
     */
    public String getFilePath(){
        String uploadFilePath = "file";
        File fileSaveDir = new File(uploadFilePath);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdirs();
        }
        return uploadFilePath + File.separator;
    }


    public static main getInstance(){
        if (instance == null) {
            instance = new main();
            instance.onEnable();
            cnt++;
        }
        return instance;
    }
}