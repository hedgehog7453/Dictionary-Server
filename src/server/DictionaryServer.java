package server;


public class DictionaryServer {
	
	private static ServerDictionaryApp app;
	
	public static void main(String args[])
	{
		app = new ServerDictionaryApp(Integer.parseInt(args[0]), args[1]);
		app.run();
	}
	
}
