package com.example.q4_photoapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Toast;

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
    private List<Uri> imageUris = new ArrayList<>();
    private File folder;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));

        folder = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        findViewById(R.id.btnPickFolder).setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
            startActivity(intent);
        });

        findViewById(R.id.btnCapture).setOnClickListener(v -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, 1);
                } else {
                    openCamera();
                }
            } else {
                openCamera();
            }
        });

        loadImages();
    }

    private void openCamera() {
        try {
            File photoFile = new File(folder,
                    "IMG_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".jpg");

            imageUri = FileProvider.getUriForFile(
                    this,
                    "com.example.q4_photoapp.fileprovider",
                    photoFile
            );

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivity(intent);

        } catch (Exception e) {
            Toast.makeText(this, "Camera error", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadImages() {
        imageUris.clear();

        if (folder != null && folder.exists()) {
            File[] files = folder.listFiles();

            if (files != null) {
                for (File f : files) {
                    if (f.getName().endsWith(".jpg")) {
                        imageUris.add(Uri.fromFile(f));
                    }
                }
            }
        }

        recyclerView.setAdapter(new ImageAdapter(this, imageUris, uri -> {
            Intent intent = new Intent(this, ImageDetailActivity.class);
            intent.putExtra("uri", uri.toString());
            startActivity(intent);
        }));
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadImages();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1 && grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            openCamera();
        }
    }
}