package server;

import java.net.*;
import java.util.ArrayList;

public class ServerDictionaryApp {
	
	protected DictionaryManager dm;
	private Socket clientSocket;
	private ServerSocket serverSocket = null;
	private int port;
	protected ArrayList<RequestThread> threads;
	private boolean serverRunning; // Status of server
	
	public ServerDictionaryApp(int port, String dictAddress)
	{
		dm = new DictionaryManager(dictAddress);
		this.port = port;
		threads = new ArrayList<RequestThread>();
		clientSocket = null;
		serverRunning = true;
	}
	
	/**
	 * Run the application.
	 */
	public void run()
	{
		try 
		{
			serverSocket = new ServerSocket(port);
			while (serverRunning)
			{
				System.out.println("Server listening on port " + port + " for connection ..."); 
				clientSocket = serverSocket.accept(); // listen for client connection
				
				// Create thread for request
				new RequestThread(this, clientSocket);
			}
			try // Stop all threads and close socket when server stops
			{
				if (serverSocket != null)
				{
					serverSocket.close(); // Close server
					for (int i=0; i<threads.size(); i++)
					{
						threads.get(i).stopThread(); // Close all threads
					}
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
				System.exit(0);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.exit(0);
		}
	}
}
