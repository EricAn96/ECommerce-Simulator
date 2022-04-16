// Student ID: 501087117, Name: Gyu-won An
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;

/*
 * Models a simple ECommerce system. Keeps track of products for sale, registered customers, product orders and
 * orders that have been shipped to a customer
 */
public class ECommerceSystem
{
    private ArrayList<Customer> customers = new ArrayList<Customer>();	
    
    private ArrayList<ProductOrder> orders   = new ArrayList<ProductOrder>();
    private ArrayList<ProductOrder> shippedOrders   = new ArrayList<ProductOrder>();
    
    // These variables are used to generate order numbers, customer id's, product id's 
    private int orderNumber = 500;
    private int customerId = 900;
    private int productId = 700;
    // key is the ProductObject,
    // value two parts: popularity at index 0 and ratings from index 1 - 5
    private HashMap<String, Product> products = new HashMap<>();

    public ECommerceSystem()
    {
    	// Create some customers. Notice how generateCustomerId() method is used
    	customers.add(new Customer(generateCustomerId(),"Inigo Montoya", "1 SwordMaker Lane, Florin"));
    	customers.add(new Customer(generateCustomerId(),"Prince Humperdinck", "The Castle, Florin"));
    	customers.add(new Customer(generateCustomerId(),"Andy Dufresne", "Shawshank Prison, Maine"));
    	customers.add(new Customer(generateCustomerId(),"Ferris Bueller", "4160 Country Club Drive, Long Beach"));

        try {
            readTextFile();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
    private void readTextFile() throws FileNotFoundException {
        // Use scanner to read line by line
        Scanner scanner = new Scanner(new File("products.txt"));
        while (scanner.hasNextLine()) {
            Product product;

            // Gather info from lines: category, name, price. productId is generated from the system
            Product.Category category = Product.Category.valueOf(scanner.nextLine());
            String name = scanner.nextLine();
            Double price = Double.valueOf(scanner.nextLine());
            String productId = generateProductId();

            // if this set of lines are for a book, separate the 4th line by space, and the 5th line by :
            if (category == Product.Category.BOOKS) {
                String[] stocks = scanner.nextLine().split("\\s+");
                String[] bookInfo = scanner.nextLine().split(":");

                product = new Book(name, productId, price,
                        Integer.parseInt(stocks[0]), Integer.parseInt(stocks[1]),
                        bookInfo[0], bookInfo[1], Integer.parseInt(bookInfo[2]));
            }
            // if category == SHOES, read stock on 4th line and split it.
            // first 5 are for brown color and the next 5 are for black color shoes
            else if (category == Product.Category.SHOES) {
                String[] stocks = scanner.nextLine().split("\\s+");
                int[] brown = new int[5];
                int[] black = new int[5];
                for (int i = 0; i < 5; i++) {
                    brown[i] = Integer.parseInt(stocks[i]);
                    black[i] = Integer.parseInt(stocks[i+5]);
                }
                product = new Shoes(name, productId, price, brown, black);
                scanner.nextLine();
            }
            // else, just read the stock normally
            else {
                int stock = Integer.parseInt(scanner.nextLine());
                product = new Product(name, productId, price, stock, category);
                scanner.nextLine();
            }
            // key: productId
            // value: Product Object
            this.products.put(productId, product);
        }
        scanner.close();
    }
    private String generateOrderNumber()
    {
    	return "" + orderNumber++;
    }

    private String generateCustomerId()
    {
    	return "" + customerId++;
    }
    
    private String generateProductId()
    {
    	return "" + productId++;
    }
    private Product checkProduct(String productId, String productOptions) {
        // Search if productId exists in products
        Product product = products.get(productId);

        // if the product variable equals to null, set the error message and return null
        if (product == null) {
            throw new UnknownProductException("Failed to find the product with productId: " + productId);
        }
        // Check if the options are valid for this product (e.g. Paperback or Hardcover or EBook for Book product)
        // See class Product and class Book for the method validOptions()
        // If options are not valid, set errMsg string and return null;
        if (!product.validOptions(productOptions)){
            throw new InvalidOptionException("Provided productOptions is not valid: " + productOptions +
                    "\n This product is in " + product.getCategory() + " category.");
        }

        // Check if the product has stock available (i.e. not 0)
        // See class Product and class Book for the method getStockCount()
        // If no stock available, set errMsg string and return null

        if (product.getStockCount(productOptions) <= 0) {
            throw new OutOfStockException("Product with the given productOptions is 0 in stocks.: " + productOptions);
        }

        return product;
    }

    private Customer checkCustomer(String customerId) {
        // iterate over customers arraylist. If the current element's id matches customerId, alias customer variable with
        // the current element
        Customer customer;
        for (Customer c: customers) {
            if (c.getId().equals(customerId)) {
                customer = c;
                return customer;
            }
        }
        throw new UnknownCustomerException("Failed to find the customer with customerId: " + customerId);
    }

    public void printAllProducts()
    {
        // Get all the values from products, cast it, and then print it
        for (Product p: products.values()) {
            p.print();
        }
    }
    
    // Print all products that are books. See getCategory() method in class Product
    public void printAllBooks()
    {
        // iterate over all products. if current element's name is "Book", use the print() of it.
    	for (Product p : products.values()) {
            if (p.getCategory() == Product.Category.BOOKS) {
                p.print();
            }
        }
    }
    public void printAllShoes()
    {
        // iterate over all products. if current element's name is "Shoes", use the print() of it.
        for (Product p : products.values()) {
            if (p.getCategory() == Product.Category.SHOES) {
                p.print();
            }
        }
    }
    // all books by the given author are printed in increasing order of year published
    public void printAllBooksByAuthor(String author) {

        ArrayList<Book> books = new ArrayList<Book>();
        // iterate over all products. if current element's name is "Book" AND author's name is equal to given author variable,
        // cast and add it to books arrayList
        for (Product p : products.values()) {
            if (p.getCategory() == Product.Category.BOOKS) {
                Book book = (Book) p;
                if (book.getAuthor().equals(author)){
                    books.add(book);
                }
            }
        }
        // sort it by year
        Collections.sort(books, Book::compareTo);

        // print all books
        for (Book b: books) {
            b.print();
        }
    }

    // Print all current orders
    public void printAllOrders()
    {
        // iterate over the orders and print them
    	for (ProductOrder po: orders) {
            po.print();
        }
    }
    // Print all shipped orders
    public void printAllShippedOrders()
    {
        // iterate over the shippedOrders and print them
        for (ProductOrder po: shippedOrders) {
            po.print();
        }
    }
    
    // Print all customers
    public void printCustomers()
    {
        // iterate over the customers and print them
        for (Customer p: customers) {
            p.print();
        }
    }
    /*
     * Given a customer id, print all the current orders and shipped orders for them (if any)
     */
    public void printOrderHistory(String customerId)
    {
      // Make sure customer exists - check using customerId
    	// If customer does not exist, set errMsg String and return false
    	// see video for an appropriate error message string
    	// ... code here

        // iterate over customers. if no customer was found that matches 'customerId', alias that customer with the variable
        // foundCustomer. if foundCustomer remains as null after the iteration, set the errMsg and return false.
        Customer foundCustomer = checkCustomer(customerId);

    	// Print current orders of this customer 
    	System.out.println("Current Orders of Customer " + customerId);
    	// enter code here

        // iterate over the orders arraylist. if the order's id matches with foundCustomer's id, use its print() to print the infos
    	for (ProductOrder po: orders) {
            if (po.getCustomer().getId().equals(foundCustomer.getId())){
                po.print();
            }
        }
    	// Print shipped orders of this customer 
    	System.out.println("\nShipped Orders of Customer " + customerId);
    	//enter code here

        // iterate over the shippedOrders arraylist. If the order's id matches with foundCustomer's id, use its print() to print the infos
        for (ProductOrder po: shippedOrders) {
            if (po.getCustomer().getId().equals(foundCustomer.getId())){
                po.print();
            }
        }
    }
    
    public void orderProduct(String productId, String customerId, String productOptions)
    {

        Customer customer = checkCustomer(customerId);
        Product product = checkProduct(productId, productOptions);

        // reduce stock count of product by 1
        product.reduceStockCount(productOptions);

        // increase the popularity of the product by 1
        product.setPopularity(product.getPopularity() + 1);

        // Create a ProductOrder, (make use of generateOrderNumber() method above)
    	// Add to orders list and return order number string
        ProductOrder newOrder = new ProductOrder(generateOrderNumber(), product, customer, productOptions);
        orders.add(newOrder);
        System.out.println("Order #" + newOrder.getOrderNumber());
    }

    /*
     * Create a new Customer object and add it to the list of customers
     */
    
    public void createCustomer(String name, String address)
    {
    	// Check name parameter to make sure it is not null or ""
    	// If it is not a valid name, set errMsg (see video) and return false
    	// Repeat this check for address parameter
    	if (name == null || name.equals("")) {
            throw new InvalidCustomerNameException("Given name is invalid.");
        }
        if (address == null || address.equals("")) {
            throw new InvalidCustomerAddressException("Given address is invalid.");
        }
    	// Create a Customer object and add to array list
        Customer customer = new Customer(generateCustomerId(), name, address);
        customers.add(customer);
    }
    
    public void shipOrder(String orderNumber)
    {
      // Check if order number exists first. If it doesn't, set errMsg to a message (see video) 
    	// and return false
    	// Retrieve the order from the orders array list, remove it, then add it to the shippedOrders array list
    	// return a reference to the order

        // Go through every element in orders arraylist and add to newOrders if orderNumber does not equal to the element's
        // order number. If an element with orderNumber was not found, set error message and return null. If found,
        // set this.orders as newOrders and return the reference object.
        ArrayList<ProductOrder> newOrders = new ArrayList<ProductOrder>();
        ProductOrder reference = null;
        for (ProductOrder o: orders){
            if (!o.getOrderNumber().equals(orderNumber)) {
                newOrders.add(o);
            } else {
                reference = o;
            }
        }
        if (reference == null) {
            throw new InvalidOrderNumberException("Given orderNumber is invalid.");
        }
        shippedOrders.add(reference);
        orders = newOrders;
        reference.print();
    }
    
    /*
     * Cancel a specific order based on order number
     */
    public void cancelOrder(String orderNumber)
    {
        // Check if order number exists first. If it doesn't, set errMsg to a message (see video)
        // and return false

        // iterate over orders arraylist and if an element's order number matches the orderNumber, alias the variable po
        // to that element. If no element with orderNumber was found, set the error message and return false.
        // Else, delete the element and return true
    	ProductOrder po = null;

        for (ProductOrder p: orders) {
            if (p.getOrderNumber().equals(orderNumber)) {
                po = p;
            }
        }

        if (po == null) {
            throw new InvalidOrderNumberException("Given orderNumber is invalid.");
        }

        // iterate over the orders arraylist, and add every element to newOrders arraylist except for the element that has
        // the same order number as orderNumber. return true
        ArrayList<ProductOrder> newOrders = new ArrayList<ProductOrder>();
        for (ProductOrder p: orders){
            if (p.getOrderNumber().equals(orderNumber)){
                continue;
            }
            newOrders.add(p);
        }
        this.orders = newOrders;
    }
    
    // Sort products by increasing price
    public void printByPrice()
    {

        // create a temporary arraylist to store all products
        ArrayList<Product> tmp = new ArrayList<Product>();
        for (Product p : products.values()) {
            tmp.add(p);
        }

        // leverage the compareByPrice() function of class Product to sort the arraylist products
        Collections.sort(tmp, Product::compareByPrice);

        // print
        for (Product p: tmp) {
            p.print();
        }
    }
    
    // Sort products alphabetically by product name
    public void printByName()
    {
        // create a temporary arraylist to store all products
        ArrayList<Product> tmp = new ArrayList<Product>();
        for (Product p : products.values()) {
            tmp.add(p);
        }

        // leverage the compareTo() function of class Product to sort the arraylist products
        Collections.sort(tmp, Product::compareTo);

        // print
        for (Product p: tmp) {
            p.print();
        }
    }

    public void printCustCart(String customerId) {

        // check if customer exists. If exists, print all the items in the person's cart
        Customer customer = checkCustomer(customerId);

        ArrayList<CartItem> cart = customer.getCart().getCartItems();
        int index = 1;
        for (CartItem c: cart) {
            System.out.print("\n" + index + ".");
            c.print();
            index += 1;
        }
    }

    public void printByRating(int rating, String category) {
        if (!category.equals("COMPUTERS") && !category.equals("GENERAL") && !category.equals("BOOKS") &&
                !category.equals("FURNITURE") && !category.equals("SHOES") && !category.equals("CLOTHING")) {
            throw new InvalidCategoryException("Invalid Category.");
        }

        // store all the products that matches the criteria in tmp arraylist
        ArrayList<Product> tmp = new ArrayList<>();
        for (Product p: products.values()) {
            if (p.getRating() >= rating && p.getCategory() == Product.Category.valueOf(category)) {
                tmp.add(p);
            }
        }
        // print
        for (Product p: tmp) {
            p.print();
        }
    }

    // Sort customers alphabetically by customer name
    public void sortCustomersByName()
    {
        // leverage the overwritten compareTo() function of class Customer to sort the arraylist customers
        Collections.sort(customers, Customer::compareTo);
    }

    public void addToCart(String productId, String customerId, String productOptions) {
        // retrieve the customer and product objects if the input parameters are valid
        Customer customer = checkCustomer(customerId);
        Product product = checkProduct(productId, productOptions);

        boolean found = false;
        ArrayList<CartItem> items = customer.getCart().getCartItems();

        // reduce the stock count
        product.reduceStockCount(productOptions);

        // iterate over items that are in the customer's cart
        // add the product into the card if it is new, add the quantity if the product exists in the cart
        for (CartItem c: items) {
            if (c.getProduct().compareTo(product) == 0 && c.getOption().equals(productOptions)) {
                c.addQuantity();
                found = true;
            }
        }
        if (!found) {
            items.add(new CartItem(product, productOptions));
        }
    }

    public void removeCartItem(String customerId, int index, int quantity) {
        // get the cartItem and corresponding product
        Customer customer = checkCustomer(customerId);
        CartItem cartItem = customer.getCart().getCartItems().get(index);
        Product product = cartItem.getProduct();

        // check for invalid inputs
        if (quantity > cartItem.getQuantity() || quantity <= 0) {
            throw new InvalidCartItemIndexException("Given index is out of range.");
        }
        if (index > customer.getCart().getCartItems().size()){
            throw new InvalidCartItemQuantityException("Given quantity is bigger than existing CartItem quantity.");
        }

        // increase the stock in the system
        int newQuantity = cartItem.getQuantity()+quantity;
        product.setStockCount(newQuantity, cartItem.getOption());

        // decrease the quantity of the cartItem
        cartItem.setQuantity(cartItem.getQuantity()-quantity);

        // if cartItem's quantity is 0, remove it from the customer's cart
        if (cartItem.getQuantity() == 0) {
            customer.getCart().getCartItems().remove(cartItem);
            System.out.println("Qty is now 0. Item removed from cart.");
        } else {
            System.out.println("Item successfully removed from the cart");
        }
    }

    public void orderAll(String customerId) {
        // iterate over customer cart and call orderProduct()
        Customer customer = checkCustomer(customerId);
        ArrayList<CartItem> cart = customer.getCart().getCartItems();
        for (CartItem c: cart) {
            for (int i = 0; i < c.getQuantity(); i++) {
                orderProduct(c.getProduct().getId(), customerId, c.getOption());
            }
        }
        cart.clear();
    }

    public void showStats() {
        // create a temporary arraylist to store all products
        ArrayList<Product> tmp = new ArrayList<>(products.values());

        // sort them by the popularity and reverse it
        tmp.sort(Comparator.comparing(Product::getPopularity));
        Collections.reverse(tmp);

        // print each products
        for (Product p : tmp) {
            System.out.println(p.getName() + " ID: " + p.getId() + " sold: " + p.getPopularity());
        }
    }

    public void rateProduct(String customerId, String productId, String rating, String comment) {
        // check if customerId is valid
        checkCustomer(customerId);

        // retrieve product object
        Product product = products.get(productId);
        // raise an exeption if product is null
        if (product == null) {
            throw new UnknownProductException("Failed to find the product with productId: " + productId);
        }
        // raise an exception if rating goes out of bounds
        if (!rating.equals("5") && !rating.equals("4") && !rating.equals("3") && !rating.equals("2") && !rating.equals("1")) {
            throw new InvalidRatingException("Rating number is invalid (1-5 ints only)");
        }
        // raise an exception if comment is empty string
        if (comment.equals("")) {
            throw new InvalidCommentException("Comment string cannot be empty");
        }

        int intRating = Integer.parseInt(rating);
        // if the number of its reviews is 0, set its rating to given variable rating
        if (product.getNumberOfReviews() == 0) {
            product.setRating(intRating);
            product.setNumberOfReviews(1);
        } else {
            // calculate the new rating with the given rating
            double newRating = (product.getRating() * product.getNumberOfReviews() + intRating) /
                                                        (product.getNumberOfReviews() + 1);
            product.setRating(newRating);
            product.setNumberOfReviews(product.getNumberOfReviews()+1);
        }

        // save user comment in the review arraylist
        product.getReviews()[intRating-1].add("Customer #" + customerId + ": " + comment);
        System.out.println("Rating and comment saved successfully");
    }
}

class UnknownCustomerException extends RuntimeException {
    public UnknownCustomerException(String message)
    {
        super(message);
    }
}
class UnknownProductException extends RuntimeException {
    public UnknownProductException(String message)
    {
        super(message);
    }
}
class OutOfStockException extends RuntimeException {
    public OutOfStockException(String message)
    {
        super(message);
    }
}
class InvalidCustomerNameException extends RuntimeException {
    public InvalidCustomerNameException(String message)
    {
        super(message);
    }
}
class InvalidCustomerAddressException extends RuntimeException {
    public InvalidCustomerAddressException(String message)
    {
        super(message);
    }
}
class InvalidOrderNumberException extends RuntimeException {
    public InvalidOrderNumberException(String message)
    {
        super(message);
    }
}
class InvalidOptionException extends RuntimeException {
    public InvalidOptionException(String message)
    {
        super(message);
    }
}
class InvalidCartItemIndexException extends RuntimeException {
    public InvalidCartItemIndexException(String message)
    {
        super(message);
    }
}
class InvalidCartItemQuantityException extends RuntimeException {
    public InvalidCartItemQuantityException(String message)
    {
        super(message);
    }
}
class InvalidRatingException extends RuntimeException {
    public InvalidRatingException(String message)
    {
        super(message);
    }
}
class InvalidCommentException extends RuntimeException {
    public InvalidCommentException(String message)
    {
        super(message);
    }
}
class InvalidCategoryException extends RuntimeException {
    public InvalidCategoryException(String message)
    {
        super(message);
    }
}