package client;

import java.io.BufferedReader;
import java.io.IOException;

public class ClientReceive implements Runnable{
	
	private Client client;
	private BufferedReader in;

	public ClientReceive(Client _client, BufferedReader _in) {
		this.client = _client;
		this.in = _in;
	}

	public void run() {
		boolean isActive = true ;
		while(isActive) {
			String message = null;
			try {
				message = in.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (message != null) {
				System.out.println("\nMessage reçu : " + message);
				if(message.contains("cØnnected:"))	{
					this.client.clientPanel.fillConnection(message.split(":"));
				} else {
					this.client.clientPanel.receivedMessage.changeSomething(message);
					if(message.contains("vient de se connecter")) {
						String idClient = message.split("vient")[0].split("Le")[1];
						this.client.clientPanel.newConnection(idClient);
					} else if(message.contains("nous a quitté")) {
						String idClient = message.split("nous")[0].split("Le")[1];
						this.client.clientPanel.endConnection(idClient);
					}
				}
			} else {
				isActive = false;
			}
		}
		try {
			client.disconnectedServer();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}

}
