/* Created by Preston Yun for the Integrative Neuroscience Laboratory at Southern Illinois University Carbondale

For questions or comments, please send an email to prestonyun24@gmail.com */

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JComponent;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.awt.BorderLayout;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class Numbers {
	
	static GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
	
	NumberFormat currencyFmt = new DecimalFormat("#0.00");
	
	BackEnd backend = new BackEnd();
	
	final String FIRE = "Fire";
	
	// The test is 12 minutes, or 720,000 milliseconds, so these values are hard-coded
	static long testBegin = 0, testEnd = 720000;
	
	// The introduction count-down is 15 seconds. 'cd' variable must be 17 for some reason
	int cd = 17;
	
	// Values for the number of sequences, based on the interval of the test
	public static final int numSequences = (int) (testEnd / BackEnd.testInterval) / 5;
	public static final int numTargetSequences = (int) (numSequences * 0.4 + 0.5);
	public static final int numNeutralSequences = numSequences - numTargetSequences;
	
	public static int index = 0;
	public static long timeInit;
	public static int correctResponses = 0;
	
	JFrame jframe = new JFrame("INL - Integrative Neuroscience Laboratory");
	
	Container content = jframe.getContentPane();
	
	JButton start = new JButton("Begin Task");
	JButton end = new JButton("End Task");
	
	JLabel jLabel = new JLabel("0");
	JLabel intro = new JLabel("<html><center>3-IN-A-ROW-TASK " + BackEnd.testInterval + "MS"
			+ "<br><br>WHEN YOU DETECT 3 EVEN NUMBERS IN A ROW, PRESS 'T' AS QUICKLY AS POSSIBLE"
			+ "<br><br>WHEN YOU DETECT 3 ODD NUMBERS IN A ROW, PRESS 'T' AS QUICKLY AS POSSIBLE"
			+ "<br><br>YOU WILL GET BONUS $ FOR A CORRECT PRESS, BUT LOSE $ FOR A WRONG PRESS"
			+ "<br><br>THERE ARE " + numTargetSequences + " TARGET SEQUENCES IN THIS TEST"
			+ "<br><br>REMEMBER, PRESS 'T' TO REGISTER AN INPUT");
	
	JLabel countdownTitle = new JLabel("TASK WILL BEGIN IN");
	JLabel countdown = new JLabel(Integer.toString(cd));
	
	public JLabel money = new JLabel("$ 0.00");
	
	boolean isRunning = false;

	public static void main(String[] args) {
		
		JFrame.setDefaultLookAndFeelDecorated(true);
		Numbers num = new Numbers();
		
		NumberGenerator.generateNumbers();
		
		num.createView();

	}
	
	// Initializes UI components
	private void createView()
	{
		content.setLayout(new BorderLayout());
		content.setBackground(Color.black);
		
		intro.setFont(new Font("Times New Roman", Font.PLAIN, 40));
		intro.setForeground(Color.white);
		intro.setHorizontalAlignment(SwingConstants.CENTER);
		intro.setAlignmentX(0.5f);
		
		start.setPreferredSize(new Dimension(100, 50));
		start.setFont(new Font("Times New Roman", Font.PLAIN, 40));
		end.setFont(new Font("Times New Roman", Font.PLAIN, 30));
		
		content.add(intro, BorderLayout.CENTER);
		content.add(start, BorderLayout.SOUTH);
		
		start.addActionListener(actionListener);
		end.addActionListener(actionListener);
		
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		device.setFullScreenWindow(jframe);
		
		jframe.setVisible(true);
	}
	

	// Action Listener for the various buttons
	ActionListener actionListener = new ActionListener()
	{
		public void actionPerformed(ActionEvent e)
		{
			if (e.getSource() == start)
			{
				displayIntro();
			}
			else if (e.getSource() == end)
			{
				// Prints results to the output file
				BackEnd.print.println("Total profits: $" + currencyFmt.format(BackEnd.profit));
				BackEnd.print.println("Correct Responses: " + correctResponses + "/" + numTargetSequences);
				BackEnd.print.close();
				isRunning = false;
				System.exit(0);
			}
		}
	};
	

	// Method to display the NumbersTask test
	public void displayTest()
	{
		isRunning = true;
		content.add(jLabel, BorderLayout.CENTER);
		content.add(money, BorderLayout.NORTH);
		
		jLabel.setFont(new Font("Times New Roman", Font.PLAIN, 150));
		jLabel.setForeground(Color.white);
		jLabel.setHorizontalAlignment(SwingConstants.CENTER);
		jLabel.setAlignmentX(0.5f);
		
		money.setFont(new Font("Times New Roman", Font.PLAIN, 75));
		money.setForeground(Color.white);
		money.setHorizontalAlignment(SwingConstants.CENTER);
		money.setAlignmentX(0.5f);
		
		jLabel.setVisible(true);
		money.setVisible(true);
		countdownTitle.setVisible(false);
		countdown.setVisible(false);
		
		jLabel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("T"), FIRE);
		jLabel.getActionMap().put(FIRE, backend.new userListener());
		
		final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
		scheduler.scheduleAtFixedRate(new mainInit(), 0, BackEnd.testInterval, TimeUnit.MILLISECONDS);
	}
	
	
	//Method to display the introduction count-down
	public void displayIntro()
	{
		content.add(end, BorderLayout.SOUTH);
		content.add(countdownTitle, BorderLayout.NORTH);
		content.add(countdown, BorderLayout.CENTER);
		
		intro.setVisible(false);
		start.setVisible(false);
		end.setVisible(true);
		countdownTitle.setVisible(true);
		countdown.setVisible(true);
		
		countdownTitle.setFont(new Font("Times New Roman", Font.PLAIN, 100));
		countdownTitle.setForeground(Color.white);
		countdownTitle.setHorizontalAlignment(SwingConstants.CENTER);
		countdownTitle.setAlignmentX(0.5f);
		
		countdown.setFont(new Font("Times New Roman", Font.PLAIN, 150));
		countdown.setForeground(Color.white);
		countdown.setHorizontalAlignment(SwingConstants.CENTER);
		countdown.setAlignmentX(0.5f);
		
		final ScheduledExecutorService introduction = Executors.newSingleThreadScheduledExecutor();
		introduction.scheduleAtFixedRate(new introInit(),  0,  1000,  TimeUnit.MILLISECONDS);
	}
	
	class mainInit implements Runnable
	{
		public void run()
		{
			timeInit = System.currentTimeMillis();
			jLabel.setText(Integer.toString(NumberGenerator.numbers.get(index)));
			BackEnd.memory.add(NumberGenerator.numbers.get(index));
			System.out.println(NumberGenerator.numbers.get(index));
			index++;
			
			testBegin += BackEnd.testInterval;
			
			money.setText("$ " + currencyFmt.format(BackEnd.profit));
			
			if (testBegin == testEnd)
			{
				BackEnd.print.println("");
				BackEnd.print.println("Total profits: $" + currencyFmt.format(BackEnd.profit));
				BackEnd.print.println("Correct Responses: " + correctResponses + "/" + numTargetSequences);
				BackEnd.print.close();
				isRunning = false;
				System.exit(0);
			}
		}
	}
	

	//Initiator method for the introduction
	class introInit implements Runnable
	{
		public void run()
		{
			countdown.setText(Integer.toString(cd - 2) + " seconds");
			cd--;
			
			if (cd == 1)
				displayTest();
		}
	}
		
}

