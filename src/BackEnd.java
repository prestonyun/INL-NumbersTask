/* Created by Preston Yun for the Integrative Neuroscience Laboratory at Southern Illinois University Carbondale

For questions or comments, please send an email to prestonyun24@gmail.com */

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import javax.swing.AbstractAction;

public class BackEnd {
	
	public static Vector<Integer> memory = new Vector<Integer>();
	public static PrintWriter print;
	
	// This variable is the delay between displayed numbers, and can be changed according to the user's needs.
	// Note: You must change the file name manually in the BackEnd constructor
	public static int testInterval = 1000;
	
	Date date;
	SimpleDateFormat dateFmt;
	
	// Constructor creates a File object to print results to a .txt file
	public BackEnd()
	{
		
		dateFmt = new SimpleDateFormat("hh.mm.ss");
		SimpleDateFormat fileFmt = new SimpleDateFormat("dd.MMM.yyyy");
		String d = fileFmt.format(new Date()) + "_" + dateFmt.format(new Date());
		final String path_name = "C:results1000ms_" + d + ".txt";
		File results = new File(path_name);
		print = null;

		try
		{
			print = new PrintWriter(results);
			print.println("NumbersTask results from: " + new Date());
			print.println();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
	}
	
	//Variable for income the participant would make
	public static double profit = 0.00;

	public static boolean success = false;

	public void registerResponse()
	{
		// delta is the time it takes to register the response (reaction time)
		long delta = System.currentTimeMillis() - Numbers.timeInit;
		
		success = validate();
		
		if (success)
		{
			date = new Date();
			print.println(dateFmt.format(date) + " Response time: " + delta + "ms");
			System.out.println(delta);
			profit += 0.125;
			Numbers.correctResponses++;
		}
		else
		{
			date = new Date();
			print.println(dateFmt.format(date) + " Invalid");
			if (profit > 0)
				profit -= 0.125;
		}
	}
	
	// This method determines if the previously displayed sequence is valid or invalid
	public boolean validate()
	{
		int max = memory.size();
		
		int third = memory.elementAt(max - 3);
		int second = memory.elementAt(max - 2);
		int last = memory.elementAt(max - 1);
		
		if (third % 2 == 1 && second % 2 == 1 && last % 2 == 1)
			return true;
		else if (third % 2 == 0 && second % 2 == 0 && last % 2 == 0)
			return true;
		else
			return false;
	}
	
	
	// Listener for user input
	public class userListener extends AbstractAction
	{
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent e)
		{
			registerResponse();
		}
	};
	
}
