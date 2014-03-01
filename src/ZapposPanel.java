// Patrick "Shelby" Stilson

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import javax.swing.*;



public class ZapposPanel extends JPanel {
	
	//jpanel element declarations
	private JButton begin; //button to begin the search for products
	private JLabel errlabel1, errlabel2, notificationlabel, info1, info2, info3, cost, quantity, additional;
	private JTextField NumProducts, DesiredPrice, OptionalAdditions; //the two important inputs
	private JTextArea output;
	private String optional;

	private final static String newline = "\n";

	//official zappos colors pulling from zappos.com 
	private Color zapposBackgroundColor = new Color(248,248,238);
	private Color zapposLightBlueColor = new Color(118,152,200);
	private Color zapposDarkBlueColor = new Color(42,87,132);
	
	public ZapposPanel ()
	{
		
		//initial input panel
		JPanel subPanel1 = new JPanel(); 
			subPanel1.setPreferredSize(new Dimension (900,50));
			subPanel1.setBackground(zapposBackgroundColor);
			NumProducts = new JTextField (5);
			JLabel num = new JLabel ("Quantity of Products: ");
			DesiredPrice = new JTextField (5);
			JLabel price = new JLabel ("Desired Price: ");
			JLabel addition = new JLabel ("Additional Search Criteria: (Optional) ");
			OptionalAdditions = new JTextField (10);
			begin = new JButton ("Find");
			begin.addActionListener(new ButtonListener());
		subPanel1.add (num);
		subPanel1.add (NumProducts);
		subPanel1.add (price);
		subPanel1.add (DesiredPrice);
		subPanel1.add (addition);
		subPanel1.add (OptionalAdditions);
		subPanel1.add (begin);		


		JPanel subPanel15 = new JPanel();
			subPanel15.setPreferredSize(new Dimension (900, 40));
			subPanel15.setBackground(zapposBackgroundColor);
			subPanel15.setLayout(new BoxLayout(subPanel15, BoxLayout.Y_AXIS));
			errlabel1 = new JLabel ("", JLabel.CENTER);
			errlabel2 = new JLabel ("", JLabel.CENTER);	
			errlabel1.setHorizontalTextPosition(JLabel.CENTER);
			errlabel2.setHorizontalTextPosition(JLabel.CENTER);
			subPanel15.add (errlabel1);
			subPanel15.add (errlabel2);
		
		//border panel
		JPanel subPanel2 = new JPanel(); 
			subPanel2.setPreferredSize(new Dimension (900,20));
			subPanel2.setBackground(zapposDarkBlueColor);
		
		//displays information
		JPanel subPanel3 = new JPanel(); 
			subPanel3.setPreferredSize(new Dimension (800,30));
			subPanel3.setBackground(zapposLightBlueColor);
			info1 = new JLabel ("Number of Products : ");
			info2 = new JLabel ("Desired Price Point: ");
			info3 = new JLabel ("Search Term: ");
			cost = new JLabel ("");
			quantity = new JLabel("");
			additional = new JLabel("");
		subPanel3.add (info1);
		subPanel3.add (quantity);
		subPanel3.add (info2);
		subPanel3.add (cost);
		subPanel3.add (info3);
		subPanel3.add (additional);
		
		//border panel
		JPanel subPanel4 = new JPanel(); 
		subPanel4.setPreferredSize(new Dimension (900,20));
		subPanel4.setBackground(zapposDarkBlueColor);

		JPanel subPanel5 = new JPanel();
		subPanel5.setPreferredSize(new Dimension (900,500));
		subPanel5.setBackground(zapposLightBlueColor);	
		notificationlabel = new JLabel("");
		subPanel5.add (notificationlabel);	

		output = new JTextArea("");
		JScrollPane scrollPane = new JScrollPane(output); 
		output.setEditable(false);	
		
		output.setBackground(zapposBackgroundColor);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollPane.setLayout(new ScrollPaneLayout());
		subPanel5.add (scrollPane);	
		
		JLabel BeginningLabel = new JLabel("Find List of Products");
		
		add (BeginningLabel);
		add (subPanel1);
		add (subPanel15);
		add (subPanel2);
		add (subPanel3);
		add (subPanel4);
		add (subPanel5);
		
		setPreferredSize (new Dimension(900,730));
		setBackground (zapposLightBlueColor);
	}
	
	private class ButtonListener implements ActionListener
	{
		public void actionPerformed (ActionEvent event)
		{
			
			
			if (event.getSource() == begin)
			{	
				int i = 0;
				double d = 0.00; 

				String nump = NumProducts.getText();
				String dprice = DesiredPrice.getText();
				String s = OptionalAdditions.getText();

				output.setText(" ");
				
				errlabel1.setForeground(Color.red);
				errlabel2.setForeground(Color.red);
				
				quantity.setText("");
				cost.setText("");
				additional.setText("");
				
				//error checks number of products input
				try 
				{
					i = Integer.parseInt(nump.trim());
				} 
				catch (NumberFormatException e)
				{
					errlabel1.setText ("\t\tQuantity of Products must be of format # and larger than 0. Please Try Again");
				}

				//error checks desired price input
				try 
				{
					d = Double.parseDouble(dprice.trim());
				} 
				catch (NumberFormatException e)
				{
					errlabel2.setText ("\t\tDesired Price must be of format #.## and larger than 0. Please Try Again");
				}
				
				//error checks so that input is larger than zero. 
				if(i < 1) 
				{
					errlabel1.setText ("\t\tQuantity of products must be of format # and larger than 0. Please Try Again");
				} 
				//error checks so that input is larger than zero. 
				else if(d <= 0) 
				{
					errlabel2.setText ("\t\tDesired Price must be of format #.## and larger than 0. Please Try Again");
				} 
				//otherwise, if no errors, then have valid inputs and can stop loop
				else 
				{
					errlabel1.setText("");
					errlabel2.setText("");
					notificationlabel.setText("");
					NumProducts.setText("");
					DesiredPrice.setText("");
					OptionalAdditions.setText("");
					
					//rounds numbers to appropriate cents 
					d = Math.round(d*100)/100.0d;
					//gets the optional search criteria
					if(s == "")
					{
						s = "None";
					}
					
					cost.setText(" $" + d + "   ");
					quantity.setText(" " + i + "    ");
					additional.setText(" " + s);
					
					cost.setForeground (Color.white);
					quantity.setForeground (Color.white);
					additional.setForeground(Color.white);
					
					NumberCrunch crunch = new NumberCrunch(d, i, s);
					try 
					{
						output.append(crunch.getLists() + newline);
						//System.out.println(crunch.getProductLists() + newline);
					} 
					catch (org.json.simple.parser.ParseException e) 
					{
						// TODO Auto-generated catch block
						notificationlabel.setText ("An error has occurred. We apologize for the inconvenience.");
						e.printStackTrace();
						
					} 
					catch (IOException e) {
						// TODO Auto-generated catch block
						notificationlabel.setText ("An error has occurred. We apologize for the inconvenience.");
						e.printStackTrace();
					}
					//notificationlabel.setText("Results:");
				}
					
			}
			
			
		}
	}
}
