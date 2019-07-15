package com.androidnetworking.lab2;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;
import java.util.Scanner;

public class HomeActivity extends AppCompatActivity {
    private TextView tvName;
    private TextView tvList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        tvName = findViewById(R.id.tvName);
        tvList = findViewById(R.id.tvList);

        MyAsyncTask myAsyncTask = new MyAsyncTask();
        myAsyncTask.execute("http://dotplays.com/android/bai1.php?food=today");

        Intent intent = getIntent();
        String name =intent.getStringExtra("name");
        tvName.setText(name);
    }

    public void onExit(View view) {
        finish();
    }

    public class MyAsyncTask extends AsyncTask<String, Long, String> {
        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0]);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                InputStream inputStream = httpURLConnection.getInputStream();

                Scanner scanner = new Scanner(inputStream);

                String data = "";
                while (scanner.hasNext()) {
                    data = data + scanner.nextLine();
                }

                return data;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.e("data", s );
            tvList.setText(s.replace("\\n", Objects.requireNonNull(System.getProperty("line.separator"))));
        }
    }
}
