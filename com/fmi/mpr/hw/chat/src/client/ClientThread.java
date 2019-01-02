package client;


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class ClientThread extends Thread {
	MulticastSocket socket;
	InetAddress address;
	Client client;

	public ClientThread(MulticastSocket  clientSocket, Client client) throws IOException {
		socket = clientSocket;
		address = InetAddress.getByName("230.0.0.1");
		socket.joinGroup(address);
		this.client = client;
	}

	DatagramPacket packet;

	public void sendDataToServer(String reply) throws IOException {
		byte[] sendbuffer = new byte[1024];
		
		sendbuffer = reply.getBytes();
		packet = new DatagramPacket(sendbuffer, sendbuffer.length, 4446);
		socket.send(packet);

	}
	
	public byte[] recieveDataFromServer() throws IOException {
		byte[] receivebuffer = new byte[1024];

		DatagramPacket receivePacket = new DatagramPacket(receivebuffer, receivebuffer.length);
		socket.receive(receivePacket);
		return receivePacket.getData();

	}

	public void run() {
		while (true) {

			try {

				while (recieveDataFromServer() == null) {
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				String serverdata = new String(recieveDataFromServer());
				System.out.println(serverdata);
				
				

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


	}

}
}
