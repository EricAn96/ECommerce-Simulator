import java.util.ArrayList;

public class Cart {

    private ArrayList<CartItem> cartItems;

    public Cart(){
        cartItems = new ArrayList<>();
    }

    public ArrayList<CartItem> getCartItems() {
        return cartItems;
    }
}
