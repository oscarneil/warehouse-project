package gjt.usblab.bridge;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.time.Duration;

// this shoulbe be a bridge that connect web and esp12s

public class QRCodeBridge {
    public static HashMap<Integer,LocalDateTime> mp = new HashMap<>();
    public static boolean codeAvailable(int code){
        if (!mp.containsKey(code)) return false;

        if (LocalDateTime.now().isAfter(mp.get(code))) {
            
            return false;

        }
        return true; 
    }  
    public static void codeEnable(int code){
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime destTime = currentTime.plusMinutes(15);
        System.out.println("QRCODE NO:"+code+ " Enabled");
        mp.put(code, destTime);
    }
    public static ArrayList<Long> getDistance(int code){
        LocalDateTime currentTime = LocalDateTime.now();
        Duration duration = Duration.between(currentTime, mp.get(code));

        long minutes = duration.toMinutes();
        long seconds = duration.minusMinutes(minutes).getSeconds();
        ArrayList<Long> ret = new ArrayList<>();
        ret.add(minutes);
        ret.add(seconds);
        return ret;
    }
}
