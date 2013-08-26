import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

import com.google.common.collect.Lists;


public class WordVector {
	float[] values;
	private double absValue;
	public double getAbsValue() {
		return absValue;
	}

	public WordVector(float[] values){
		this.values = values;
		double sum = 0;
		for(float a:values){
			sum+=Math.pow(a,2);
		}
		absValue = Math.sqrt(sum);
	}
	
	public WordVector(Collection<Float> words) {
		values = ArrayUtils.toPrimitive(words.toArray(new Float[words.size()]));
		
		double sum = 0;
		for(float a:values){
			sum+=Math.pow(a,2);
		}
		absValue = Math.sqrt(sum);
		
	}

	public float getDistance(float[] vector){
		double distance = 0;
		for(int i = 0; i<values.length;i++){
			distance+=(Math.pow(values[i]-vector[i], 2));
		}
		return (float)Math.sqrt(distance);
	}

	public float getDistance(Collection<Float> rawVector){
		
		WordVector vector = new WordVector(rawVector);
		float dotProduct = dotProduct(vector);
		
		return (float) (dotProduct/(this.getAbsValue()*vector.getAbsValue()));
	}
	
	private float dotProduct(WordVector vector) {
		float sum = 0;
		for(int i = 0; i<values.length;i++){
			sum+=(values[i]*vector.values[i]);
		}
		return sum;
	}

	public int getSize() {
		return values.length;
	}

	public float getValue(int i) {
		return values[i];
	}

	public float[] substract(WordVector b) {
		float[] returnVector = new float[values.length];
		for(int i = 0; i<values.length;i++){
			returnVector[i] = values[i]-b.values[i];
		}
		
		return returnVector;
	}
	
	public float[] add(WordVector b) {
		float[] returnVector = new float[values.length];
		for(int i = 0; i<values.length;i++){
			returnVector[i] = values[i]+b.values[i];
		}
		return returnVector;
	}
}
