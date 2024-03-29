package sg.edu.nus.iss.server;

//sub process
//being spin off by the main
//connection must come in from the client side and connect to the server which instantiates an object
//it has a runnable, all acceptance of connections are handled here
//uses NetworkIO when want to read and write

import java.io.IOException;
import java.net.Socket;

public class CookieClientHandler implements Runnable{

    private Socket sock;
    private String cookieFile;

    public CookieClientHandler(Socket s, String cookieFile){
        this.sock = s;
        this.cookieFile = cookieFile;
    }
    
    @Override
public void run() {
    System.out.println("Starting a client thread");
    NetworkIO netIO = null;
    try {
        netIO = new NetworkIO(sock);
        String req = "";
        String randomCookieResp = "";
        while (true) {
            req = netIO.read();
            System.out.printf("[client] %s\n", req);
            if (req.trim().equals("exit"))
                break;
            if (req.trim().equals("get-cookie")) {
                System.out.printf("file -> %s\n", this.cookieFile);

                randomCookieResp = Cookie.getRandomCookie(this.cookieFile);
                netIO.write("cookie-text,"+ randomCookieResp);
            } else {
                netIO.write("error,invalid command");
            }
        }
        netIO.close();
        sock.close();

        System.out.println("Exiting the thread !");
    } catch (IOException e) {
        e.printStackTrace();
    }
}

    
        
    
}