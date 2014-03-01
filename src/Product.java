
import org.json.simple.*;

public class Product 
{
	
	private double price;		
	private String id;			
	private String name;		
	private String brand;		
	private String priceString;	

	public Product(JSONObject product) {

		brand = (String)product.get("brandName");
		price = Double.parseDouble(((String) product.get("price")).substring(1));
		id = (String)product.get("productId");
		name = (String)product.get("productName");

		priceString = String.format("%.2f", price);
	}

	public String getProductInfo() {
		return " $" + priceString + "\t" + brand + " - " + name  + " : (" + id + ")" ;
	}

	public double getPrice() {
		return price;
	}
}