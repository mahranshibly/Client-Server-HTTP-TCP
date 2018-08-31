package multithreadedServer;


/**
 *
 * @author MahranSh
 */

import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        SimpleMultithreadedServer server = new SimpleMultithreadedServer(45000);
        new Thread(server).start();
        System.out.println("Server is waiting to connect");
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            System.out.println("InterruptedException: " + e);
        }
      
       System.out.println("Stopping Server");
       server.stop();

    }

}
