// Student ID: 501087117, Name: Gyu-won An


public class Shoes extends Product{

    // Index 0 of the array represents the size 6 and index 4 represents the size 10
    private int[] brownShoes;
    private int[] blackShoes;

    public Shoes(String name, String id, double price, int[] brownShoes, int[] blackShoes) {
        super(name, id, price, 0, Category.SHOES);
        this.brownShoes = brownShoes;
        this.blackShoes = blackShoes;
    }

    public boolean validOptions(String productOptions) {
        if (productOptions.equals("")) {
            return false;
        }
        // Parse the color and size from productOptions and see if the given options are valid
        // Index 0 of the array represents the size 6 and index 4 represents the size 10
        int size = Integer.parseInt(productOptions.split(" ")[1]);
        String color = productOptions.split(" ")[3];
        if ((size == 6 || size == 7 || size == 8 || size == 9 || size == 10) &&
        (color.equalsIgnoreCase("Black") || (color.equalsIgnoreCase("Brown")))){
            return true;
        }
        return false;
    }

    public int getStockCount(String productOptions)
    {
        // Parse the color and size from productOptions and get the stock count
        int size_index = Integer.parseInt(productOptions.split(" ")[1]) - 6;
        String color = productOptions.split(" ")[3];
        if (color.equalsIgnoreCase("Black")) {
            return this.blackShoes[size_index];
        } else {
            return this.brownShoes[size_index];
        }
    }

    public void setStockCount(int stockCount, String productOptions)
    {
        // set stockCount by size and color
        int size_index = Integer.parseInt(productOptions.split(" ")[1]) - 6;
        String color = productOptions.split(" ")[3];
        if (color.equalsIgnoreCase("Black")) {
            this.blackShoes[size_index] = stockCount;
        } else {
            this.brownShoes[size_index] = stockCount;
        }
    }
    public void reduceStockCount(String productOptions) {
        // Parse the color and size from productOptions and see if stocks are live
        int size_index = Integer.parseInt(productOptions.split(" ")[1]) - 6;
        String color = productOptions.split(" ")[3];
        if (color.equalsIgnoreCase("Black")) {
            this.blackShoes[size_index] -= 1;
        } else {
            this.brownShoes[size_index] -= 1;
        }
    }

    public void print()
    {
        super.print();
    }
}
