package com.example.mdbook;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;


public class ChooseUploadActivity extends AppCompatActivity {

    Button uploadFromGallery;
    Button openCamera;

    public static final int GET_FROM_GALLERY_REQUEST_CODE = 1;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_upload);

        checkPermission();

        uploadFromGallery = findViewById(R.id.openGallery);
        openCamera = findViewById(R.id.openCamera);

        uploadFromGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                viewGallery();

            }
        });

        openCamera.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                takeAPhoto();

            }
        });
    }

    public void takeAPhoto() {

        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);

        }


    public void viewGallery(){

        Intent photoPickerIntent = new Intent(Intent.ACTION_GET_CONTENT);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, GET_FROM_GALLERY_REQUEST_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            checkPermission();
            if (resultCode == RESULT_OK) {

                Toast toast = Toast.makeText(getApplicationContext(), "Photo OK!", Toast.LENGTH_SHORT);
                toast.show();
                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                String path = saveImage(thumbnail);
                Intent intent = new Intent();
                intent.putExtra("uri", path);
                setResult(RESULT_OK, intent);
                finish();


            } else if (resultCode == RESULT_CANCELED) {

                Toast toast = Toast.makeText(getApplicationContext(), "Process was cancelled", Toast.LENGTH_SHORT);
                toast.show();
                this.finish();

            } else {

                Toast toast = Toast.makeText(getApplicationContext(), "An unknown error occurred", Toast.LENGTH_SHORT);
                toast.show();
                this.finish();

            }
        }
        if (requestCode == GET_FROM_GALLERY_REQUEST_CODE) {
            checkPermission();
            if (resultCode == RESULT_OK) {

                Toast toast = Toast.makeText(getApplicationContext(), "Photo OK!", Toast.LENGTH_SHORT);
                toast.show();
                Uri selectedImage = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                    String path = saveImage(bitmap);
                    Intent intent = new Intent();
                    intent.putExtra("uri", path);
                    setResult(RESULT_OK, intent);
                    finish();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }

    }



    public void checkPermission(){

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        }

        else if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, GET_FROM_GALLERY_REQUEST_CODE);
        }

        else{
            return;

        }

        }




    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults){

        switch (requestCode){
            case CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE: {
                for (int grantResult :
                        grantResults) {
                    if (grantResults.length > 0 && grantResult == PackageManager.PERMISSION_GRANTED) {
                        return;
                    } else {
                        this.finish();
                    }
                    //return;
                }
            }
            case GET_FROM_GALLERY_REQUEST_CODE: {
                for (int grantResult : grantResults) {
                    if (grantResults.length > 0 && grantResult == PackageManager.PERMISSION_GRANTED) {
                        return;
                    } else {
                        this.finish();
                    }
                    //return;
                }
            }

        }
    }

    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + "/tmp");
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();

            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }

}
