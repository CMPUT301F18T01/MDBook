package com.example.mdbook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.WriterException;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import androidmads.library.qrgenearator.QRGSaver;

public class GenerateQRActivity extends AppCompatActivity {

    private ImageView qrImage;
    private Button generate;
    private String inputValue;
    private Bitmap bitmap;
    private QRGEncoder qrgEncoder;
    private TextView tag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_qr);

        inputValue = UserController.getController().getUser().getUserID();

        qrImage =  findViewById(R.id.qrImageView);
        generate = findViewById(R.id.generateButton);
        tag = findViewById(R.id.tag);

        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (inputValue.length() > 0) {
                    WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
                    Display display = manager.getDefaultDisplay();
                    Point point = new Point();
                    display.getSize(point);
                    int width = point.x;
                    int height = point.y;
                    int smallerDimension = width < height ? width : height;
                    smallerDimension = smallerDimension * 3 / 4;

                    qrgEncoder = new QRGEncoder(
                            inputValue, null,
                            QRGContents.Type.TEXT,
                            smallerDimension);
                    try {
                        bitmap = qrgEncoder.encodeAsBitmap();
                        qrImage.setImageBitmap(bitmap);
                        Toast.makeText(GenerateQRActivity.this, "Generating...", Toast.LENGTH_LONG).show();
                        tag.setText("QR code for user: rajkapadia" + inputValue);
                    } catch (WriterException e) {
                        Toast.makeText(GenerateQRActivity.this, "Cannot generate", Toast.LENGTH_LONG).show();
                    }
                } else {
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}
