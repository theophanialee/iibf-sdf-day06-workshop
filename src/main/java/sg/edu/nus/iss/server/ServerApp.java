package sg.edu.nus.iss.server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//main class to call the subsequent network methods and generate a random cookie
//main class that establishes the CookieClientHandler
//only can accept 2 connections concurrently
public class ServerApp 
{
    public static void main( String[] args )
    {
        int portNumber = 3000;
        // 3000 is a fallback value

        if (args.length > 0)
        portNumber = Integer.parseInt((args[0]));

        String cookieFile = args[1];

        ExecutorService threadPool = Executors.newFixedThreadPool(2);
      

        try (  ServerSocket server = new ServerSocket(portNumber)) {
            System.out.println("Server connected on port: " + portNumber);
            while(true) {
                //waiting for client to connect
                Socket socket = server.accept();
                //initiate a new thread to handle the client
                CookieClientHandler cch = new CookieClientHandler(socket, cookieFile);
                //submit the task to the thread pool
                threadPool.submit(cch);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
       

    }
    
}