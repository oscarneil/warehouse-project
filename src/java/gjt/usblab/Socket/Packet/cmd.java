package gjt.usblab.Socket.Packet;

public enum cmd {
    
    RED_ON("RED-ON"),
    RED_OFF("RED-OFF"),
    GREEN_ON("GREEN-ON"),
    GREEN_OFF("GREEN-OFF"),
    YELLOW_ON("YELLOW-ON"),
    YELLOW_OFF("YELLOW-OFF"),
    CHECK_ON("CHECK-ON"),
    CHECK_OFF("CHECK-OFF"),
    LED_ON("LED-ON"),
    LED_OFF("LED-OFF");


    public String c;
    private cmd(String command){
        this.c = command;
    }
    @Override
    public String toString(){
        return c;
    }
}
