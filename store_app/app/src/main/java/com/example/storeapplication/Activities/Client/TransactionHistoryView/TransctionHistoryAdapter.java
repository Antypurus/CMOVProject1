package com.example.storeapplication.Activities.Client.TransactionHistoryView;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.storeapplication.R;

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
        public TextView name;
        public Transaction transaction;
        public TransactionHistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.transaction_date);
            this.itemView.setOnClickListener(view->OpenTransaction());
        }

        private void OpenTransaction()
        {
            Intent intent = new Intent(this.itemView.getContext(),TransactionViewActivity.class);
            intent.putExtra("products",this.transaction.products);
            this.itemView.getContext().startActivity(intent);
        }

    }

    @NonNull
    @Override
    public TransactionHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ConstraintLayout layout = (ConstraintLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_list_entry,parent,false);
        TransactionHistoryViewHolder holder = new TransactionHistoryViewHolder(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionHistoryViewHolder holder, int position) {
        holder.name.setText(this.transactions.get(position).id);
        holder.transaction = this.transactions.get(position);
    }

    @Override
    public int getItemCount() {
        return this.transactions.size();
    }

}
