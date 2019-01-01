package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ServerConnection extends Thread{
	
	Server server;
	Socket socket;
	DataInputStream dInput;
	DataOutputStream dOutput;
	
	boolean shouldRun = true;

	public ServerConnection(Socket socket, Server server) {
		super("ServerConnectionThread");
		this.socket= socket;
		this.server = server;
	}
	
	//TODO: Must be data
	public void sendDataToClient(String text) {
		try {
			dOutput.writeUTF(text);
			dOutput.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendDataToAllClients(String text) {
		for(int index = 0; index<server.connections.size(); index++) {
			ServerConnection serverConnection = server.connections.get(index);
			serverConnection.sendDataToClient(text);
		}
	}
	
	public void run() {
		
		try {
			dInput = new DataInputStream(socket.getInputStream());
			dOutput = new DataOutputStream(socket.getOutputStream());
		
			while(shouldRun) {
				while(dInput.available() == 0) {
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
				String textIn = dInput.readUTF();
				sendDataToAllClients(textIn);
				
			}
			
			socket.close();
			dInput.close();
			dOutput.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}