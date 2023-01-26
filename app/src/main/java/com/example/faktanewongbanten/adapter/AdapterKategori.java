package com.example.faktanewongbanten.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.faktanewongbanten.model.ModelKategori;
import com.example.faktanewongbanten.R;

import java.util.ArrayList;

public class AdapterKategori extends RecyclerView.Adapter<AdapterKategori.ViewProcessHolder> {

    Context context;
    private ArrayList<ModelKategori> item;

    public AdapterKategori(Context context, ArrayList<ModelKategori> item) {
        this.context = context;
        this.item = item;
    }

    @NonNull
    @Override
    public ViewProcessHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.kategori_list, parent, false); //memanggil layout list recyclerview
        ViewProcessHolder processHolder = new ViewProcessHolder(view);
        return processHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewProcessHolder holder, int position) {
        final ModelKategori data = item.get(position);
        holder.kategori.setText(data.kategori+" | ");
    }

    @Override
    public int getItemCount() {
        return item.size();
    }

    public class ViewProcessHolder extends RecyclerView.ViewHolder {
        TextView kategori;
        public ViewProcessHolder(View itemView) {
            super(itemView);
        }
    }

}
