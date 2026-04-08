package com.example.q4_photoapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<File> imageFiles = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));

        findViewById(R.id.btnCapture).setOnClickListener(v -> openCamera());
        loadImages();
    }

    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File photoFile = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                "IMG_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".jpg");

        Uri photoURI = FileProvider.getUriForFile(this, "com.example.q4_photoapp.fileprovider", photoFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
        startActivityForResult(intent, 100);
    }

    private void loadImages() {
        imageFiles.clear();
        File dir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if (dir != null && dir.exists()) {
            File[] files = dir.listFiles();
            if (files != null) {
                for (File f : files) if (f.getName().endsWith(".jpg")) imageFiles.add(f);
            }
        }
        recyclerView.setAdapter(new ImageAdapter(this, imageFiles, file -> {
            Intent intent = new Intent(this, ImageDetailActivity.class);
            intent.putExtra("path", file.getAbsolutePath());
            startActivity(intent);
        }));
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadImages(); // Refresh gallery on return
    }
}