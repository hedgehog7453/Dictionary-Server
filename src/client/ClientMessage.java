package client;

public class ClientMessage {

	private String hostName;
	private int port;
	
	private String operation;
	private String word;
	private String meanings[];
	
	public ClientMessage(String hostName, int port, String op, String word, String[] meanings)
	{
		this.hostName = hostName;
		this.port = port;
		this.operation = op;
		this.word = word;
		this.meanings = meanings.clone();
	}
	
	public String getHostName()
	{
		return hostName;
	}
	
	public int getPort() {
		return port;
	}

	public String getMessage()
	{
		// Generate message for server
    	String clientMessage = operation + "," + word;
    	for (int i=0; i<meanings.length; i++)
    	{
    		clientMessage += ",";
    		clientMessage += meanings[i];
    	}
    	return clientMessage;
	}
	
	
	
}
