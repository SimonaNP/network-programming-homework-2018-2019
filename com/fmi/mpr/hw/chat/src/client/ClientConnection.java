package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientConnection extends Thread {

	Client client;
	Socket socket;
	DataInputStream dInput;
	DataOutputStream dOutput;
	boolean shouldRun = true;

	public ClientConnection(Socket socket, Client client) {
		this.socket = socket;
		this.client = client;
	}

	public void sendDataToServer(String text) {
		
			try {
				dOutput.writeUTF(text);
				dOutput.flush();
			} catch (IOException e) {
				e.printStackTrace();
				close();
			}
		
	}

	public void run() {
		try {
			dInput = new DataInputStream(socket.getInputStream());
			dOutput = new DataOutputStream(socket.getOutputStream());

			while (shouldRun) {
				try {
					while (dInput.available() == 0) {
						try {
							Thread.sleep(1);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					String reply = dInput.readUTF();
					System.out.println(reply);
				} catch (IOException e) {
					e.printStackTrace();
					close();
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void close() {
		try {
			socket.close();
			dInput.close();
			dOutput.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}