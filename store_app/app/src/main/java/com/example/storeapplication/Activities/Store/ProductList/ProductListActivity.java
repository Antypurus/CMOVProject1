package com.example.storeapplication.Activities.Store.ProductList;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.storeapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import Common.HTTP.HTTP;
import Common.HTTP.HTTPResultHandler;
import DataModels.Product;

public class ProductListActivity extends AppCompatActivity {

    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstances)
    {
        super.onCreate(savedInstances);
        setContentView(R.layout.product_list);

        layoutManager = new LinearLayoutManager(this);

        try {
            HTTP.GetRequest("10.0.0.5:80/products", null, new HTTPResultHandler() {
                @Override
                public void Handler(Object result) {
                    try {
                        String res = (String)result;
                        JSONObject JSONResult = new JSONObject(res);
                        String key = JSONResult.getString("key");
                        String prods = JSONResult.getString("products");
                        JSONArray JSONProds = new JSONArray(prods);
                        ArrayList<Product> products = new ArrayList<>();
                        for(int i=0;i<JSONProds.length();++i)
                        {
                            JSONObject jsonproduct = new JSONObject(JSONProds.getString(i));
                            products.add(new Product(jsonproduct.getString("product"),key));
                        }

                        RecyclerView list = findViewById(R.id.productList);
                        list.setLayoutManager(layoutManager);
                        ProductListAdapter prodList = new ProductListAdapter(products);
                        list.setAdapter(prodList);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
