
import java.util.ArrayList;

public class List implements Comparable
{

	private ArrayList<Product> list;
	private double listsum;							
	private double idealTotal; 					
	private double approximation;	

	public List(ArrayList<Product> products, double total) 
	{
		list = products;
		listsum = 0;
		idealTotal = total;
		for (Product x:list) listsum += x.getPrice(); 
		approximation = Math.abs(idealTotal - listsum);
	}

	public String toString() 
	{
		double leftover;
		leftover = listsum - idealTotal;
		leftover = Math.round(leftover*100)/100.00d;
		listsum = Math.round(listsum*100)/100.00d;
		String pricetotal;
		
		if(leftover < 0)
		{
			pricetotal = "  $" + listsum + " (" + leftover + ")\n";
		}
		else
		{
			pricetotal = "  $" + listsum + " (+" + leftover + ")\n";
		}

		for (int i = 0; i < list.size(); i++) 
		{
			pricetotal += "  (" + (i+1) + ") " + list.get(i).getProductInfo() + "\n";
		}

		return pricetotal;
	}

	public int compareTo(Object x) 
	{
		List comparelist = (List) x;
		if (this.equals(comparelist))
		{
			return 0;
		}
		else if (this.approximation < comparelist.getapproximation())
		{ 
			return -1;
		}
		else 
		{
			return 1;
		}
	}

	public boolean equals(List comparelist) 
	{
		if( this.list.size() != comparelist.getProductListLength()) 
		{
			return false;
		}

		if (this.idealTotal != comparelist.getTotal()) 
		{
			return false;
		}

		for (int i = 0; i < list.size(); i++)
		{
			if (Math.abs(this.list.get(i).getPrice() - comparelist.getPrice(i)) > .01) 
			{
				return false;
			}
		}
		return true;
	}

	public double getPrice(int index) 
	{
		return list.get(index).getPrice();
	}

	public double getlistsum() 
	{
		return listsum;
	}

	public int getProductListLength() 
	{
		return list.size();
	}

	public double getapproximation() 
	{
		return approximation;
	}

	public double getTotal() 
	{
		return idealTotal;
	}

}