package client;

import java.util.Observable;


public class Message extends Observable {

	String message;
	
	/**
	 * 
	 * @param _message String
	 */
	public Message(String _message) {
		this.message = _message;
	}
	
	/**
	 * 
	 * @param newMessage String new received message
	 */
	public void changeSomething(String newMessage) {
	      // Notify observers of change
	      this.message = newMessage;
	      setChanged();
	      notifyObservers(this.message);
	}

}
