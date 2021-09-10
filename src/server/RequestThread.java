package server;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class RequestThread extends Thread {
	
	private final int OPERATION = 0;
	private final int WORD = 1;
	private final int MEANINGS = 2;
	
	private ServerDictionaryApp sdApp;
	private Socket socket;
	private BufferedReader in;
	private BufferedWriter out;
	
	public RequestThread(ServerDictionaryApp sdApp, Socket socket)
	{
		this.sdApp = sdApp;
		this.socket = socket;
		try 
		{
			in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	
		System.out.println("Client from " + socket.getInetAddress().getHostName() 
				+ " connected, port no. " + socket.getLocalPort());
		sdApp.threads.add(this);
		serveRequest();
	}
	
	/**
	 * Serve the client's request
	 */
	public void serveRequest()
	{
		try 
		{
			String clientMsg = in.readLine();
			while(clientMsg != null) 
			{
				System.out.println("Message from client: " + clientMsg);
				String components[] = clientMsg.split(",");
				String serverFeedback = "";

				// Search word
				if (components[OPERATION].equals("Search"))
				{
					serverFeedback = generateSearchFeedbackMsg(components[WORD]);
				}
				// Add word
				else if (components[OPERATION].equals("Add"))
				{
					// Copy meanings to ArrayList
					ArrayList<String> meanings = new ArrayList<String>();
					for (int i=MEANINGS; i<components.length; i++)
					{
						meanings.add(components[i]);
					}
					serverFeedback = generateAddFeedbackMsg(components[WORD], meanings);
				}
				// Remove word
				else if (components[OPERATION].contentEquals("Remove"))
				{
					serverFeedback = generateRemoveFeedbackMsg(components[WORD]);
				}
				else
				{
					serverFeedback = "Invalid,Operation is not valid. Please select again. ";
				}
				
				out.write(serverFeedback + "\n");
				out.flush();
				System.out.println(serverFeedback);
				System.out.println("Response sent");
				
				clientMsg = in.readLine();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			stopThread();
		}
	}
	
	/**
	 * Generate server's message for search operation.
	 * Format: Search,StatusMessage,Meaning1,Meaning2,...
	 */
	private String generateSearchFeedbackMsg(String word)
	{
		ArrayList<String> meanings = sdApp.dm.searchWord(word);
		String msg = "";
		if (meanings == null)
		{
			msg = "Search,Word not found in the dictionary.";
		}
		else
		{
			msg = "Search,The meaning of word \"" + word + "\" is: ";
			for (int i=0; i<meanings.size(); i++)
			{
				msg += ("," + meanings.get(i));
			}
		}
		return msg;
	}
	
	/**
	 * Generate server's message for add operation.
	 * Format: Add,StatusMessage
	 */
	private String generateAddFeedbackMsg(String word, ArrayList<String> meanings)
	{
		String msg = "";
		DictionaryManager.AddStatus status = sdApp.dm.addWord(word, meanings);
		if (status == DictionaryManager.AddStatus.ADD_WORD)
		{
			msg = "Add,Word added to dictionary.";
		}
		else if (status == DictionaryManager.AddStatus.ADD_MEANINGS)
		{
			msg = "Add,New meanings added to dictionary.";
		}
		else if (status == DictionaryManager.AddStatus.REDUNDANT_WORD)
		{
			msg = "Add,Word and meaning(s) already exist.";
		}
		return msg;
	}
	
	/**
	 * Generate server's message for remove operation.
	 * Format: Remove,StatusMessage
	 */
	private String generateRemoveFeedbackMsg(String word)
	{
		String msg = "";
		DictionaryManager.RemoveStatus status = sdApp.dm.removeWord(word);
		if (status == DictionaryManager.RemoveStatus.NOTFOUND)
		{
			msg = "Remove,Word does not exist in dictionary.";
		}
		else
		{
			msg = "Remove,Word removed from dictionary.";
		}
		return msg;
	}
	
	/**
	 * Stop this thread
	 */
	public void stopThread()
	{
		try
		{
			sdApp.threads.remove(this);
			System.out.println("client socket closing");
			socket.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
}
