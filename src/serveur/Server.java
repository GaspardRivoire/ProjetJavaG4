package serveur;

import java.util.ArrayList;

public class Server {
	
	private int port;
	private ArrayList<ConnectedClient> clients;

	/**
	 * Server constructor 
	 * @param _port int port used by server
	 */
	Server(int _port) {
		this.port = _port;
		this.clients = new ArrayList<ConnectedClient>();
		
		Thread threadConnection = new Thread(new Connection(this));
		threadConnection.start();
	}

	/**
	 * Add a new client
	 * @param newClient ConnectedClient new Client to add
	 */
	public void addClient(ConnectedClient newClient) {
		String allClients = "";
		for (ConnectedClient client : clients) {
			client.sendMessage("Le client "+newClient.getId()+" vient de se connecter");
			allClients = allClients + " client " + client.getId() + ":";
		}
		this.clients.add(newClient);
		newClient.sendMessage("cØnnected:" + allClients);
	}
	
	/**
	 * Send message to all client
	 * @param m String message to send
	 * @param id int client who send message
	 */
	public void broadcastMessage(String m, int id) {
		for (ConnectedClient client : clients) {
			if (client.getId() != id) {
			client.sendMessage("Message de "+id+" : "+ m);
			}
		}
	}
	
	/**
	 * When client leave
	 * @param discClient ConnectedClient to remove
	 */
	public void disconnectedClient(ConnectedClient discClient) {
		discClient.closeClient();
		clients.remove(discClient);
		for (ConnectedClient client : clients) {
			client.sendMessage("Le client "+discClient.getId()+" nous a quitté");
		}
	}
	
	/**
	 * 
	 * @return port int 
	 */
	public int getPort() {
		return port;
	}

	/**
	 * 
	 * @param port int new port
	 */
	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * 
	 * @return clients ArrayList<ConnectedClient>
	 */
	public ArrayList<ConnectedClient> getClients() {
		return clients;
	}

	/**
	 * 
	 * @param clients ArrayList<ConnectedClient>
	 */
	public void setClients(ArrayList<ConnectedClient> clients) {
		this.clients = clients;
	}
}
