package client;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application{ 
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		String address = "127.0.0.1";
		Integer port = 2050;
		Client client = new Client(address, port, primaryStage);
	}

}
