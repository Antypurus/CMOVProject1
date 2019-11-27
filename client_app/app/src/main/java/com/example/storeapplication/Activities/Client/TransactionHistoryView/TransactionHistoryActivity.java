package com.example.storeapplication.Activities.Client.TransactionHistoryView;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clientapplicaton.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import Common.Constants;
import Common.HTTP.HTTP;
import Common.HTTP.HTTPResultHandler;
import DataModels.ClientSystem;
import DataModels.Product;
import DataModels.Transaction;

public class TransactionHistoryActivity extends AppCompatActivity {

    RecyclerView transactionsList;

    @Override
    public void onCreate(Bundle savedInstances)
    {
        super.onCreate(savedInstances);
        setContentView(R.layout.transaction_history);

        this.transactionsList = findViewById(R.id.transactions);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        transactionsList.setLayoutManager(linearLayoutManager);

        try {
            HashMap<String,String> headers = new HashMap<>();
            headers.put("user_id", ClientSystem.GetSystem().ClientUserID);
            HTTP.GetRequest(Constants.Transaction_History_Route, headers, new HTTPResultHandler() {
                @Override
                public void Handler(Object result) {
                    // parse response
                    try {
                        ArrayList<Transaction> transactions = new ArrayList<>();
                        JSONArray transactionsJSON = new JSONArray((String)result);
                        for(int i=0;i<transactionsJSON.length();++i)
                        {
                            ArrayList<Product> products = new ArrayList<>();
                            JSONObject transactionJSON = (JSONObject) transactionsJSON.get(i);
                            String transaction_id = transactionJSON.getString("transaction_id");
                            String client_id = transactionJSON.getString("client_id");
                            boolean was_discounter = transactionJSON.getBoolean("was_discounted");
                            JSONArray productsJSON = new JSONArray((String)transactionJSON.getString("products"));
                            for(int prod = 0;prod<productsJSON.length();++prod)
                            {
                                String productJSON = productsJSON.get(prod).toString();
                                Product product = new Product(productJSON);
                                products.add(product);
                            }
                            Transaction transaction = new Transaction(transaction_id,client_id,"",was_discounter,products);
                            transactions.add(transaction);
                        }
                        TransctionHistoryAdapter adapter = new TransctionHistoryAdapter(transactions);
                        transactionsList.setAdapter(adapter);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
