package com.example.faktanewongbanten.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.faktanewongbanten.R;
import com.example.faktanewongbanten.activity.AkunSaya;
import com.example.faktanewongbanten.activity.BeritaActvty;
import com.example.faktanewongbanten.activity.EditBerita;
import com.example.faktanewongbanten.model.ModelBerita;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AdapterAkunSaya extends RecyclerView.Adapter<AdapterAkunSaya.ViewProcessHolder> {
    Context context;
    private ArrayList<ModelBerita> item;

    public AdapterAkunSaya(Context context, ArrayList<ModelBerita> item) {
        this.context = context;
        this.item = item;
    }

    @NonNull
    @Override
    public ViewProcessHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.akunsaya_list, parent, false); //memanggil layout list recyclerview
        ViewProcessHolder processHolder = new ViewProcessHolder(view);
        return processHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewProcessHolder holder, int position) {
        final ModelBerita data = item.get(position);
        final int panjang = data.judul.length();
        if (panjang >= 60) {
            final String mIsi = data.judul.substring(0, 60);
            holder.judulBerita.setText(mIsi.concat("..."));
        } else {
            final String mIsi = data.judul;
            holder.judulBerita.setText(mIsi);
        }
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
                Intent intent = new Intent(context, EditBerita.class);
                intent.putExtra("judul", data.getJudul());
                intent.putExtra("gambar", data.getUrl_thumbnail());
                intent.putExtra("tanggal", data.getTanggal_dibuat());
                intent.putExtra("author", data.getAuthor());
                intent.putExtra("isi", data.getIsi());
                context.startActivity(intent);
            }
        });

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EditBerita.class);
                intent.putExtra("judul", data.getJudul());
                intent.putExtra("gambar", data.getUrl_thumbnail());
                intent.putExtra("tanggal", data.getTanggal_dibuat());
                intent.putExtra("author", data.getAuthor());
                intent.putExtra("isi", data.getIsi());
                intent.putExtra("id", data.getId_berita());
                intent.putExtra("idkategori", data.getId_kategori());
                context.startActivity(intent);
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String link_Delete = "https://dimas.bantani.net.id/github/delete_data";

                StringRequest stringRequest = new StringRequest(Request.Method.POST, link_Delete, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //If we are getting success from server
                        if (response.contains("success")) {
                            Toast.makeText(context, "Data sudah di-Hapus!", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(context, AkunSaya.class);
                            context.startActivity(intent);
                        } else {
                            //Displaying an error message on toast
                            Toast.makeText(context, "Gagal Hapus Data!", Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "The server unreachable", Toast.LENGTH_LONG).show();
                    }
                }
                )
                {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> params = new HashMap<>();
                        params.put("id_berita", data.getId_berita());
                        params.put("delete", "berita");
                        //...
                        return params;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(context);
                requestQueue.add(stringRequest);
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
        ImageView imageBerita,delete,edit;

        public ViewProcessHolder(View itemView) {
            super(itemView);
            judulBerita = itemView.findViewById(R.id.judulBerita);
            tglBerita = itemView.findViewById(R.id.tanggalBerita);
            ctyBerita = itemView.findViewById(R.id.categoryBerita);
            cvBerita = itemView.findViewById(R.id.cardBerita);
            imageBerita = itemView.findViewById(R.id.gambarBerita);
            delete = itemView.findViewById(R.id.btnDelete);
            edit = itemView.findViewById(R.id.btnEdit);
        }
    }
}
