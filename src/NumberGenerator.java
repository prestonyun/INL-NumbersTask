/* Created by Preston Yun for the Integrative Neuroscience Laboratory at Southern Illinois University Carbondale

For questions or comments, please send an email to prestonyun32@gmail.com */

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Vector;

public class NumberGenerator {
	
	public static String[] sequencesArray = new String[Numbers.numSequences];
	public static int seqCounter = 0;
	
	// All of the numbers are stored as Integers in a Vector
	public static final Vector<Integer> numbers = new Vector<Integer>();
	
	public static final int[] evens = {2, 4, 6, 8};
	public static final int[] odds = {1, 3, 5, 7, 9};
	
	// Target sequences contain three even or odd numbers in a row
	public static final String[] tSequences = {"OOOEE", "OOOEO", "EOOOE", "EEOOO", "OEOOO", "EEEOO", "EEEOE", "OEEEO", "OOEEE", "EOEEE"};
	
	// Neutral sequences do not contain three even or odd numbers in a row
	public static final String[] nSequences = {"OEOEO", "OOEOE", "OEOOE", "OEEOO", "OOEEO", "OOEOO", "OEEOE", "OEOEE", "EOEOE", "EEOEO", "EOEEO", "EOOEE", "EEOEE", "EEOOEE", "EOOEO"};

	
	public static void generateNumbers()
	{
		// In the ArrayList, a true value codes for a target sequence, and false codes for a neutral sequence
		ArrayList<Boolean> sequences = new ArrayList<Boolean>();
		Random rand = new Random();
		char endChar = 'A';
		
		// Populate the ArrayList with the number of target and neutral sequences
		for (int i = 0; i < Numbers.numTargetSequences; i++)
			sequences.add(true);
		for (int i = 0; i < Numbers.numNeutralSequences; i++)
			sequences.add(false);
		
		// Shuffle the ArrayList so the sequences will be displayed randomly
		Collections.shuffle(sequences);
		
		for (int i = 0; i < sequences.size(); i++)
		{
			if (sequences.get(i) == true)
			{
				sequencesArray[i] = tSequences[rand.nextInt(10)];
				while (endChar == sequencesArray[i].charAt(0))
					sequencesArray[i] = tSequences[rand.nextInt(10)];
				
				endChar = sequencesArray[i].charAt(4);
			}
			else
			{
				sequencesArray[i] = nSequences[rand.nextInt(15)];
				while (endChar == sequencesArray[i].charAt(0))
					sequencesArray[i] = nSequences[rand.nextInt(15)];
				
				endChar = sequencesArray[i].charAt(4);
			}
				
		}
		
		for (String s : sequencesArray)
		{
			int[] numeric = convertToNumeric(s);
			
			for (Integer i : numeric)
				numbers.add(i);
		}
	}
	
	// Method to convert Strings to Numerical values. Also ensures that there will never be four even or odd numbers in a row
	public static int[] convertToNumeric(String sequence)
	{
		int[] numeric = new int[5];
		int cur = 0;
		Random rand = new Random();
		
		for (int i = 0; i < 5; i++)
		{
			if (sequence.charAt(i) == 'E')
			{
				numeric[i] = evens[rand.nextInt(4)];
				while (numeric[i] == cur)
					numeric[i] = evens[rand.nextInt(4)];
				
				cur = numeric[i];
			}
			else
			{
				numeric[i] = odds[rand.nextInt(5)];
				while (numeric[i] == cur)
					numeric[i] = odds[rand.nextInt(5)];
				
				cur = numeric[i];
			}
		} // end for
		
		return numeric;
	}
	
}
