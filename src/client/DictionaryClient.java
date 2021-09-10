package client;


public class DictionaryClient {

	private static ClientDictionaryApp app;
	
	public static void main(String args[])
	{
		app = new ClientDictionaryApp();
		app.initialize();
	}
}
