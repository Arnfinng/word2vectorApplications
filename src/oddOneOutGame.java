import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.google.common.collect.Multimap;


public class oddOneOutGame{
	private Multimap<String, Float> wordVectors;
	private String[] words;
	public void playOddOneOut(Multimap<String, Float> wordVectors) {
		this.wordVectors = wordVectors;
		System.out.println("Hello human. I attempt to find a word in a list which doesn't belong.");
		System.out.println("Type several words separated by spaces. The more words you enter, the better I can guess.");

		try{
			BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
			String s = bufferRead.readLine();
			words = s.toLowerCase().split(" ");
			System.out.println("I don't think " + get_furthest_word() + " belong in this list!");
		} catch(IOException e){
			e.printStackTrace();
		}

	}
	private String get_furthest_word() {
		for(String word : words)
			if(!wordVectors.containsKey(word))
				return word;
		WordVector[] vectList = getVectors();
		float[] mean = findMean(vectList);
		int highestDistance = gethighestDistance(mean, vectList);
		return words[highestDistance];
	}

	private int gethighestDistance(float[] mean, WordVector[] vectList) {
		float maxDistance = -1;
		int maxIndex = -1;
		int counter = 0;
		for(WordVector word:vectList){
			float distance = word.getDistance(mean);
			if(distance> maxDistance){
				maxDistance = distance;
				maxIndex = counter;
			}
			counter++;
		}
		return maxIndex;
	}
	private float[] findMean(WordVector[] vectList) {
		float[] mean = new float[vectList[0].getSize()];
		for(int i = 0; i<vectList[0].getSize();i++){
			float temp = 0;
			for(WordVector word:vectList){
				temp+=word.getValue(i);
			}
			temp = temp/(float)vectList.length;
			mean[i] = temp;
		}
		return mean;
	}
	
	private WordVector[] getVectors() {
		WordVector[] vectList = new WordVector[words.length];
		for(int i = 0; i<words.length;i++){
			vectList[i] = new WordVector(wordVectors.get(words[i]));
		}
		return vectList;
	}
}