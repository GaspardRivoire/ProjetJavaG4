package serveur;

/**
 * start a server. Reads the server's port from the command line argument
 * @author Remi Watrigant
 *
 */
public class MainServer {
	/**
	 * creates a new server
	 * @param args String[]
	 */
	public static void main(String[] args) {

		try {
			if (args.length != 1) {
				printUsage();
			} else {
				Integer port = new Integer(args[0]);
				Server server = new Server(port);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	private static void printUsage() {
		System.out.println("java server.Server <port>");
		System.out.println("\t<port>: server's port");
	}
}
