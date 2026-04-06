package com.example.q4_photoapp;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.io.File;
import java.util.List;

// Make sure NO "import android.R;" is at the top!

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {
    private final Context context;
    private final List<File> files;
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(File file);
    }

    public ImageAdapter(Context context, List<File> files, OnItemClickListener listener) {
        this.context = context;
        this.files = files;
        this.listener = listener;
    }

    @NonNull @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // If this line is red, ensure res/layout/item_image.xml exists
        View v = LayoutInflater.from(context).inflate(R.layout.item_image, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        File file = files.get(position);

        if (file != null && file.exists()) {
            holder.img.setImageURI(Uri.fromFile(file));
        }

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(file);
            }
        });
    }

    @Override
    public int getItemCount() {
        return files != null ? files.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        public ViewHolder(View v) {
            super(v);
            // If this line is red, ensure android:id="@+id/imageView" is in item_image.xml
            img = v.findViewById(R.id.imageView);
        }
    }
}