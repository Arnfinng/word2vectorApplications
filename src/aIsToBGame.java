import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.google.common.collect.Multimap;


public class aIsToBGame{
	private Multimap<String, Float> wordVectors;
	private String[] words;


	public void playAIsToBGame(Multimap<String, Float> wordVectors){
		this.wordVectors = wordVectors;
		System.out.println("Hello human. I try to find words similar to eachother in the same way as an example");
		System.out.println("So if you ask: \"Big is to bigger as small is to what?\" I would answer smaller. ");
		System.out.println("So enter 3 words in the order first word is to second word as third word is to ?");
		try{
			BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
			String s = bufferRead.readLine();
			words = s.toLowerCase().split(" ");
			System.out.println(words[0] +" is to " + words[1] + " as " + words[2] + " is to " + getSimilarDifferenceWord());
		} catch(IOException e){
			e.printStackTrace();
		}
	}

	private String getSimilarDifferenceWord() {
		float[] differenceVector = getDifferenceVector();
		float[] similarVector = getSimilarVector(differenceVector);
		String mostSimilarWord = getMostSimilarWord(similarVector);
		return mostSimilarWord;
	}

	private String getMostSimilarWord(float[] similarVector) {
		WordVector similarWord = new WordVector(similarVector);
		float maxDistance = -1;
		String maxWord = "";
		for(String word:wordVectors.keySet()){
			float distance = similarWord.getDistance(wordVectors.get(word));
			if(word.equals(words[0]) || word.equals(words[1]) || word.equals(words[2])){
			}else if(distance>maxDistance){
				maxDistance = distance;
				maxWord = word;
			}
		}
		return maxWord;
	}

	private float[] getSimilarVector(float[] differenceVector) {
		WordVector a = new WordVector(wordVectors.get(words[2]));
		WordVector b = new WordVector(differenceVector);
		return a.add(b);
	}
	private float[] getDifferenceVector() {
		WordVector a = new WordVector(wordVectors.get(words[0]));
		WordVector b = new WordVector(wordVectors.get(words[1]));
		return b.substract(a);
	}
}