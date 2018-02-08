package serveur;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ConnectedClient implements Runnable{
	
	static int idCounter;
	private int id;
	private Server server;
	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;	

	/**
	 * Constructor
	 * @param _server
	 * @param _socket
	 */
	public ConnectedClient(Server _server, Socket _socket) {
		this.server = _server;
		this.socket = _socket;
		this.id = idCounter;
		idCounter++;
		
		try {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Nouvelle connexion, id = " + id);
	}
	
	/**
	 * Run the client
	 */
	public void run() {
		boolean isActive = true;
		while (isActive) {
			String message = null;
			try {
				message = in.readLine();
			} catch (IOException e1) {
				//e1.printStackTrace();
			}
			if(message!=null) {
				try {
					server.broadcastMessage(message, id);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			else {
				try {
					server.disconnectedClient(this);
					isActive = false;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
	public void sendMessage(String m) {
		this.out.println(m);
		this.out.flush();
	}
	
	public void closeClient() {
		try {
			this.in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.out.close();
		try {
			this.socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static int getIdCounter() {
		return idCounter;
	}

	public static void setIdCounter(int idCounter) {
		ConnectedClient.idCounter = idCounter;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Server getServer() {
		return server;
	}

	public void setServer(Server server) {
		this.server = server;
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public BufferedReader getIn() {
		return in;
	}

	public void setIn(BufferedReader in) {
		this.in = in;
	}

	public PrintWriter getOut() {
		return out;
	}

	public void setOut(PrintWriter out) {
		this.out = out;
	}
}
