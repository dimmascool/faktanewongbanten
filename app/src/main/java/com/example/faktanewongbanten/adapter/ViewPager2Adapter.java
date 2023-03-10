package com.example.faktanewongbanten.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.faktanewongbanten.model.ModelBerita;
import com.example.faktanewongbanten.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

@SuppressWarnings("ALL")
public class ViewPager2Adapter extends RecyclerView.Adapter<ViewPager2Adapter.ViewHolder> {

    // Array of images
    // Adding images from drawable folder
    private ArrayList<ModelBerita> images;
    private Context ctx;

    // Constructor of our ViewPager2Adapter class
    public ViewPager2Adapter(Context context, ArrayList<ModelBerita> images) {
        this.ctx = context;
        this.images = images;
    }

    // This method returns our layout
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.image_holder, parent, false);
        return new ViewHolder(view);
    }

    // This method binds the screen with the view
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // This will set the images in imageview
        final ModelBerita data = images.get(position);
//        Log.e("vpg",data.url_thumbnail);

        Picasso picasso = new Picasso.Builder(ctx).listener(new Picasso.Listener() {
                    @Override
                    public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                        exception.printStackTrace();
                    }
                })
                .build();
        picasso.load(data.url_thumbnail)
                .fit()
                .centerCrop()
                .into(holder.images);

        final int panjang = data.judul.length();
        if (panjang >= 68) {
            final String mIsi = data.judul.substring(0, 68);
            holder.judulBerita.setText(mIsi.concat("..."));
        } else {
            final String mIsi = data.judul;
            holder.judulBerita.setText(mIsi);
        }

    }
    // This Method returns the size of the Array
    @Override
    public int getItemCount() {
        return images.size();
    }

    // The ViewHolder class holds the view
    @SuppressWarnings("CanBeFinal")
    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView images;
        TextView judulBerita;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            images = itemView.findViewById(R.id.images);
            judulBerita = itemView.findViewById(R.id.tvJudulVPG);
        }

    }
}
