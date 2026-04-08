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
import java.text.SimpleDateFormat;
import java.util.Date;

public class ImageDetailActivity extends AppCompatActivity {

    private Uri imageUri;
    private File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_detail);

        // ✅ Back button
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        ImageView img = findViewById(R.id.detailImageView);
        TextView details = findViewById(R.id.txtDetails);
        Button btnDelete = findViewById(R.id.btnDelete);

        String uriString = getIntent().getStringExtra("uri");

        if (uriString == null) {
            finish();
            return;
        }

        imageUri = Uri.parse(uriString);
        img.setImageURI(imageUri);

        // ✅ Convert Uri → File
        file = new File(imageUri.getPath());

        if (file.exists()) {
            String info = "Name: " + file.getName() +
                    "\n\nPath: " + file.getAbsolutePath() +
                    "\n\nSize: " + (file.length() / 1024) + " KB" +
                    "\n\nDate: " + new SimpleDateFormat("dd-MM-yyyy HH:mm")
                    .format(new Date(file.lastModified()));

            details.setText(info);
        }

        // ✅ DELETE BUTTON FIXED
        btnDelete.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Delete Image")
                    .setMessage("Are you sure you want to delete?")
                    .setPositiveButton("Yes", (d, w) -> {

                        if (file.exists() && file.delete()) {
                            Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(this, "Delete failed", Toast.LENGTH_SHORT).show();
                        }

                    })
                    .setNegativeButton("No", null)
                    .show();
        });
    }

    // ✅ Back button action
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}