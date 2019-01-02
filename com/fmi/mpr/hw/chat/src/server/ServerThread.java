package server;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;


public class ServerThread extends Thread {
	BufferedReader serverRead = new BufferedReader(new InputStreamReader(System.in));
	
	Server server;
	protected DatagramSocket serverSocket;
	protected boolean shouldRun = true;

	
	public ServerThread(DatagramSocket serverSocket, Server server) throws IOException {
		this.serverSocket = serverSocket;
		this.server = server;
	}

	public void sendDataToClients(String data) throws IOException {
		byte[] sendbuffer = new byte[1024];
		sendbuffer = data.getBytes();
		InetAddress IP = InetAddress.getByName("230.0.0.1");
		DatagramPacket sendPacket = new DatagramPacket(sendbuffer, sendbuffer.length, IP, 4446);
		serverSocket.send(sendPacket);
	}

	

	public void run() {
		while (shouldRun) {
			try {
				while (serverRead.readLine().isEmpty()) {
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				sendDataToClients(serverRead.readLine());

			} catch (IOException e) {
				e.printStackTrace();
				shouldRun = false;
			}
		}
		serverSocket.close();
	}
}