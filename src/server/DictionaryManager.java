package server;
import java.io.*;
import java.util.*;


public class DictionaryManager {
	
	private String dictAddress;
	private HashMap<String, ArrayList<String>> dict;
	enum AddStatus {
		  ADD_WORD,
		  ADD_MEANINGS,
		  REDUNDANT_WORD
	}
	enum RemoveStatus {
		  REMOVED,
		  NOTFOUND
	}
	
	public DictionaryManager(String dictAddress)
	{
		this.dictAddress = dictAddress;
		dict = new HashMap<String, ArrayList<String>>();
		LoadDictionary();
	}
	
	/** 
	 * Load local dictionary (CSV file) to HashMap
	 */
	private void LoadDictionary()
	{
		try
		{
			File file = new File(dictAddress);
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String entry;
			while((entry = br.readLine()) != null){
				String components[] = entry.split(",");
				String word = components[0];
				ArrayList<String> meanings = new ArrayList<String>();
				for (int i=1; i<components.length; i++)
				{
					meanings.add(components[i]);
				}
				dict.put(word, meanings);
			}
			fr.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	/**
	 * Return ArrayList of meanings if the word is in the dictionary.
	 * If the word is not in the dictionary, return null.
	 */
	public synchronized ArrayList<String> searchWord(String word)
	{
		if (dict.containsKey(word))
		{
			return dict.get(word);
		}
		else
		{
			return null;
		}
	}
	
	/**
	 * If word does not already exist in the dictionary, add word and return ADD_WORD;
	 * If word already exists but there are new meanings, add the new meanings and return ADD_MEANINGS
	 * If both word and meanings are redundant, return WORD_REDUNDANT
	 */
	public synchronized AddStatus addWord(String word, ArrayList<String> newMeanings)
	{
		if (dict.containsKey(word)) // word is already in dictionary
		{
			ArrayList<String> meanings = dict.get(word); // existing meanings of the word
			ArrayList<String> addMeanings = new ArrayList<String>(); // meanings to be added to the word
			int newMeaningsCount = 0;
			// Compare new meanings with existing meanings
			for (String newMeaning : newMeanings)
			{
				boolean add = true;
				for (String meaning : meanings)
				{
					if (newMeaning.equals(meaning))
					{
						add = false;
						break;
					}
				}
				if (add)
				{
					newMeaningsCount++;
					addMeanings.add(newMeaning);
				}
			}
			if (newMeaningsCount == 0)
			{
				return AddStatus.REDUNDANT_WORD;
			}
			else
			{
				meanings.addAll(addMeanings);
				dict.put(word, meanings);
				writeMapToDict();
				return AddStatus.ADD_MEANINGS;
			}
		}
		else // word is not in dictionary
		{
			dict.put(word, newMeanings);
			writeMapToDict();
			return AddStatus.ADD_WORD;
		}
	}
	
	/**
	 * If word exists in the dictionary, remove it and return REMOVED
	 * If word is not in the dictionary, return NOTFOUND
	 */
	public synchronized RemoveStatus removeWord(String word)
	{
		if (dict.containsKey(word))
		{
			dict.remove(word);
			writeMapToDict();
			return RemoveStatus.REMOVED;
		}
		else
		{
			return RemoveStatus.NOTFOUND;
		}
	}
	
	/**
	 * Write dictionary from HashMap to CSV
	 */
	public synchronized void writeMapToDict()
	{
		String newDict = "";
		for (String word : dict.keySet()) {
		    newDict += word;
		    ArrayList<String> meanings = dict.get(word);
		    for (String meaning : meanings)
		    {
		    	newDict += ("," + meaning);
		    }
		    newDict += "\n";
		}
		try
		{
			File file = new File(dictAddress);
			FileWriter fw = new FileWriter(file);
			System.out.println(newDict);
			fw.write(newDict);
			fw.close();    
		}
		catch (Exception e)
		{
			System.out.println("Failed to save dictionary");
		}
	}
}
