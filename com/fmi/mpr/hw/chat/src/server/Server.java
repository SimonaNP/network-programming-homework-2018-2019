package server;

import java.io.IOException;
import java.net.DatagramSocket;

public class Server {
	
	DatagramSocket serverSocket ;
	  boolean shouldRun = true;
	  
	  
    public static void main(String[] args) throws java.io.IOException {
        new Server();
    }
    
public Server() {
	try {
		serverSocket = new DatagramSocket(4446);
		while (shouldRun) {
			//Socket socket = serverSocket.accept();
			ServerThread serverThread = new ServerThread(serverSocket, this);
			serverThread.start();
		}

	} catch (IOException e) {
		e.printStackTrace();
	}
}
}