package client;

import java.io.*;
import java.net.*;



public class Client {

	ClientThread clientThread;
	boolean shouldRun = true; 
	
    public static void main(String[] args) throws IOException {
    	new Client();
}
    
    public Client() {
		 try {
			 MulticastSocket  clientSocket = new MulticastSocket ();
			 clientThread = new ClientThread(clientSocket, this);
			 clientThread.start();
			listenForInput();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		 
	}
    
    public void listenForInput() throws IOException {
		 BufferedReader clientRead =new BufferedReader(new InputStreamReader(System.in));
		 
		while(true) {
			while(clientRead.readLine().isEmpty()) {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			String input = clientRead.readLine();
			
			clientThread.sendDataToServer(input);

			
		}
}
}