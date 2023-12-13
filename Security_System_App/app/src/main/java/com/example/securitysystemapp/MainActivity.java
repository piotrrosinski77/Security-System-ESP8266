package com.example.securitysystemapp;
import static kotlin.io.ConsoleKt.readLine;

import java.io.IOException;
import androidx.appcompat.app.AppCompatActivity;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;
import android.app.FragmentTransaction;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import org.json.JSONException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //Fullscreen mode (without status bar)


        if (isOnline()) {
            Toast.makeText(MainActivity.this.getApplicationContext(),
                    "Connection found.", Toast.LENGTH_SHORT).show();
            LoadValues task = new LoadValues();
            task.execute();
        }
            else {
            Toast.makeText(MainActivity.this.getApplicationContext(),
                    "No connection found. Connect your device and try again later.", Toast.LENGTH_LONG).show();
        }
    }
    private boolean isOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    private class LoadValues extends AsyncTask<String , String , Long > {

        @Override
        protected Long doInBackground(String... params) {
            HttpURLConnection connection = null;
            int status;
            try {
                URL dataUrl = new URL("https://security-system-api-260273149601.herokuapp.com/water");
                connection = (HttpURLConnection) dataUrl.openConnection();
                connection.connect();
                status = connection.getResponseCode();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            StringBuilder sb = null;
            if (status == 200) {
                InputStream is = null;
                try {
                    is = connection.getInputStream();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                BufferedReader reader = new BufferedReader(new
                        InputStreamReader(is));
                String responseString;
                sb = new StringBuilder();
                while (true) {
                    try {
                        if ((responseString = reader.readLine()) == null) break;
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    sb = sb.append(responseString);
                }
            }
            assert sb != null;
            String values = sb.toString();
            Log.d("VALUES", values);
            return (0L);
        }
    }
}
