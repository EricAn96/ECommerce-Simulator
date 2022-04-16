public class CartItem implements Comparable<CartItem>{

    private Product product;
    private int quantity;
    private String option;

    public CartItem(Product product, String option){
        this.product = product;
        this.quantity = 1;
        this.option = option;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void addQuantity() {
        this.quantity += 1;
    }

    public String getOption() {
        return option;
    }

    public void print() {
        this.product.print();
        System.out.print("\t Option(s):[" + this.option + "]\t Qty: " + this.quantity + "\n");
    }

    @Override
    public int compareTo(CartItem o) {
        if (this.product.compareTo(o.product) == 0 && this.option.equalsIgnoreCase(o.option)) {
            return 1;
        }
        return 0;
    }
}
