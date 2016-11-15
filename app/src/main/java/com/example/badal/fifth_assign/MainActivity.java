package com.example.badal.fifth_assign;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    Button downbutton;
    TextView resultext;
    private static final String TAG="MainActivity";
    String nresult;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        downbutton=(Button) findViewById(R.id.download_button);
        resultext=(TextView) findViewById(R.id.data);
        resultext.setSelected(true);

        downbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConnectivityManager connMgr = (ConnectivityManager)
                        getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                    new BackgroundTask().execute();
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "No Network Connection Available", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

    }

    public class BackgroundTask extends AsyncTask<Void,String,Void>{
        String result;
        ProgressDialog progressDialog;

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Document document= Jsoup.connect("https://www.iiitd.ac.in/about").get();
                result=document.text();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            progressDialog.hide();
            Toast toast = Toast.makeText(getApplicationContext(), "Download Complete", Toast.LENGTH_SHORT);
            toast.show();
            nresult=result.substring(0,22);
            resultext.setText(nresult);
            System.out.println(result);
            super.onPostExecute(aVoid);
        }

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setTitle("Download in Progress");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setIndeterminate(true);
            progressDialog.setProgress(0);
            progressDialog.show();
            super.onPreExecute();
        }


    }
}
