package com.example.mdbook;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;


public class ShowBodyLocationsActivity extends AppCompatActivity {

    private float y_up;
    private float y_down;
    private float x_left;
    private float x_right;

    private ArrayList<String> bodypartlist;
    private ArrayList<String> labellist;
    private Record record;
    private BodyLocation bodylocation;
    private UserManager userManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_body_locations);
    }


    //your canvas as per your requirement
    public class CanvasWithText extends View {

        public Paint pBackground,pBackground1, pText;

        private String str;

        public CanvasWithText(Context context, String str) {
            super(context);
            this.str = str;
            pBackground = new Paint();
            pBackground1 = new Paint();
            pText = new Paint();
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            pBackground.setColor(Color.WHITE);
            pBackground1.setColor(Color.BLACK);
            canvas.drawRect(0, 0, 512, 512, pBackground);
            canvas.drawRect(100, 100, 120, 120, pBackground1);
            pText.setColor(Color.BLACK);
            pText.setTextSize(20);
            canvas.drawText(str, 100, 100, pText);
        }
    }
}

