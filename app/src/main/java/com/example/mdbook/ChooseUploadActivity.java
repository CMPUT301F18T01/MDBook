package com.example.mdbook;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;

public class ChooseUploadActivity extends AppCompatActivity {

    Button uploadFromGallery;
    Button openCamera;
    Uri imageFileUri;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_upload);

        uploadFromGallery = findViewById(R.id.openGallery);
        openCamera = findViewById(R.id.openCamera);

        openCamera.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                checkPermission();

            }
        });
    }

    public void takeAPhoto() {

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        String folder = Environment.getExternalStorageDirectory().getAbsolutePath() + "/tmp";
        File folderF = new File(folder);

        if (!folderF.exists()) {
            folderF.mkdir();
        }

        String imageFilePath = folder + "/" + String.valueOf(System.currentTimeMillis()) + ".jpg";
        File imageFile = new File(imageFilePath);
        imageFileUri = Uri.fromFile(imageFile);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if(intent.resolveActivity(getPackageManager())!= null) {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUri);

            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {

            if (resultCode == RESULT_OK) {

                Toast toast = Toast.makeText(getApplicationContext(),"Photo OK!", Toast.LENGTH_SHORT);
                toast.show();
                String uri = imageFileUri.getPath();
                Intent intent = new Intent();
                intent.putExtra("uri", uri);
                setResult(RESULT_OK, intent);
                finish();

            } else if (resultCode == RESULT_CANCELED) {

                Toast toast = Toast.makeText(getApplicationContext(),"Process was cancelled", Toast.LENGTH_SHORT);
                toast.show();
                this.finish();

            } else {

                Toast toast = Toast.makeText(getApplicationContext(),"An unknown error occurred", Toast.LENGTH_SHORT);
                toast.show();
                this.finish();

            }
        }
    }

    public void checkPermission(){

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        }

        else{
            takeAPhoto();
        }
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults){
        switch (requestCode){
            case CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE: {
                for (int grantResult:
                     grantResults){
                    if (grantResults.length > 0 && grantResult == PackageManager.PERMISSION_GRANTED) {
                        takeAPhoto();
                    } else {
                        this.finish();
                    }
                    return;
                }
            }
        }
    }


}
