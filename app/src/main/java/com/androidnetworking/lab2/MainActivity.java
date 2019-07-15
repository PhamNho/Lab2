package com.androidnetworking.lab2;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;
import java.util.Scanner;


public class MainActivity extends AppCompatActivity {
    private EditText edtUserName;
    private EditText edtPassword;
    private EditText edtName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtUserName = findViewById(R.id.edtUserName);
        edtPassword = findViewById(R.id.edtPassword);
        edtName = findViewById(R.id.edtName);

    }

    private boolean validateUserName() {
        String userName = edtUserName.getText().toString().trim();
        if (userName.isEmpty()) {
            edtUserName.setError("UserName trống");
            return false;
        } else if (userName.length() > 6) {
            edtUserName.setError("UserName không quá 6 ký tự");
            return false;
        } else {
            edtUserName.setError(null);
            return true;
        }
    }

    private boolean validatePassword() {
        String password = edtPassword.getText().toString().trim();
        if (password.isEmpty()) {
            edtPassword.setError("Password trống");
            return false;
        } else if (password.length() > 6) {
            edtPassword.setError("Password không quá 6 ký tự");
            return false;
        } else {
            edtPassword.setError(null);
            return true;
        }
    }

    private boolean validateName() {
        String name = edtName.getText().toString().trim();
        if (name.isEmpty()) {
            edtName.setError("Name trống");
            return false;
        } else if (name.length() > 20) {
            edtName.setError("Name không quá 20 ký tự");
            return false;
        } else {
            edtName.setError(null);
            return true;
        }
    }

    public void onLogin(View view) {
        if (!validateUserName() | !validatePassword() | !validateName()) {
            return;
        }
        if (edtUserName.getText().toString().trim().equals("admin") && edtPassword.getText().toString().trim().equals("123456")) {
            Intent intent = new Intent(this, HomeActivity.class);
            intent.putExtra("name", edtName.getText().toString());
            startActivity(intent);
            Toast.makeText(this, "Xin chào " + edtName.getText(), Toast.LENGTH_SHORT).show();
            finish();
        } else {
            if (!edtUserName.getText().toString().trim().equals("admin")) {
                edtUserName.setError("Tài khoản không tồn tại");
            }
            if (!edtPassword.getText().toString().trim().equals("123456")) {
                edtPassword.setError("Sai mật khẩu");
            }
            MyAsyncTask myAsyncTask = new MyAsyncTask();
            myAsyncTask.execute("http://dotplays.com/android/login.php");
        }
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
            Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
        }
    }
}
