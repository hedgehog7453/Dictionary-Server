package client;

import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

public class ButtonListener implements ActionListener
{
	private JTextField hostNameTextField;
	private JTextField portTextField;
	private JComboBox<String> operationComboBox;
	private JTextField wordTextField;
	private JTextArea meaningsTextArea;

	private ClientDictionaryApp cdApp;

	public ButtonListener(ClientDictionaryApp cdApp)
	{
		this.cdApp = cdApp;
	}

	/**
	 * Initialize ButtonListener with input fields in GUI
	 */
	public void initialize(JTextField hostName,  JTextField port, JComboBox<String> operation, JTextField word, JTextArea meanings, JTextArea feedback) 
	{
		this.hostNameTextField = hostName;
		this.portTextField = port;
		this.operationComboBox = operation;
		this.wordTextField = word;
		this.meaningsTextArea = meanings;
	}

	/**
	 * When Submit button is clicked, read and validate all inputs from GUI, 
	 * pass client message object to ClientDictionaryApp.
	 */
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		cdApp.disableSubmit();

		String hostName = hostNameTextField.getText();
		String portStr = portTextField.getText();
		String operation = (String)operationComboBox.getSelectedItem();
		String word = wordTextField.getText();
		String allMeanings = meaningsTextArea.getText();
		String[] meanings = normalizeMeanings(allMeanings);

		if (portValid(portStr) && operationValid(operation) && wordValid(word) && meaningsValid(operation, meanings))
		{
			// Format the input
			int port = Integer.parseInt(portStr);
			word = word.toLowerCase();
			ClientMessage newMsg = new ClientMessage(hostName, port, operation, word, meanings);
			cdApp.sendMessage(newMsg);
		}
		else
		{
			cdApp.enableSubmit();
		}
	}

	/**
	 * Remove strings that do not contain any alphabets, 
	 * and remove repeated strings.
	 */
	private String[] normalizeMeanings(String allMeanings)
	{
		String[] meanings = allMeanings.split("[\\r\\n]+");
		ArrayList<String> meaningsAl = new ArrayList<String>();
		for (String meaning : meanings)
		{
			if (meaning.matches(".*[A-Za-z].*")) // Check if line contains alphabets
			{
				meaningsAl.add(meaning);
			}
		}
		// Remove redundant meanings
		ArrayList<String> validMeaningsAl = new ArrayList<String>();
		for (int i=0; i<meaningsAl.size(); i++)
		{
			boolean add = true;
			if (i != meaningsAl.size()-1)
			{
				for (int j=i+1; j<meaningsAl.size(); j++)
				{
					if (meaningsAl.get(i).equals(meaningsAl.get(j))) 
					{
						add = false;
						break;
					}
				}
			}
			if (add)
			{
				validMeaningsAl.add(meaningsAl.get(i));
			}
			
		}
		return validMeaningsAl.toArray(new String[validMeaningsAl.size()]);  
	}

	/**
	 * Validate port number
	 */
	private boolean portValid(String portStr)
	{
		try
		{
			int port = Integer.parseInt(portStr);
			if (port > 65535 || port < 1)
			{
				cdApp.displayInfo("Port number is not valid. Please try again. ");
				return false;
			}
		}
		catch (NumberFormatException e)
		{
			cdApp.displayInfo("Port number is not valid. Please try again. ");
			return false;
		}
		return true;
	}
	
	/**
	 * Validate operation
	 */
	private boolean operationValid(String operation)
	{
		if (!operation.equals("Search") && !operation.equals("Add") && !operation.equals("Remove"))
		{
			cdApp.displayInfo("Operation is not valid. Please try again. ");
			return false;
		}
		return true;
	}

	/**
	 * Validate word
	 */
	private boolean wordValid(String word)
	{
		if (word.length() == 0)
		{
			cdApp.displayInfo("Please enter a word. ");
			return false;
		}
		for (int i=0; i<word.length(); i++)
		{
			if (!Character.isLetter(word.charAt(i)))
			{
				cdApp.displayInfo("Word is invalid. Please enter again. Note that only alphabets are accepted. ");
				return false;
			}
		}
		return true;
	}

	/**
	 * Validate word meanings
	 */
	private boolean meaningsValid(String operation, String[] meanings)
	{
		if (operation == "Add" && meanings.length == 0)
		{
			cdApp.displayInfo("Please enter the meanings of the word. ");
			return false;
		}
		return true;
	}

}