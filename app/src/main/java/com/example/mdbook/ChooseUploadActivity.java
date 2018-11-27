package com.example.mdbook;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
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
import android.widget.ImageView;

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

                StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                StrictMode.setVmPolicy(builder.build());
                takeAPhoto();

            }
        });
    }

    public void takeAPhoto() {

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
            //TextView tv = (TextView) findViewById(R.id.status);
            if (resultCode == RESULT_OK) {
                //tv.setText("Photo OK!");
                ImageView image = findViewById(R.id.body_front);
                image.setImageDrawable(Drawable.createFromPath(imageFileUri.getPath()));
                this.finish();
            } else if (resultCode == RESULT_CANCELED) {
                this.finish();
                //tv.setText("Photo canceled");
            } else {
                this.finish();
                //tv.setText("Not sure what happened!" + resultCode);
            }
        }
    }
}
