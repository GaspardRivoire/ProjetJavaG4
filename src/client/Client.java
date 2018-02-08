package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Client {
	
	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;
	public ClientPanel clientPanel;

	/**
	 * Constructor a new client 
	 * @param _address
	 * @param _port
	 * @param primaryStage
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public Client(String _address, int _port, Stage primaryStage) throws UnknownHostException, IOException {
		this.socket = new Socket(_address, _port);
		try {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		clientPanel = new ClientPanel(this.out);
		Group root = new Group();
		root.getChildren().add(clientPanel);
		Scene scene = new Scene(root, 600, 500);
		primaryStage.setTitle("Mon chat");
		primaryStage.setScene(scene);
		primaryStage.show();	
		
		Thread ClientSend = new Thread(new ClientSend(this.out));
		ClientSend.start();
		
		Thread ClientReceive = new Thread(new ClientReceive(this, this.in));
		ClientReceive.start();
	}
	
	/**
	 * When the client leave the channel.
	 * @throws IOException
	 */
	public void disconnectedServer() throws IOException
	{
		in.close();
		out.close();
		socket.close();
		System.exit(0);
	}

}