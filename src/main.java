import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;


public class main {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		String fileName = "vectors";
		System.out.println("Hi human. I can reason about words, almost like a human.");
		System.out.println("I can do two different things at the moment");
		System.out.println("First of I can figure out which word doesn't belong in a set");
		System.out.println("Secondly I find words that are different in the same way.");
		System.out.println("So I can figure out which word that is to big as smaller is to small (bigger, in this case)");

		System.out.println("first I must load some information, gathered from Wikipedia");
		System.out.println("Just give me a moment. Shouldn't take more than a minute");

		Multimap<String, Float> wordVectors = readVectorsFromFiles(fileName);

		System.out.println("There, now I'm ready. Now I need you to make a choice");
		System.out.println("Do you want to try the \"what word don't belong\" game? All you have to do is press 1.");
		System.out.println("Or do you want to try a fun game of a is to b as x is to what game? If that is the case, just press 2.");
		
		BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
		try{
			String s = "";
			while(!(s = bufferRead.readLine()).equals("q")){

				if(s.equalsIgnoreCase("1"))
					new oddOneOutGame().playOddOneOut(wordVectors);
				else if (s.equalsIgnoreCase("2"))
					new aIsToBGame().playAIsToBGame(wordVectors);
				System.out.println("Ready to try again? q to quit");
				System.out.println("Do you want to try the what word don't belong game? All you have to do is press 1.");
				System.out.println("Or do you want to try a fun game of a is to b as x is to what game? If that is the case, just press 2.");
			}
		} catch(IOException e){
			e.printStackTrace();
		}
	}


	private static Multimap<String, Float> readVectorsFromFiles(String mapName) throws IOException{
		File folder = new File(mapName);
		Multimap<String, Float> wordVectors = ArrayListMultimap.create(200000, 250);
		for (File file : folder.listFiles()) {
			if (file.isFile()) {
				BufferedReader reader = new BufferedReader(new FileReader(file));
				String line = null;
				while ((line = reader.readLine()) != null) {
					List<String> words = new ArrayList<>();
					words = Lists.newArrayList(line.split(" "));
					wordVectors.putAll(words.get(0), convertToFloatArray(words.subList(1, words.size()-1)));
				}
			}
		}
		return wordVectors;
	}

	private static List<Float> convertToFloatArray(List<String> subList) {
		List<Float> resultList = new ArrayList<>();
		for(String value:subList)
			resultList.add(Float.parseFloat(value));
		return resultList;
	}

}

