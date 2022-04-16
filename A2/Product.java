import java.util.ArrayList;

// Student ID: 501087117, Name: Gyu-won An
/*
 * class Product defines a product for sale by the system. 
 * 
 * A product belongs to one of the 5 categories below. 
 * 
 * Some products also have various options (e.g. size, color, format, style, ...). The options can affect
 * the stock count(s). In this generic class Product, product options are not used in get/set/reduce stockCount() methods  
 * 
 * Some products
 */
public class Product implements Comparable<Product>
{
	public static enum Category {GENERAL, CLOTHING, BOOKS, FURNITURE, COMPUTERS, SHOES};
	
	private String name;
	private String id;
	private Category category;
	private double price;
	private int stockCount;
	private int popularity;
	private ArrayList[] reviews;
	private int numberOfReviews;
	private double rating;

	public Product(String name, String id, double price, int stock, Category category)
	{
		this.name = name;
		this.id = id;
		this.price = price;
		this.stockCount = stock;
		this.category = category;
		this.popularity = 0;
		this.reviews = new ArrayList[] {new ArrayList<String>(), new ArrayList<String>()
				,new ArrayList<String>(),new ArrayList<String>(),new ArrayList<String>()};
		this.numberOfReviews = 0;
		this.rating = 0.0;
	}

	/*
	 * This method always returns true in class Product. In subclasses, this method will be overridden
	 * and will check to see if the options specified are valid for this product.
	 */

	public boolean validOptions(String productOptions)
	{
		if (productOptions != "") {
			return false;
		}
		return true;
	}
	
	public Category getCategory()
	{
		return category;
	}
	
	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public double getPrice()
	{
		return price;
	}

	public void setPrice(double price)
	{
		this.price = price;
	}

	/*
	 * Return the number of items currently in stock for this product
	 * Note: in this general class, the productOptions parameter is not used. It may be used
	 * in subclasses.
	 */

	public int getStockCount(String productOptions)
	{
		return stockCount;
	}
	/*
	 * Set (or replenish) the number of items currently in stock for this product
	 * Note: in this general class, the productOptions parameter is not used. It may be used
	 * in subclasses.
	 */
	public void setStockCount(int stockCount, String productOptions)
	{
		this.stockCount = stockCount;
	}
	/*
	 * Reduce the number of items currently in stock for this product by 1 (called when a product has
	 * been ordered by a customer)
	 * Note: in this general class, the productOptions parameter is not used. It may be used
	 * in subclasses.
	 */
	public void reduceStockCount(String productOIptions)
	{
		stockCount--;
	}

	public int getPopularity() {
		return popularity;
	}

	public void setPopularity(int popularity) {
		this.popularity = popularity;
	}

	public ArrayList[] getReviews() {
		return reviews;
	}

	public void addReview(int rating, String comment) {

	}

	public int getNumberOfReviews() {
		return numberOfReviews;
	}

	public void setNumberOfReviews(int numberOfReviews) {
		this.numberOfReviews = numberOfReviews;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public void print()
	{
		System.out.printf("\nId: %-5s Category: %-9s Name: %-20s Price: %7.1f", id, category, name, price);
		System.out.print("\t\tRating: " + this.rating + "\t\t");
	}
	
	/*
	 * Two products are equal if they have the same product Id.
	 * This method is inherited from superclass Object and overridden here
	 */

	public boolean equals(Object other)
	{
		Product otherP = (Product) other;
		return this.id.equals(otherP.id);
	}

	//	the value 0 if the argument string is equal to this string;
	//	a value less than 0 if this string is lexicographically less than the string argument;
	//	and a value greater than 0 if this string is lexicographically greater than the string argument.
	@Override
	public int compareTo(Product o) {
		return this.name.compareTo(o.name);
	}

	public int compareByPrice(Product o) {
		if (this.price == o.price) {
			return 0;
		} else if (this.price < o.price) {
			return -1;
		}
		return 1;
	}

}
