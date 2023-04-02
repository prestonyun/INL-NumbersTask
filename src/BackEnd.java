/* Created by Preston Yun for the Integrative Neuroscience Laboratory at Southern Illinois University Carbondale

For questions or comments, please email prestonyun24@gmail.com */

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import javax.swing.AbstractAction;

public class BackEnd {
	
	public static Vector<Integer> memory = new Vector<>();
	public static PrintWriter print;
	
	// This variable is the delay between displayed numbers, and can be changed according to the user's needs.
	// Note: You must change the file name manually in the BackEnd constructor
	public static int testInterval = 1000;
	
	private final SimpleDateFormat dateFmt = new SimpleDateFormat("hh.mm.ss");

	public static double profit = 0.00;
	private static boolean success = false;
	
	// Constructor creates a File object to print results to a .txt file
	public BackEnd()
	{
		final SimpleDateFormat fileFmt = new SimpleDateFormat("dd.MMM.yyyy");
		final String d = fileFmt.format(new Date()) + "_" + dateFmt.format(new Date());
		final String path_name = "results" + testInterval + "ms_" + d + ".txt";
		final File results = new File(path_name);

		try
		{
			print = new PrintWriter(results);
			print.println("NumbersTask results from: " + new Date() + "\n");
			print.println();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
	}

	public void registerResponse()
	{
		// delta is the time it takes to register the response (reaction time)
		long delta = System.currentTimeMillis() - Numbers.timeInit;
		success = validate();
		
		if (success)
		{
			print.println(dateFmt.format(new Date()) + " Response time: " + delta + "ms");
			System.out.println(delta);
			profit += 0.125;
			Numbers.correctResponses++;
		}
		else
		{
			print.println(dateFmt.format(new Date()) + " Invalid");
			if (profit > 0)
				profit -= 0.125;
		}
	}
	
	// This method determines if the previously displayed sequence is valid or invalid
	public boolean validate() {
		int max = memory.size();

		int third = memory.elementAt(max - 3);
		int second = memory.elementAt(max - 2);
		int last = memory.elementAt(max - 1);

		boolean valid = true;
		for (int num : new int[]{third, second, last}) {
			if (num % 2 != last % 2) {
				valid = false;
				break;
			}
		}
		return valid;
	}
	
	
	// Listener for user input
	public class userListener extends AbstractAction
	{
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e)
		{
			registerResponse();
		}
	};
	
}
