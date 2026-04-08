package com.example.q4_photoapp;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {

    private Context context;
    private List<Uri> imageUris;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Uri uri);
    }

    public ImageAdapter(Context context, List<Uri> imageUris, OnItemClickListener listener) {
        this.context = context;
        this.imageUris = imageUris;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_image, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Uri uri = imageUris.get(position);

        holder.img.setImageURI(uri);

        holder.itemView.setOnClickListener(v -> listener.onItemClick(uri));
    }

    @Override
    public int getItemCount() {
        return imageUris.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;

        ViewHolder(View v) {
            super(v);
            img = v.findViewById(R.id.imageView);
        }
    }
}