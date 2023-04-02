/* Created by Preston Yun for the Integrative Neuroscience Laboratory at Southern Illinois University Carbondale

For questions or comments, please send an email to prestonyun24@gmail.com */

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Vector;

public class NumberGenerator {
	public static String[] sequencesArray = new String[Numbers.numSequences];
	// All the numbers are stored as Integers in a Vector
	public static final Vector<Integer> numbers = new Vector<Integer>();
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
		
		// Not sure exactly what this does honestly, it seems to be a check for false-positives, and the appropriate correction
		// Don't judge me, it works and I don't want to mess with it
		for (int i = 0; i < sequences.size(); i++)
		{
			boolean isTarget = sequences.get(i);
			String sequence;

			do {
				sequence = isTarget ? tSequences[rand.nextInt(10)] : nSequences[rand.nextInt(15)];
			} while (sequence.charAt(0) == endChar);

			endChar = sequence.charAt(4);
			sequencesArray[i] = sequence;

			for (final int num : convertToNumeric(sequence))
				numbers.add(num);
		}
	}
	
	// Method to convert Strings to Numerical values. Also ensures that there will never be four even or odd numbers in a row
	public static int[] convertToNumeric(String sequence)
	{
		Random rand = new Random();
		final int[] numeric = new int[5];
		int cur = 0;
		final int[] evens = {2, 4, 6, 8};
		final int[] odds = {1, 3, 5, 7, 9};
		
		for (int i = 0; i < 5; i++) {
			final boolean isEven = sequence.charAt(i) == 'E';
			int[] values = isEven ? evens : odds;
			int index = rand.nextInt(values.length);
			numeric[i] = values[index];

			while (numeric[i] == cur)
				numeric[i] = values[rand.nextInt(values.length)];

			cur = numeric[i];
		}
		
		return numeric;
	}
	
}
