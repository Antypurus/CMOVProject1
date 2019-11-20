package DataModels;

import java.util.ArrayList;

public class Transaction {

    public String id;
    public String client;
    public String voucher;
    public boolean was_discount_used;
    public ArrayList<Product>products;

    public Transaction(String id, String client, String voucher, boolean was_discount_used, ArrayList<Product> products)
    {
        this.id = id;
        this.client = client;
        this.voucher = voucher;
        this.was_discount_used = was_discount_used;
        this.products = products;
    }

}
