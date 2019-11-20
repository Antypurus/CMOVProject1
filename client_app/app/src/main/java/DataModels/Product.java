package DataModels;

import org.json.JSONObject;

import java.io.Serializable;

import Common.RSA;

public class Product implements Serializable {

    private String RAWJSON;

    private String productID;
    private String productName;
    private int priceEuro;
    private int priceCent;

    public Product(String data, String key) throws Exception {
        this.RAWJSON = data;
        JSONObject JSONproduct = new JSONObject(data);
        String signature = JSONproduct.getString("sign");
        String rawProduct = JSONproduct.getString("product");
        if (RSA.Verify(rawProduct, signature, key)) {
            JSONObject product = new JSONObject(JSONproduct.getString("product"));
            this.productID = product.getString("id");
            this.productName = product.getString("name");
            this.priceEuro = product.getInt("euros");
            this.priceCent = product.getInt("cents");
        }
    }

    public Product(String data) throws Exception {
        this.RAWJSON = data;
        JSONObject product = new JSONObject(data);
        this.productID = product.getString("id");
        this.productName = product.getString("name");
        this.priceEuro = product.getInt("euros");
        this.priceCent = product.getInt("cents");
    }

    public int getPriceCent() {
        return priceCent;
    }

    public int getPriceEuro() {
        return priceEuro;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductID() {
        return productID;
    }

    public String getRAWJSON() {
        return RAWJSON;
    }
}
