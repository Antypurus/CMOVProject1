package com.example.storeapplication.Activities.Client.TransactionHistoryView;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clientapplicaton.R;

import java.util.ArrayList;

import DataModels.Product;

public class TransactionViewActivity extends AppCompatActivity {

    TextView header;
    RecyclerView productList;

    ArrayList<Product> products;

    @Override
    public void onCreate(Bundle savedInstances)
    {
        super.onCreate(savedInstances);
        setContentView(R.layout.transaction);

        Bundle b = getIntent().getExtras();
        products = (ArrayList<Product>)b.getSerializable("products");
        String id = (String)b.getSerializable("id");

        header = findViewById(R.id.transaction_id);
        productList = findViewById(R.id.products);

        header.setText(id);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        TransactionAdapter adapter = new TransactionAdapter(products);
        productList.setLayoutManager(manager);
        productList.setAdapter(adapter);
    }

}
