/**
 * TimedClient.java
 *
 * @author Gagne, Galvin, Silberschatz
 * Operating System Concepts with Java - Eighth Edition
 * Copyright John Wiley & Sons - 2010.
 *
 * Edited by Peter Schriever & Femke Hoornveld at 2016-12-01:
 * Added a counting semaphore and made it possible to start multiple clients
 */

import java.net.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TimedClient implements Runnable
{
	private final static Logger LOGGER = Logger.getLogger(Worker.class.getName());
    private Thread[] cliThreads;

	public static void main(String[] args) throws IOException {
        TimedClient clients = new TimedClient(10);
	}

    public TimedClient(int clients) {
        cliThreads = new Thread[clients];
        for (int i = 0; i < clients ; i++) {
            cliThreads[i] = new Thread(this);
            cliThreads[i].start();
        }
    }

    public TimedClient() {
        this(1);
    }

    @Override
    public void run() {
        InputStream in = null;
        BufferedReader bin = null;

        try (Socket sock = new Socket("127.0.0.1", 2500)) {
            in = sock.getInputStream();
            bin = new BufferedReader(new InputStreamReader(in));

            String line;
            while ((line = bin.readLine()) != null)
                System.out.println(line);
        }
        catch (IOException ioe) {
            LOGGER.log(Level.WARNING, ioe.getMessage());
        }
    }
}
