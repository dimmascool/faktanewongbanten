package com.example.faktanewongbanten.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.faktanewongbanten.activity.BeritaActvty;
import com.example.faktanewongbanten.model.ModelBerita;
import com.example.faktanewongbanten.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterHome extends RecyclerView.Adapter<AdapterHome.ViewProcessHolder> {

    Context context;
    private ArrayList<ModelBerita> item;

    public AdapterHome(Context context, ArrayList<ModelBerita> item) {
        this.context = context;
        this.item = item;
    }

    @NonNull
    @Override
    public ViewProcessHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_list, parent, false); //memanggil layout list recyclerview
        ViewProcessHolder processHolder = new ViewProcessHolder(view);
        return processHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewProcessHolder holder, int position) {
        final ModelBerita data = item.get(position);
        holder.judulBerita.setText(data.judul);
        holder.tglBerita.setText(data.tanggal_dibuat);
        holder.ctyBerita.setText(" | "+data.kategori);

        Picasso picasso = new Picasso.Builder(context.getApplicationContext()).listener(new Picasso.Listener() {
                    @Override
                    public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                        exception.printStackTrace();
                    }
                })
                .build();
        picasso.load(data.url_thumbnail)
                .fit()
                .error(R.drawable.ic_launcher_background)
                .centerCrop().into(holder.imageBerita);
        holder.cvBerita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, BeritaActvty.class);
                intent.putExtra("judul", data.getJudul());
                intent.putExtra("gambar", data.getUrl_thumbnail());
                intent.putExtra("tanggal", data.getTanggal_dibuat());
                intent.putExtra("author", data.getAuthor());
                intent.putExtra("isi", data.getIsi());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return item.size();
    }

    public class ViewProcessHolder extends RecyclerView.ViewHolder {
        TextView judulBerita;
        TextView tglBerita;
        TextView ctyBerita;
        CardView cvBerita;
        ImageView imageBerita;

        public ViewProcessHolder(View itemView) {
            super(itemView);
            judulBerita = itemView.findViewById(R.id.judulBerita);
            tglBerita = itemView.findViewById(R.id.tanggalBerita);
            ctyBerita = itemView.findViewById(R.id.categoryBerita);
            cvBerita = itemView.findViewById(R.id.cardBerita);
            imageBerita = itemView.findViewById(R.id.gambarBerita);
        }
    }
}
