package client;

import java.awt.EventQueue;

public class ClientDictionaryApp {

	private ClientGUI window;
	private ClientSocket socket;
	private ButtonListener btnListener;

	/**
	 * Initialize GUI and ActionListener
	 */
	public void initialize()
	{
		// Create buttonListener for submit button		
		btnListener = new ButtonListener(this);

		// Create GUI window
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new ClientGUI(btnListener);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Send message to server.
	 */
	public void sendMessage(ClientMessage msg)
	{
		socket = new ClientSocket(this); // Initialize socket
		String response = socket.sendMessage(msg); // Send the Message
		if (response != null)
			interpretServerResponse(response); // Process server's response
	}
	
	/**
	 * Interpret the response from server
	 * Format: Operation,StatusMessage(,Meaning1,Meaning2,...)
	 */
	private void interpretServerResponse(String response)
	{
		String components[] = response.split(",");
		displayInfo(components[1]);
		if (components.length >= 3) // response for search operation
		{
			for (int i=2; i<components.length; i++)
			{
				displayInfo((i-1) + ". " + components[i]);
			}
		}
	}

	/**
	 * Display information on TextArea of GUI
	 */
	public void displayInfo(String info)
	{
		window.displayFeedback(info);
	}
	
	public void disableSubmit()
	{
		window.disableSubmit();
	}
	
	public void enableSubmit()
	{
		window.enableSubmit();
	}
}
