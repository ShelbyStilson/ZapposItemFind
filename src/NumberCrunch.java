import org.json.simple.*;
import org.json.simple.parser.*;
import java.io.*;
import java.util.*;

public class NumberCrunch
{

	private int quantity, pageNumber;			
	private double cost, maxPrice;		
	private JSONArray zapposProducts;		
	private ArrayList<Product> productObjects; 
	private ArrayList<List> Lists;
	private String specifics;

	public NumberCrunch(double price, int number, String optional) 
	{
		productObjects = new ArrayList<Product>();
		Lists = new ArrayList<List>();
		zapposProducts = new JSONArray();

		cost = price;
		quantity = number;
		maxPrice = Integer.MAX_VALUE; 
		pageNumber = 1;		
		specifics = optional;

	}

	public String getLists() throws ParseException, IOException 
	{
		this.FindAppropriateProducts();
		this.SearchZappos();
		this.ListofProducts();
		this.sortList();

		if (Lists.size() != 0) 
		{
			String answer = "\n";
			for (List x:Lists) 
			{
				answer += x.toString() + "\n";
			}
			return answer;
		}
		else 
		{
			return "I'm sorry but there appears to be no Zappos Products that match your criteria. Please try again. We apologize for the inconvenience.";
		}
	}

	private void FindAppropriateProducts() throws ParseException, IOException 
	{
		//excludes unnecessary information
		String http = "http://api.zappos.com/Search?key=";
		String Key = "a73121520492f88dc3d33daf2103d7574f1a3166";
		String exclusions = "&excludes=[%22thumbnailImageUrl%22,%22productUrl%22,%22originalPrice%22,%22percentOff%22,%22styleId%22,%22colorId%22]&term=";
		String limit = "&limit=100&sort={%22price%22:%22asc%22}&page=";
	
		String call = APIWork.webstring(http + Key + exclusions + specifics + limit + pageNumber);
		JSONObject apiobj = APIWork.parsecall(call);
		JSONArray apidata = APIWork.theresults(apiobj);

		double firstPrice = getPrice(apidata.get(0));

	
		if((firstPrice * quantity) > cost) 
		{
			zapposProducts = null;
		}

		maxPrice = cost - (quantity - 1)*(firstPrice);


		pageNumber++;
		Double finalPagePrice = getPrice(apidata.get(apidata.size() - 1));

		while(finalPagePrice < maxPrice) 
		{ 
			String call2 = APIWork.webstring(http + Key + exclusions + specifics + limit + pageNumber);
			JSONObject apiobj2 = APIWork.parsecall(call2);
			JSONArray apidata2 = APIWork.theresults(apiobj2);
			apidata.addAll(apidata2);

			finalPagePrice = getPrice(apidata2.get(apidata2.size() - 1));

			pageNumber++;
		}

		zapposProducts = apidata;
	}

	private void SearchZappos() 
	{
		productObjects.add(new Product((JSONObject)zapposProducts.get(0)));

		int amount = 1;
		int pricenumber = 1;
		for(int i = 1; i < zapposProducts.size() && getPrice(zapposProducts.get(i)) < maxPrice; i++) 
		{
			double currentPrice = getPrice(zapposProducts.get(i));
			if (currentPrice > productObjects.get(pricenumber-1).getPrice()) 
			{
				productObjects.add(new Product((JSONObject)zapposProducts.get(i)));
				pricenumber++;
				amount = 1;
			} 
			else if (Math.abs(currentPrice - productObjects.get(pricenumber-1).getPrice()) < .01 && amount < quantity)
			{
				productObjects.add(new Product((JSONObject)zapposProducts.get(i)));
				pricenumber++;
				amount++;
			} 
			else 
			{
				while (i < zapposProducts.size() && Math.abs(currentPrice - productObjects.get(pricenumber-1).getPrice()) < .01) 
				{
					i++;
					currentPrice = getPrice(zapposProducts.get(i));
				}
				i++;
				amount = 0;
			}
		}
	}

	private void ListofProducts() 
	{
		recursion(productObjects, cost, new ArrayList<Product>());
	}

	private void recursion(ArrayList<Product> List, double target, ArrayList<Product> partial) 
	{
		int priceWithinAmount = 1;

		if (partial.size() > quantity) 
		{ 
			return; 
		}

		double sum = 0;
		for (Product x : partial) sum += x.getPrice();

		if (Math.abs(sum - target) < priceWithinAmount && partial.size() == quantity && Lists.size() < 15) 
		{
			if (Lists.size() == 0) 
			{	
				Lists.add(new List(partial, cost)); 
			}
			else
			{
				List testerList = Lists.get(Lists.size() -1);
				List partialList = new List(partial, cost);

				if (!partialList.equals(testerList))
				{
					Lists.add(partialList);
				}
			}
		}

		if (sum >= target + priceWithinAmount) 
		{
			return;
		}

		for (int i = 0; i < List.size() && !(partial.size() == quantity && sum < target); i++)
		{
			ArrayList<Product> remaining = new ArrayList<Product>();
			Product n = List.get(i);

			for (int j=i+1; j < List.size(); j++) 
			{
				remaining.add(List.get(j)); 
			}

			ArrayList<Product> partial_rec = new ArrayList<Product>(partial);
			partial_rec.add(n);
			recursion(remaining, target, partial_rec);
		}
	}

	private void sortList() 
	{
		Collections.sort(Lists);
	}

	private Double getPrice(Object item)
	{
		return Double.parseDouble(((String) ((JSONObject) item).get("price")).substring(1));
	}

}