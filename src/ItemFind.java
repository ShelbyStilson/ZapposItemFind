import javax.swing.JFrame;


public class ItemFind {
	public static void main (String[] args)
	{
		JFrame frame = new JFrame ("Zappos Challenge");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		ZapposPanel panel = new ZapposPanel();
		
		frame.getContentPane().add(panel);
		frame.pack();
		frame.setVisible(true);
	}
}
