package serveur;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Connection implements Runnable {
	
	private Server server;
	private ServerSocket serverSocket;

	/**
	 * Constructor
	 * @param _server Server
	 */
	public Connection(Server _server) {
		this.server = _server;
		try {
			this.serverSocket = new ServerSocket(server.getPort());
			this.serverSocket.setReuseAddress(true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Wait new connection
	 */
	public void run() {
		while(true)	{
			try {
				Socket sockNewClient = serverSocket.accept();
				ConnectedClient newClient = new ConnectedClient(server, sockNewClient);
				server.addClient(newClient);
				Thread threadNewClient = new Thread(newClient);
				threadNewClient.start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
