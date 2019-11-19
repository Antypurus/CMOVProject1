package DataModels;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

public class Cart {

    private CopyOnWriteArrayList<Product> products;

    public Cart() {
        this.products = new CopyOnWriteArrayList<>();
    }

    public void AddProduct(Product product) {
        this.products.add(product);
    }

    public void RemoveProduct(Product product) {
        this.products.remove(product);
    }

    public CopyOnWriteArrayList<Product> GetProducts() {
        return this.products;
    }

}
