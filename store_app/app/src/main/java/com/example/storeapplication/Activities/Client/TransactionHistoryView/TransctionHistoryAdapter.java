package com.example.storeapplication.Activities.Client.TransactionHistoryView;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import DataModels.Transaction;

public class TransctionHistoryAdapter extends RecyclerView.Adapter<TransctionHistoryAdapter.TransactionHistoryViewHolder>{

    private ArrayList<Transaction> transactions;

    public TransctionHistoryAdapter(ArrayList<Transaction> transactions)
    {
        this.transactions = transactions;
    }

    public static class TransactionHistoryViewHolder extends RecyclerView.ViewHolder
    {

        public TransactionHistoryViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    @NonNull
    @Override
    public TransactionHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionHistoryViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return this.transactions.size();
    }

}
