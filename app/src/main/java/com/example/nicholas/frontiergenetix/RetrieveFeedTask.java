package com.example.nicholas.frontiergenetix;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Nicholas on 5/8/2016.
 */
public class RetrieveFeedTask extends AsyncTask<Object, Void, Void> {
    private Exception exception;
    Context mContext;
    ProgressDialog spinner;
    String email;
    WebView web;
    static final String API_URL = "http://www.snpedia.com/index.php/";
    static final String API_URL_START = "http://bots.snpedia.com/api.php?action=query&titles=";
    static final String API_URL_END = "&prop=revisions&rvprop=content&format=json";
 //   static final String API_URL_DBSNP = "http://www.ncbi.nlm.nih.gov/SNP/snp_ref.cgi?searchType=adhoc_search&type=rs&rs=";
    private Activity parent;

    public RetrieveFeedTask(Context mainContext, Activity _parent, String snp){
        super();
        mContext = mainContext;
        spinner = new ProgressDialog(mainContext);
        this.spinner.setMessage("LOADING");
        this.spinner.show();
        this.parent = _parent;
        this.email = snp;
    }

    protected Void doInBackground(Object... params) {
        // Do some validation here
            try {
                web = (WebView)params[0];
                URL url = new URL(API_URL_START+email+API_URL_END);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                parent.runOnUiThread(new Runnable() { // this code ensures the Toast appears on the screen (i.e. the UiThread)
                    public void run() {
                        web.loadUrl(API_URL + email);
                        web.setWebViewClient(new WebViewClient() {
                            public boolean shouldOverrideUrlLoading(WebView viewx, String urlx) {
                                viewx.loadUrl(urlx);
                                return false;
                            }
                        });
                    }
                });
                urlConnection.disconnect();
            }
            catch(Exception e) {
                Log.e("ERROR", e.getMessage(), e);
            }
        return null;
    }

    protected void onPostExecute(Void response) {
        if (spinner.isShowing()) {
            spinner.dismiss();
        }
    }
}