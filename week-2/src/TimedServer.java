import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

class Worker implements Runnable
{
    private int sleepTime = 10;

    private Socket connection;
    private Semaphore semaphore;
    private final static Logger LOGGER = Logger.getLogger(Worker.class.getName());

    public Worker(Socket connection, Semaphore semaphore) {
        this.connection = connection;
        this.semaphore = semaphore;
    }

    public void run() {
        try {
            PrintWriter pout = new PrintWriter(connection.getOutputStream(), true);

            while (sleepTime > 0) {
                String s = (sleepTime == 1) ? " second." : " seconds.";
                pout.println("Sleeping " + sleepTime + " more " + s);
                Thread.sleep(1000);
                sleepTime -= 1;
            }

            // release the semaphore
            semaphore.release();

            // now close the socket connection
            connection.close();
        }
        catch (InterruptedException | IOException ie) {
            LOGGER.log(Level.WARNING, ie.getMessage());
        }
    }
}


public class TimedServer
{
    public static final int CLI_LIMIT = 5;
    public static final int PORT = 2500;

    private final static Logger LOGGER = Logger.getLogger(Worker.class.getName());

    public static void main(String[] args) {
        Socket connection;

        try {
            ServerSocket server = new ServerSocket(PORT);
            Semaphore semaphore = new Semaphore(CLI_LIMIT);

            while (true) {
                semaphore.acquire();
                connection = server.accept();
                Thread worker = new Thread(new Worker(connection, semaphore));
                LOGGER.log(Level.INFO, "Worker started, available permits: "+semaphore.availablePermits());
                worker.start();
            }
        }
        catch (IOException | InterruptedException e) {
            LOGGER.log(Level.WARNING, e.getMessage());
        }
    }
}