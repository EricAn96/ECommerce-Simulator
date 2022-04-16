// Student ID: 501087117, Name: Gyu-won An
/* A book IS A product that has additional information - e.g. title, author

 	 A book also comes in different formats ("Paperback", "Hardcover", "EBook")
 	 
 	 The format is specified as a specific "stock type" in get/set/reduce stockCount methods.

*/
public class Book extends Product
{
  private String author;
  private String title;
  private int year;
  
  // Stock related information NOTE: inherited stockCount variable is used for EBooks
  int paperbackStock;
  int hardcoverStock;
  
  public Book(String name, String id, double price, int paperbackStock, int hardcoverStock, String title, String author, int year)
  {
  	 // Make use of the constructor in the super class Product. Initialize additional Book instance variables. 
  	 // Set category to BOOKS
      // set the object's properties as Product by calling its parent object's constructor, as well as set the Book object's independent
      // properties
      super(name, id, price, (int) Double.POSITIVE_INFINITY, Category.BOOKS);
      this.author = author;
      this.title = title;
      this.hardcoverStock = hardcoverStock;
      this.paperbackStock = paperbackStock;

      // Couldn't edit the initializations (as mentioned), thus the year is randomized
      this.year = year;
  }

  // return the author name
  public String getAuthor(){
      return author;
  }

  // return the title string
  public String getName() {
      return title;
  }
  // Check if a valid format  
  public boolean validOptions(String productOptions)
  {
  	// check productOptions for "Paperback" or "Hardcover" or "EBook"
  	// if it is one of these, return true, else return false
      if (productOptions.equalsIgnoreCase("Paperback") || productOptions.equalsIgnoreCase("Hardcover") || productOptions.equalsIgnoreCase("EBook")){
          return true;
      }
      return false;
  }

  // Override getStockCount() in super class.
  public int getStockCount(String productOptions)
    {
  	// Use the productOptions to check for (and return) the number of stock for "Paperback" etc
  	// Use the variables paperbackStock and hardcoverStock at the top. 
  	// For "EBook", use the inherited stockCount variable.
        if (productOptions.equalsIgnoreCase("Paperback")){
            return this.paperbackStock;
        } else if (productOptions.equalsIgnoreCase("Hardcover")){
            return this.hardcoverStock;
        } else if (productOptions.equalsIgnoreCase("EBook")){
            return super.getStockCount(productOptions);
        }
        return -1;
	}
  
  public void setStockCount(int stockCount, String productOptions)
	{
    // Use the productOptions to check for (and set) the number of stock for "Paperback" etc
   	// Use the variables paperbackStock and hardcoverStock at the top. 
   	// For "EBook", set the inherited stockCount variable.
        if (productOptions.equalsIgnoreCase("Paperback")){
            this.paperbackStock = stockCount;
        } else if (productOptions.equalsIgnoreCase("Hardcover")){
            this.hardcoverStock = stockCount;
        } else if (productOptions.equalsIgnoreCase("EBook")){
            super.setStockCount(super.getStockCount(productOptions), productOptions);
        }
	}
  /*
   * When a book is ordered, reduce the stock count for the specific stock type
   */
  public void reduceStockCount(String productOptions)
	{
  	// Use the productOptions to check for (and reduce) the number of stock for "Paperback" etc
   	// Use the variables paperbackStock and hardcoverStock at the top. 
   	// For "EBook", set the inherited stockCount variable.
        if (productOptions.equalsIgnoreCase("Paperback")){
            this.paperbackStock -= 1;
        } else if (productOptions.equalsIgnoreCase("Hardcover")){
            this.hardcoverStock -= 1;
        } else if (productOptions.equalsIgnoreCase("EBook")){
            // You don't really need to pass in a parameter, but the skeleton code requires it
            super.reduceStockCount(productOptions);
        }
	}
  /*
   * Print product information in super class and append Book specific information title and author
   */
  public void print()
  {
  	// Replace the line below.
  	// Make use of the super class print() method and append the title and author info. See the video
      // Print the product info first, followed by this Book's info
      super.print();
      System.out.print(" Book " + "Title: " + this.title + " Author: " + this.author + " Published: " + this.year);
  }
    public int compareTo(Book b) {
        return this.year-b.year;
    }
}
