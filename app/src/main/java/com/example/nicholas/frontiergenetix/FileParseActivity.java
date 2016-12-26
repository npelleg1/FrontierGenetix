package com.example.nicholas.frontiergenetix;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FileParseActivity extends AppCompatActivity {

    String path;
    BufferedReader reader;
    ArrayAdapter<String> snp_adapter;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_parse);

        lv = (ListView)findViewById(R.id.listView2);
        Intent i = getIntent();
        path = i.getStringExtra("PATH");
        Log.d("PATH FROM:", path);

        snp_adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.listview_item);
        Log.d("Adapter", "Made success");
        try {
            File genome_report = new File (path);
            Log.i("PATH FOR READER:", genome_report.getAbsolutePath());
            reader = new BufferedReader(new FileReader(genome_report));
            String line;
            String l[];
            while((line=reader.readLine())!=null){
                if(!line.substring(0,1).equals("#")){
                    l = line.split("\\t");
                    final String snp = l[0];
              /*      try {
                        BuildListTask asyncTask = new BuildListTask(FileParseActivity.this, this, l[0], new AsyncResponse(){
                            @Override
                            public void processFinish(String output){
                                snp_adapter.add(snp + " (" + output+")");
                            }
                        });
                        asyncTask.execute();
                    }catch(Exception e){
                        Log.e("ERROR!", e.getMessage());
                    } */
                    snp_adapter.add(snp+ " ");
                }
            }
            reader.close();
        }
        catch (FileNotFoundException fnfe) {
            Log.e("ERROR", "FILE NOT FOUND");
        } catch (IOException ioe) {
            Log.e("ERROR", "IO EXCEPTION");
        }
        lv.setAdapter(snp_adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(FileParseActivity.this, MainActivity.class);
                String send = parent.getItemAtPosition(position).toString().substring(0, parent.getItemAtPosition(position).toString().indexOf(" "));
                i.putExtra("SNP", send);
                startActivity(i);
            }
        });
    }
}