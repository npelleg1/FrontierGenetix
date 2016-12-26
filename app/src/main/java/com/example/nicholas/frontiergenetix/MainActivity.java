package com.example.nicholas.frontiergenetix;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final WebView web = (WebView)findViewById(R.id.webView);
        final TextView snpText = (TextView)findViewById(R.id.snpTextView);
        final Context c = MainActivity.this;
        final Activity a = this;

        Intent i = getIntent();
        String snp_text = i.getStringExtra("SNP");
        snpText.setText(snp_text);

        final String snp = snpText.getText().toString();
        new RetrieveFeedTask(c, a, snp).execute(web);
    }
}
