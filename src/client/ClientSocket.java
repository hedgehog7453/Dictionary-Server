package client;
import java.io.*;
import java.net.*;


public class ClientSocket {
	
	private ClientDictionaryApp cdApp;
	
	private Socket socket = null;
	
	public ClientSocket(ClientDictionaryApp cdApp)
	{
		this.cdApp = cdApp;
	}
	
	/**
	 * Send message to server
	 */
	public String sendMessage(ClientMessage msg)
	{
		String received = null;
		try
		{
			socket = new Socket(msg.getHostName(), msg.getPort());
			// Send
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));
			out.write(msg.getMessage() + "\n");
			out.flush();
			cdApp.displayInfo("Message sent to server: " + msg.getMessage());
			
			// Receive
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			received = in.readLine(); 
		}
		catch (SocketException e)
		{
			cdApp.displayInfo("Connection failed. Please try again later.");
		}
		catch (UnknownHostException e)
		{
			cdApp.displayInfo("Connection failed. Please check the address and port number and try again.");
		}
		catch (IOException e)
		{
			cdApp.displayInfo("Unexpected error has occurred. Please try again.");
		} 
		finally
		{
			// Close the socket
			if (socket != null)
			{
				try
				{
					socket.close();
				}
				catch (Exception e) 
				{
					cdApp.displayInfo("Unexpected error has occurred. Please try again.");
				}
			}
		}
		cdApp.enableSubmit();
		return received;
		
	}
	
}
