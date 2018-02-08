package client;

import java.io.PrintWriter;
import java.util.Observable;
import java.util.Observer;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class ClientPanel extends Parent implements Observer {

	PrintWriter oute;
	TextArea tapText;
	ScrollPane scrollReceivedText;
	TextFlow receivedText;
	Button sendBtn;
	Button clearBtn;
	Text connected;
	TextArea textMembers;
	Message receivedMessage = new Message("");
	
	/**
	 * Constructor of all interface and add button event.
	 * @param out PrintWriter
	 */
	public ClientPanel(PrintWriter out) {		
		this.oute = out;
		this.receivedMessage.addObserver(this);	
		
		this.receivedText = new TextFlow();	
		
		this.scrollReceivedText = new ScrollPane();
		this.scrollReceivedText.setLayoutX(50);
		this.scrollReceivedText.setLayoutY(50);
		this.scrollReceivedText.setPrefHeight(280);
		this.scrollReceivedText.setPrefWidth(400);
		this.scrollReceivedText.setFitToWidth(true);
		
		this.scrollReceivedText.setContent(receivedText);
		this.scrollReceivedText.vvalueProperty().bind(receivedText.heightProperty());
		
		this.getChildren().add(scrollReceivedText);
		
		this.tapText = new TextArea();
		this.tapText.setLayoutX(50);
		this.tapText.setLayoutY(350);
		this.tapText.setPrefHeight(100);
		this.tapText.setPrefWidth(400);
		this.tapText.setWrapText(true);
		this.getChildren().add(tapText);
		
		this.sendBtn = new Button();
		this.sendBtn.setLayoutX(470);
		this.sendBtn.setLayoutY(350);
		this.sendBtn.setPrefHeight(20);
		this.sendBtn.setPrefWidth(100);
		this.sendBtn.setText("Envoyer");
		this.getChildren().add(sendBtn);
		
		this.clearBtn = new Button();
		this.clearBtn.setLayoutX(470);
		this.clearBtn.setLayoutY(380);
		this.clearBtn.setPrefHeight(20);
		this.clearBtn.setPrefWidth(100);
		this.clearBtn.setText("Effacer");
		this.getChildren().add(clearBtn);
		
		this.connected = new Text();
		this.connected.setLayoutX(470);
		this.connected.setLayoutY(30);
		this.connected.setText("Connectés");
		this.getChildren().add(connected);
		
		this.textMembers = new TextArea();
		this.textMembers.setLayoutX(470);
		this.textMembers.setLayoutY(50);
		this.textMembers.setPrefHeight(280);
		this.textMembers.setPrefWidth(100);
		this.textMembers.setEditable(false);
		
		this.getChildren().add(textMembers);
		
		clearBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				tapText.clear();
			}
		});
		
		sendBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {		
				if(!tapText.getText().isEmpty())
				{
					Label tapedLabel = new Label();
					tapedLabel.setWrapText(true);
					tapedLabel.setText(tapText.getText());
					tapedLabel.setTextFill(Color.BLUE);
					tapedLabel.setAlignment(Pos.CENTER_RIGHT);
					tapedLabel.setPrefWidth(375);
					receivedText.getChildren().add(tapedLabel);
					oute.println(tapText.getText());
					oute.flush();
					tapText.clear();					
				}
			}
		});
		
		tapText.setOnKeyPressed(new EventHandler<KeyEvent>() {
		    public void handle(KeyEvent keyEvent) {
		        if (keyEvent.getCode() == KeyCode.ENTER)  {
		        	tapText.setText(tapText.getText().replace("\n", ""));
		        	sendBtn.fire();
		        	tapText.clear();
		        }
		    }
		});
	}

	/**
	 * When new message is here, update the TextFlow using obervable 
	 */
	public void update(Observable arg0, Object arg1) {	
		Platform.runLater(new Runnable() {
            public void run() {
            	Label tapedLabel = new Label();
    			tapedLabel.setWrapText(true);
    			tapedLabel.setText(receivedMessage.message);
    			tapedLabel.setPrefWidth(400);
    			tapedLabel.setTextFill(Color.DARKSLATEGRAY);			
    			receivedText.getChildren().add(tapedLabel);
            }
		});
	}
	
	/**
	 * Fill the textArea with connected client
	 * @param tabClient String[] : all id of connected clients
	 */
	public void fillConnection(String[] tabClient) {
		for(int index = 1; index < tabClient.length; index++) {
			newConnection(tabClient[index]);
		}
	}
	
	/**
	 * Add new client to textArea when connected
	 * @param idClient String : id of the client to add
	 */
	public void newConnection(final String idClient)
	{
		Platform.runLater(new Runnable() {
            public void run() {
            	textMembers.appendText(idClient + "\n");
            }
		});
	}
	
	/**
	 * Remove client from textArea
	 * @param idClient String : id of the client to remove
	 */
	public void endConnection(String idClient)
	{				
		String allText = textMembers.getText();
		final String newText = allText.split(idClient + "\n")[0] + allText.split(idClient + "\n")[1];
		
		Platform.runLater(new Runnable() {
            public void run() {
            	textMembers.clear();
    			textMembers.appendText(newText + "\n");
            }
		});
		
		
		/*Platform.runLater(()->{			
			textMembers.clear();
			textMembers.appendText(newText + "\n");
		});*/
	}
}
