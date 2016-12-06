import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main implements Runnable {
    private final static Logger LOGGER = Logger.getLogger(Main.class.getName());
    private final static Semaphore bridge = new Semaphore(1);

    private static String farmerNorthLocation = "north";
    private static String farmerSouthLocation = "south";

    private String farmer;

    private Main(String farmer) {
        this.farmer = farmer;
    }

    public static void main(String[] args) {
        Thread farmerNorth = new Thread(new Main("n"));
        Thread farmerSouth = new Thread(new Main("s"));

        farmerNorth.start(); // farmerNorth gaat naar de bridge toe
        farmerSouth.start(); // farmerSouth gaat naar de bridge toe

//        Thread[] threads = new Thread[100];
//        for (int i = 0; i < threads.length ; i++) {
//            threads[i] = new Thread(new Main(i % 2 == 0 ? "n" : "s"));
//            threads[i].start();
//        }
    }

    @Override
    public void run() {
        LOGGER.log(Level.WARNING, "Iemand is bij de bridge aangekomen. Aantal wachtende voor de bridge: "+Main.bridge.getQueueLength());
        try {
            Main.bridge.acquire(); // vraag de bridge op
            if ("n".equals(this.farmer)) {
                // verander de locatie van farmer == "n"
                Main.farmerNorthLocation = Main.farmerNorthLocation.equals("north")
                        ? "south" : "north";
                LOGGER.log(Level.INFO, "Boer: "+this.farmer+", is de bridge over en in: "+Main.farmerNorthLocation);
            }
            if ("s".equals(this.farmer)) {
                // verander de locatie van farmer == "s"
                Main.farmerSouthLocation = Main.farmerSouthLocation.equals("north")
                        ? "south" : "north";
                LOGGER.log(Level.INFO, "Boer: "+this.farmer+", is de bridge over en in: "+Main.farmerSouthLocation);
            }
            Main.bridge.release(); // geef de bridge af
            LOGGER.log(Level.WARNING, "Iemand is over de bridge gereden. Aantal wachtende voor de bridge: "+Main.bridge.getQueueLength());
        }
        catch (Exception e) {
            LOGGER.log(Level.INFO, e.getMessage());
        }
    }
}
