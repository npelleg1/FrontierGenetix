package com.example.nicholas.frontiergenetix;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Nicholas on 6/11/2016.
 */
public class SimpleBuildListTask extends AsyncTask<Object, Void, String> {
    Context mContext;
    ProgressDialog spinner;
    String email;
    static final String API_URL_START = "http://bots.snpedia.com/api.php?action=query&titles=";
    static final String API_URL_END = "&prop=revisions&rvprop=content&format=json";
    //   static final String API_URL_DBSNP = "http://www.ncbi.nlm.nih.gov/SNP/snp_ref.cgi?searchType=adhoc_search&type=rs&rs=";
    private Activity parent;

    public AsyncResponse delegate = null;

    public BuildListTask(Context mainContext, Activity _parent, String snp, AsyncResponse delegate){
        super();
        mContext = mainContext;
        spinner = new ProgressDialog(mainContext);
        this.spinner.setMessage("PROCESSING YOUR GENETIC REPORT...");
        this.spinner.show();
        this.parent = _parent;
        this.email = snp;
        this.delegate = delegate;
    }

    protected String doInBackground(Object... params) {
        // Do some validation here

        try {
            URL url = new URL(API_URL_START+email+API_URL_END);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();
                return stringBuilder.toString();
            }
            finally{
                urlConnection.disconnect();
            }
        }
        catch(Exception e) {
            Log.e("ERROR", e.getMessage(), e);
            return null;
        }
    }

    protected void onPostExecute(String response) {
        String orientation = null;
        if(response == null) {
            response = "THERE WAS AN ERROR";
            Toast.makeText(mContext, response, Toast.LENGTH_LONG).show();
        }
        else{
            try {
                JSONObject object = (JSONObject) new JSONTokener(response).nextValue();
                orientation = object.getString("query");
                if(orientation.contains("Gene")){
                    int position_start = orientation.indexOf("Gene");
                    int position_end = orientation.indexOf("|", position_start);
                    Log.i("INDEXES", position_start + " " + position_end);
                    orientation = orientation.substring(position_start, position_end);
                    if (orientation.contains("Gene_s")) {
                        orientation = orientation.substring(7, orientation.length() - 3);
                    }
                    else{
                        orientation = orientation.substring(5, orientation.length() - 2);
                    }
                }
                else{
                    orientation = "SNP does not have gene name on snpedia!";
                }
                Log.i("STRING_VALS:", "QUERY: "+orientation);
            } catch (JSONException e) {
                Log.e("ERROR", e.getMessage());
            }
        }
        Log.i("INFO", response);
        if (spinner.isShowing()) {
            spinner.dismiss();
        }
        response=orientation;
        delegate.processFinish(response);
    }
}
