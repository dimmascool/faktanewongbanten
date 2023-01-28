package com.example.faktanewongbanten.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.faktanewongbanten.activity.BeritaActvty;
import com.example.faktanewongbanten.activity.KategoriResultactvty;
import com.example.faktanewongbanten.model.ModelKategori;
import com.example.faktanewongbanten.R;
import com.squareup.picasso.Picasso;

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
        holder.kategori.setText(data.kategori);
        holder.deskripsi.setText(data.deskripsi);
        Picasso.get().load(data.getUrl_gambar_kategori())
                .fit()
                .error(R.drawable.ic_launcher_background)
                .centerCrop().into(holder.gambar);

        holder.select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, KategoriResultactvty.class);
                intent.putExtra("id", data.getId_kategori());
                intent.putExtra("kategori", data.getKategori());
                intent.putExtra("deskripsi", data.getDeskripsi());
                intent.putExtra("gambar", data.getUrl_gambar_kategori());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return item.size();
    }

    public class ViewProcessHolder extends RecyclerView.ViewHolder {
        TextView kategori;
        TextView deskripsi;
        ImageView gambar;
        CardView select;
        public ViewProcessHolder(View itemView) {
            super(itemView);
            select = itemView.findViewById(R.id.cvKategori);
            kategori = itemView.findViewById(R.id.tvKategori);
            gambar = itemView.findViewById(R.id.ivGambarcty);
            deskripsi = itemView.findViewById(R.id.tvDeskripsi);

        }
    }

}
