// Student ID: 501087117, Name: Gyu-won An
import java.util.Locale;
import java.util.Scanner;

// Simulation of a Simple E-Commerce System (like Amazon)

public class ECommerceUserInterface
{
	public static void main(String[] args)
	{
		// Create the system
		ECommerceSystem amazon = new ECommerceSystem();

		Scanner scanner = new Scanner(System.in);
		System.out.print(">");
		
		// Process keyboard actions
		while (scanner.hasNextLine())
		{
			try{
				String action = scanner.nextLine();

				if (action == null || action.equals(""))
				{
					System.out.print("\n>");
					continue;
				}
				else if (action.equalsIgnoreCase("Q") || action.equalsIgnoreCase("QUIT"))
					return;

				else if (action.equalsIgnoreCase("PRODS"))	// List all products for sale
				{
					amazon.printAllProducts();
				}
				else if (action.equalsIgnoreCase("BOOKS"))	// List all books for sale
				{
					amazon.printAllBooks();
				}
				else if (action.equalsIgnoreCase("SHOES"))	// List all shoes for sale
				{
					amazon.printAllShoes();
				}
				else if (action.equalsIgnoreCase("CUSTS")) 	// List all registered customers
				{
					amazon.printCustomers();
				}
				else if (action.equalsIgnoreCase("ORDERS")) // List all current product orders
				{
					amazon.printAllOrders();
				}
				else if (action.equalsIgnoreCase("SHIPPED"))	// List all orders that have been shipped
				{
					amazon.printAllShippedOrders();
				}
				else if (action.equalsIgnoreCase("NEWCUST"))	// Create a new registered customer
				{
					String name = "";
					String address = "";

					System.out.print("Name: ");
					if (scanner.hasNextLine())
						name = scanner.nextLine();

					System.out.print("\nAddress: ");
					if (scanner.hasNextLine())
						address = scanner.nextLine();

					amazon.createCustomer(name, address);
				}
				else if (action.equalsIgnoreCase("SHIP"))	// ship an order to a customer
				{
					String orderNumber = "";

					System.out.print("Order Number: ");
					// Get order number from scanner
					Scanner innerScanner = new Scanner(System.in);
					orderNumber = innerScanner.nextLine();
					// Ship order to customer (see ECommerceSystem for the correct method to use
					amazon.shipOrder(orderNumber);
				}
				else if (action.equalsIgnoreCase("CUSTORDERS")) // List all the current orders and shipped orders for this customer id
				{
					String customerId = "";

					System.out.print("Customer Id: ");
					// Get customer Id from scanner
					Scanner innerScanner = new Scanner(System.in);
					customerId = innerScanner.nextLine();

					// Print all current orders and all shipped orders for this customer
					amazon.printOrderHistory(customerId);

				}
				else if (action.equalsIgnoreCase("ORDER")) // order a product for a certain customer
				{
					String productId = "";
					String customerId = "";

					System.out.print("Product Id: ");
					Scanner innerScanner = new Scanner(System.in);
					// Get product Id from scanner
					productId = innerScanner.nextLine();

					System.out.print("\nCustomer Id: ");
					// Get customer Id from scanner
					customerId = innerScanner.nextLine();

					// Order the product. Check for valid orderNumber string return and for error message set in ECommerceSystem
					amazon.orderProduct(productId, customerId, "");
				}
				else if (action.equalsIgnoreCase("ORDERBOOK")) // order a book for a customer, provide a format (Paperback, Hardcover or EBook)
				{
					String productId = "";
					String customerId = "";
					String options = "";
					Scanner innerScanner = new Scanner(System.in);

					// get product id
					System.out.print("Product Id: ");
					productId = innerScanner.nextLine();

					// get customer id
					System.out.print("\nCustomer Id: ");
					customerId = innerScanner.nextLine();

					// get book form and store in options string
					System.out.print("\nFormat [Paperback Hardcover EBook]: ");
					options = innerScanner.nextLine();

					// Order product. Check for error mesage set in ECommerceSystem
					amazon.orderProduct(productId, customerId, options);
				}
				else if (action.equalsIgnoreCase("ORDERSHOES")) // order shoes for a customer, provide size and color
				{
					Scanner innerScanner = new Scanner(System.in);

					// get product id
					System.out.print("Product Id: ");
					String productId = innerScanner.nextLine();

					// get customer id
					System.out.print("\nCustomer Id: ");
					String customerId = innerScanner.nextLine();

					// get shoe size and store in options
					System.out.print("\nSize: \"6\" \"7\" \"8\" \"9\" \"10\": ");
					String size = innerScanner.nextLine();
					// if the size is out of range, shoot out the error

					// get shoe color and append to options
					System.out.print("\nColor: \"Black\" or \"Brown\": ");
					String color = innerScanner.nextLine();
					// if the size is neither Black nor Brown, shoot out the error

					//order shoes
					amazon.orderProduct(productId, customerId,"size: " + size + " color: " + color);
				}


				else if (action.equalsIgnoreCase("CANCEL")) // Cancel an existing order
				{
					String orderNumber = "";
					// get order number from scanner
					System.out.print("Order Number: ");
					Scanner innerScanner = new Scanner(System.in);
					orderNumber = innerScanner.nextLine();
					amazon.cancelOrder(orderNumber);
					// cancel order. Checks for error
					System.out.println("Cancelled order Number: " + orderNumber);
				}
				else if (action.equalsIgnoreCase("PRINTBYPRICE")) // sort products by price
				{
					amazon.printByPrice();
				}
				else if (action.equalsIgnoreCase("PRINTBYNAME")) // sort products by name (alphabetic)
				{
					amazon.printByName();
				}
				else if (action.equalsIgnoreCase("SORTCUSTS")) // sort customers by name (alphabetic)
				{
					amazon.sortCustomersByName();
				}
				else if (action.equalsIgnoreCase("BOOKSBYAUTHOR")) { // all books by the given author are printed in increasing order of year
					String author = "";
					// get author name from scanner
					System.out.print("Author Name: ");
					Scanner innerScanner = new Scanner(System.in);
					author = innerScanner.nextLine();
					amazon.printAllBooksByAuthor(author);

				}
				else if (action.equalsIgnoreCase("ADDTOCART")) { // all books by the given author are printed in increasing order of year
					String productId;
					String customerId;
					String productOptions = "";
					String isBook;
					String isShoes;

					// Get productId and customerId
					Scanner innerScanner = new Scanner(System.in);
					System.out.print("Product Id: ");
					productId = innerScanner.nextLine();
					System.out.print("\nCustomer Id: ");
					customerId = innerScanner.nextLine();

					// Check if this product is book
					System.out.print("\nIs this item a book? (Yes/No): ");
					isBook = innerScanner.nextLine();
					if (isBook.equalsIgnoreCase("YES")) {
						System.out.print("\nFormat [Paperback Hardcover EBook]: ");
						productOptions = innerScanner.nextLine();
					}
					// if the product is not a book, check if this is shoes
					if (!isBook.equalsIgnoreCase("YES")){
						System.out.print("\nIs this item a pair of Shoes? (Yes/No): ");
						isShoes = innerScanner.nextLine();
						if (isShoes.equalsIgnoreCase("Yes")) {
							System.out.print("\nSize: \"6\" \"7\" \"8\" \"9\" \"10\": ");
							String size = innerScanner.nextLine();

							// get shoe color and append to options
							System.out.print("\nColor: \"Black\" or \"Brown\": ");
							String color = innerScanner.nextLine();
							productOptions = "size: " + size + " color: " + color;
						}
					}
					// Print the success msg if success
					amazon.addToCart(productId, customerId, productOptions);
					System.out.print("\nProduct #" + productId + " \nOption(s):[" + productOptions + "]\nwas added to the cart of Customer #" + customerId);

				}else if (action.equalsIgnoreCase("REMCARTITEM")) {
					String customerId = "";

					// get customer id and printout all the items in the car
					Scanner innerScanner = new Scanner(System.in);
					System.out.print("customer Id: ");
					customerId = innerScanner.nextLine();
					amazon.printCustCart(customerId);

					// get more inputs from the user to choose which item to remove and how many to remove
					int index;
					int quantity;
					System.out.print("\nChoose the index of the item to remove: ");
					index = Integer.parseInt(innerScanner.nextLine())-1;
					System.out.print("\nHow many to remove? (MAX:existing quantity MIN:1): ");
					quantity = Integer.parseInt(innerScanner.nextLine());

					amazon.removeCartItem(customerId, index, quantity);

				} else if (action.equalsIgnoreCase("PRINTCART")) {
					// get user input to get customer id
					String customerId = "";
					Scanner innerScanner = new Scanner(System.in);
					System.out.print("customer Id: ");
					customerId = innerScanner.nextLine();

					// prints all the items in the person's cart if successful
					amazon.printCustCart(customerId);

				}else if (action.equalsIgnoreCase("ORDERITEMS")) {
					// get customerId
					String customerId;
					Scanner innerScanner = new Scanner(System.in);
					System.out.print("customer Id: ");
					customerId = innerScanner.nextLine();

					// order all items in the cart
					amazon.orderAll(customerId);
					System.out.println("All items are added to orders");
				} else if (action.equalsIgnoreCase("STATS")) {
					amazon.showStats();
				} else if (action.equalsIgnoreCase("RATE")) {
					String customerId;
					String productId;
					String rating;
					String comment;

					// Gather information from the user
					Scanner innerScanner = new Scanner(System.in);
					System.out.print("customer Id: ");
					customerId = innerScanner.nextLine();
					System.out.print("\nproduct Id: ");
					productId = innerScanner.nextLine();
					System.out.print("\nRating: ");
					rating = innerScanner.nextLine();
					System.out.print("\nComment: ");
					comment = innerScanner.nextLine();

					amazon.rateProduct(customerId, productId, rating, comment);

				} else if (action.equalsIgnoreCase("PRINTBYRATING")) {
					int rating;
					String category;

					// Gather information from the user
					Scanner innerScanner = new Scanner(System.in);

					System.out.print("Enter the minimum rating: ");
					rating = Integer.parseInt(innerScanner.nextLine());
					System.out.print("\nEnter the category: ");
					category = innerScanner.nextLine().toUpperCase();
					amazon.printByRating(rating, category);

				}
				System.out.print("\n>");
			} catch (RuntimeException e) {
				System.out.println(e.getClass().getName() + ": " + e.getMessage());
				System.out.print(">");
			}

		}
	}
}
