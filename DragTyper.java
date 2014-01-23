import java.io.*;
import java.util.*;

/*
class to find out which words match a set of characters that
were dragged from keyboard. 
there can be extra characters in the drag set, but every
character of a matched word must be present and in order.
*/
public class DragTyper{
	//list of possible words, should be loaded from a dictionary
	private ArrayList<String> words;

	//maps the first and last character key 
	//to all the words that have that key
	private Map<String, ArrayList<String>> seWordsMap;

	public DragTyper(){
		//initialize datastructures
		words = new ArrayList<String>();
		seWordsMap = new HashMap<String, ArrayList<String>>();
		
		//set up datastructures
		loadDictionary("british/brit-a-z.txt");
	}


	//load words from a dictionary
	//and setup the map datastructure
	public void loadDictionary(String dictFile){
		//load in the dictionary words
		try{
			BufferedReader reader = new BufferedReader(new FileReader(dictFile));
			String line;
			while ((line = reader.readLine()) != null) {
				//ignore the very small words
				if (line.length() > 2){
					words.add(line);	
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		//for (int i = 0; i < 10; i++){
		//	System.out.println(words.get(i));
		//}

		//fill the map from first and last characters 
		for (String word : words){
			String key = getSE(word);
			//System.out.println(key);
			ArrayList<String> list = seWordsMap.get(key);
			//initialize array lists if necessary
			if (list == null){
				list = new ArrayList<String>();
				seWordsMap.put(key, list);
			}
			list.add(word);
		}

		//now that the map is filled
		//sort the word lists by their length
		for (Map.Entry<String, ArrayList<String>> entry : seWordsMap.entrySet()){
			ArrayList<String> wl = entry.getValue();
			Collections.sort(wl, new Comparator<String>(){
				public int compare(String s1, String s2){
					int l1 = s1.length();
					int l2 = s2.length();
					if (l1 > l2)
						return -1;
					else if (l1 < l2)
						return 1;
					else return 0;
				}
			});
		}

		//print out a few entries to make sure its loaded
		/*
		int count = 0;
		for (Map.Entry<String, ArrayList<String>> entry : seWordsMap.entrySet()){
			System.out.println(entry.getKey());
			ArrayList<String> wl = entry.getValue();
			for (String w : wl){
				System.out.println("\t" + w);
			}
			if (count++ > 10)
				break;
		}
		*/
	}
	
	//return a key composed of the first and last character of the string
	// get start and end
	static String getSE(String x){
		String key = "" + x.charAt(0) + x.charAt(x.length()-1);
		return key;
	}

	//given an input string of the dragged letters
	//return a list of words that have the same start/stop characters
	//and also have all their characters match up to the inner characters of input
	public ArrayList<String> getMatches(String dragChars){
		//starting with the list of words with the same start and end character
		//narrow it down to those that also match the inner strings properly
		ArrayList<String> matches = new ArrayList<String>();
		for (String word : seWordsMap.get(getSE(dragChars))){
			if (doesDragMatch(word, dragChars)){
				matches.add(word);
			}
		}
		return matches;
	}

	//figure out if a set of dragged characters matches a word 
	//recursive function
	private boolean doesDragMatch(String word, String dragChars){
		//stopping condition
		if (word.length() == 0){
			return true;
		}

		//match the first character of the word
		int ix = dragChars.indexOf(word.charAt(0));
		if (ix == -1)
			return false;
		else{
			return doesDragMatch(word.substring(1), dragChars.substring(ix));
		}

	}


	//get rid of duplicate characters that are next to each other 
	String filterConDups(String x){
		return x.replaceAll("(.)\\1+","$1");
	}

	//print if the inner part matches
	public void test2(String word, String dragChars){
		System.out.println("inner matches");
		word = filterConDups(word);
		dragChars = filterConDups(dragChars);
		System.out.println(word + "\t" + dragChars);
		//get rid of consecutive duplicate chars

		if (doesDragMatch(word, dragChars)){
			System.out.println("\tit matches");
		}
		else{
			System.out.println("\tdoesn't match");	
		}
	}


	//print all the wrods that match 
	public void test1(String dragChars){
		System.out.println("Words that match");
		System.out.println(dragChars);
		words = getMatches(dragChars);
		for (String word : words){
			System.out.println("\t" + word);
		}
	}


	public static void main(String[] args){
		System.out.println("dragtype");
	}
}
