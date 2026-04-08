package com.example.q4_photoapp;

import android.app.AlertDialog;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.io.File;
import java.util.Date;

public class ImageDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // FIX 1: This requires res/layout/activity_image_detail.xml to exist
        setContentView(R.layout.activity_image_detail);

        String path = getIntent().getStringExtra("path");

        if (path == null) {
            Toast.makeText(this, "Error: Image path not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        File file = new File(path);

        // FIX 2: These IDs MUST match the XML file exactly
        ImageView img = findViewById(R.id.detailImageView);
        TextView details = findViewById(R.id.txtDetails);
        Button btnDelete = findViewById(R.id.btnDelete);

        if (file.exists()) {
            img.setImageURI(Uri.fromFile(file));

            String info = "Name: " + file.getName() +
                    "\n\nPath: " + file.getAbsolutePath() +
                    "\n\nSize: " + (file.length() / 1024) + " KB" +
                    "\n\nDate: " + new Date(file.lastModified());
            details.setText(info);
        }

        // FIX 3: Setting the listener on the button variable
        btnDelete.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Confirm Delete")
                    .setMessage("Delete this image forever?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        if (file.delete()) {
                            Toast.makeText(this, "Image Deleted", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(this, "Failed to delete", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        });
    }
}