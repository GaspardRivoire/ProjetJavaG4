package client;

import java.io.PrintWriter;
import java.util.Scanner;

public class ClientSend implements Runnable{
	
	private PrintWriter out;

	/**
	 * Constructor
	 * @param _out PrintWriter
	 */
	public ClientSend(PrintWriter _out) {
		this.out = _out;
	}

	/**
	 * Wait a new message from the current client.
	 */
	public void run() {
		Scanner sc = new Scanner(System.in);
		while (true) {
			System.out.print("Votre message >> ");
			String m =  sc.nextLine();
			out.println(m);
			out.flush();
		}
	}

}
