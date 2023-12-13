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
            HttpURLConnection connWater = null;
            HttpURLConnection connSound = null;

            int statusWater;
            int statusSound;
            try {
                URL dataUrlWater = new URL("https://security-system-api-260273149601.herokuapp.com/water");
                URL dataUrlSound = new URL("https://security-system-api-260273149601.herokuapp.com/sound");
                connWater = (HttpURLConnection) dataUrlWater.openConnection();
                connSound = (HttpURLConnection) dataUrlSound.openConnection();
                connWater.connect();
                connSound.connect();
                statusWater = connWater.getResponseCode();
                statusSound = connWater.getResponseCode();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            StringBuilder sbWater = null;
            StringBuilder sbSound = null;
            if (statusWater == 200 && statusSound == 200) {
                InputStream isWater = null;
                InputStream isSound = null;
                try {
                    isWater = connWater.getInputStream();
                    isSound = connSound.getInputStream();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                BufferedReader readerWater = new BufferedReader(new
                        InputStreamReader(isWater));
                BufferedReader readerSound = new BufferedReader(new
                        InputStreamReader(isSound));
                String responseStringWater;
                String responseStringSound;
                sbWater = new StringBuilder();
                sbSound = new StringBuilder();
                while (true) {
                    try {
                        if ((responseStringWater = readerWater.readLine()) == null)
                            break;
                        if ((responseStringSound = readerSound.readLine()) == null)
                            break;
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    sbWater = sbWater.append(responseStringWater);
                    sbSound = sbSound.append(responseStringSound);
                }
            }
            assert sbWater != null;
            assert sbSound != null;
            String valuesWater = sbWater.toString();
            String valuesSound = sbSound.toString();
            Log.d("VALUESWATER", valuesWater);
            Log.d("VALUESSOUND", valuesSound);
            return (0L);
        }
    }
}
