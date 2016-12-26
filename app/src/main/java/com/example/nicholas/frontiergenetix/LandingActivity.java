package com.example.nicholas.frontiergenetix;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class LandingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        ImageView img = (ImageView)findViewById(R.id.imageView);
        img.setImageResource(R.drawable.businesscardback_page_001);
        ImageView img2 = (ImageView)findViewById(R.id.imageView2);
        img2.setImageResource(R.drawable.businesscardfront_page_001);

        Button startButton = (Button)findViewById(R.id.button);
        Typeface myFont = Typeface.createFromAsset(getAssets(), "fonts/Rajdhani-Medium.ttf");
        startButton.setTypeface(myFont);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LandingActivity.this, FileActivity.class);
                startActivity(i);
            }
        });
    }
}
