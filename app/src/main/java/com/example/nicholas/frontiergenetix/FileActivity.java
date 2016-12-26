package com.example.nicholas.frontiergenetix;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class FileActivity extends AppCompatActivity {

    ListView lv;
    TextView tv, stv;
    ArrayAdapter<String> files;
    String path;
    Button back, choose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file);

        lv = (ListView)findViewById(R.id.listView);

        Typeface myFont = Typeface.createFromAsset(getAssets(), "fonts/Rajdhani-Medium.ttf");

        tv = (TextView)findViewById(R.id.fileTextView);
        tv.setTypeface(myFont);

        stv = (TextView)findViewById(R.id.textView3);
        stv.setTypeface(myFont);

        back = (Button)findViewById(R.id.backButton);
        back.setTypeface(myFont);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!path.equals("/")) {
                    path = path.substring(0, path.lastIndexOf("/"));
                    listDirectory();
                }
            }
        });

        choose = (Button)findViewById(R.id.chooseFileButton);
        choose.setTypeface(myFont);
        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (path.substring(path.length()-4).equals(".txt")) {
                    Intent i = new Intent(FileActivity.this, FileParseActivity.class);
                    i.putExtra("PATH", path);
                    Log.d("PATH TO FPA:", path);
                    startActivity(i);
                }
                else{
                    Toast.makeText(getApplicationContext(), "File must be a .txt file, pick another!", Toast.LENGTH_LONG).show();
                }
            }
        });

        path = Environment.getExternalStorageDirectory().toString();
        tv.setText(path);

        listDirectory();
    }
    public void listDirectory(){
        lv.setAdapter(null);
        tv.setText(path);
        Log.d("Files", "Path: " + path);
        File f = new File(path);
        File file[] = f.listFiles();
        if(file != null) {
            Log.d("Files", "Size: " + file.length);
            files = new ArrayAdapter<String>(getApplicationContext(),R.layout.listview_item);
            for (int i = 0; i < file.length; i++) {
                if (file[i].isFile()) {
                    Log.d("Files", "FileName:" + file[i].getName());
                } else if (file[i].isDirectory()) {
                    Log.d("Directory", "dir: " + file[i].getName());
                }
                files.add(file[i].getName());
            }
            lv.setAdapter(files);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (path.equals("/")) {
                        path = path + parent.getItemAtPosition(position).toString();
                    } else {
                        path = path + "/" + parent.getItemAtPosition(position).toString();
                    }
                    listDirectory();
                }
            });
        }
    }
}