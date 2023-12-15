package com.example.securitysystemapp;
import java.io.IOException;
import androidx.appcompat.app.AppCompatActivity;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;
import android.os.AsyncTask;
import android.util.Log;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {
    int watValue;
    int coValue;
    int burValue;
    int reedValue;
    int soundValue;
    Handler handler = new Handler();
    Runnable runnable;
    int delay = 3000;
    public MainActivity() throws JSONException {
    };

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
        } else {
            Toast.makeText(MainActivity.this.getApplicationContext(),
                    "No connection found. Connect your device and try again later.", Toast.LENGTH_LONG).show();
        }
    }
    @Override
    protected void onResume() {
        handler.postDelayed(runnable = new Runnable() {
            public void run() {
                handler.postDelayed(runnable, delay);
                LoadValues task = new LoadValues();
                task.execute();
            }
        }, delay);
        super.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable); //stop handler when activity not visible super.onPause();
    }

    private boolean isOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    private class LoadValues extends AsyncTask<String, String, Long> {
        @Override
        protected void onPostExecute(Long result) {

            TextView status = findViewById(R.id.status);

            if (watValue > 400 || coValue > 1000 || burValue == 1 || reedValue == 1 || soundValue == 1) {
                status.setText("Status: Alarm ❕");

            } else if (watValue < 400 && coValue < 1000 && burValue == 0 && reedValue == 0 && soundValue == 0) {
                status.setText("Status: OK 🙂");
            } else {
                status.setText("Status: Unknown ❔");
            }

            TextView watIcon = findViewById(R.id.watIcon);
            TextView watIconfalse = findViewById(R.id.watIconfalse);
            TextView watIcontrue = findViewById(R.id.watIcontrue);

            if (watValue > 400) {
                watIcon.setVisibility(View.GONE);
                watIconfalse.setVisibility(View.VISIBLE);
                watIcontrue.setVisibility(View.GONE);
            } else if (watValue < 400) {
                watIcon.setVisibility(View.GONE);
                watIcontrue.setVisibility(View.VISIBLE);
                watIconfalse.setVisibility(View.GONE);
            } else {
                watIcon.setVisibility(View.VISIBLE);
                watIcontrue.setVisibility(View.GONE);
                watIconfalse.setVisibility(View.GONE);
            }

            TextView coIcon = findViewById(R.id.coIcon);
            TextView coIconfalse = findViewById(R.id.coIconfalse);
            TextView coIcontrue = findViewById(R.id.coIcontrue);

            if (coValue > 1000) {
                coIcon.setVisibility(View.GONE);
                coIcontrue.setVisibility(View.GONE);
                coIconfalse.setVisibility(View.VISIBLE);
            }

            else if (coValue < 400) {
                coIcon.setVisibility(View.GONE);
                coIconfalse.setVisibility(View.GONE);
                coIcontrue.setVisibility(View.VISIBLE);

            } else {
                coIcon.setVisibility(View.VISIBLE);
                coIconfalse.setVisibility(View.GONE);
                coIcontrue.setVisibility(View.GONE);
            }

            TextView burIcon = findViewById(R.id.burIcon);
            TextView burIconfalse = findViewById(R.id.burIconfalse);
            TextView burIcontrue = findViewById(R.id.burIcontrue);

            if (burValue == 1 || reedValue == 1) {
                burIcon.setVisibility(View.GONE);
                burIconfalse.setVisibility(View.VISIBLE);
                burIcontrue.setVisibility(View.GONE);

            } else if (burValue == 0 && reedValue == 0) {
                burIcon.setVisibility(View.GONE);
                burIconfalse.setVisibility(View.GONE);
                burIcontrue.setVisibility(View.VISIBLE);

            } else {
                burIcon.setVisibility(View.VISIBLE);
                burIconfalse.setVisibility(View.GONE);
                burIcontrue.setVisibility(View.GONE);
            }

            TextView soundIcon = findViewById(R.id.soundIcon);
            TextView soundIconfalse = findViewById(R.id.soundIconfalse);
            TextView soundIcontrue = findViewById(R.id.soundIcontrue);

            if (soundValue == 1) {
                soundIcon.setVisibility(View.GONE);
                soundIconfalse.setVisibility(View.VISIBLE);
                soundIcontrue.setVisibility(View.GONE);

            } else if (soundValue == 0) {
                soundIcon.setVisibility(View.GONE);
                soundIconfalse.setVisibility(View.GONE);
                soundIcontrue.setVisibility(View.VISIBLE);
            } else {
                soundIcon.setVisibility(View.VISIBLE);
                soundIconfalse.setVisibility(View.GONE);
                soundIcontrue.setVisibility(View.GONE);
            }
        }
        @Override
        protected Long doInBackground(String... params) {

            HttpURLConnection connWater = null;
            HttpURLConnection connSound = null;
            HttpURLConnection connGas = null;
            HttpURLConnection connMotion = null;
            HttpURLConnection connReedSwitch = null;

            int statusWater;
            int statusSound;
            int statusGas;
            int statusMotion;
            int statusReedSwitch;

            try {

                URL dataUrlWater = new URL("https://security-system-api-260273149601.herokuapp.com/water");
                URL dataUrlSound = new URL("https://security-system-api-260273149601.herokuapp.com/sound");
                URL dataUrlGas = new URL("https://security-system-api-260273149601.herokuapp.com/gas");
                URL dataUrlMotion = new URL("https://security-system-api-260273149601.herokuapp.com/motion");
                URL dataUrlReedSwitch = new URL("https://security-system-api-260273149601.herokuapp.com/reedswitch");

                connWater = (HttpURLConnection) dataUrlWater.openConnection();
                connSound = (HttpURLConnection) dataUrlSound.openConnection();
                connGas = (HttpURLConnection) dataUrlGas.openConnection();
                connMotion = (HttpURLConnection) dataUrlMotion.openConnection();
                connReedSwitch = (HttpURLConnection) dataUrlReedSwitch.openConnection();

                connWater.connect();
                connSound.connect();
                connGas.connect();
                connMotion.connect();
                connReedSwitch.connect();

                statusWater = connWater.getResponseCode();
                statusSound = connSound.getResponseCode();
                statusGas = connGas.getResponseCode();
                statusMotion = connMotion.getResponseCode();
                statusReedSwitch = connReedSwitch.getResponseCode();

            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            StringBuilder sbWater = null;
            StringBuilder sbSound = null;
            StringBuilder sbGas = null;
            StringBuilder sbMotion = null;
            StringBuilder sbReedSwitch = null;

            if (statusWater == 200 && statusSound == 200 && statusGas == 200 && statusMotion == 200 && statusReedSwitch == 200) {

                InputStream isWater = null;
                InputStream isSound = null;
                InputStream isGas = null;
                InputStream isMotion = null;
                InputStream isReedSwitch = null;

                try {

                    isWater = connWater.getInputStream();
                    isSound = connSound.getInputStream();
                    isGas = connGas.getInputStream();
                    isMotion = connMotion.getInputStream();
                    isReedSwitch = connReedSwitch.getInputStream();

                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

                BufferedReader readerWater = new BufferedReader(new
                        InputStreamReader(isWater));

                BufferedReader readerSound = new BufferedReader(new
                        InputStreamReader(isSound));

                BufferedReader readerGas = new BufferedReader(new
                        InputStreamReader(isGas));

                BufferedReader readerMotion = new BufferedReader(new
                        InputStreamReader(isMotion));

                BufferedReader readerReedSwitch = new BufferedReader(new
                        InputStreamReader(isReedSwitch));

                String responseStringWater;
                String responseStringSound;
                String responseStringGas;
                String responseStringMotion;
                String responseStringReedSwitch;

                sbWater = new StringBuilder();
                sbSound = new StringBuilder();
                sbGas = new StringBuilder();
                sbMotion = new StringBuilder();
                sbReedSwitch = new StringBuilder();

                while (true) {

                    try {

                        if ((responseStringWater = readerWater.readLine()) == null)
                            break;

                        if ((responseStringSound = readerSound.readLine()) == null)
                            break;

                        if ((responseStringGas = readerGas.readLine()) == null)
                            break;

                        if ((responseStringMotion = readerMotion.readLine()) == null)
                            break;

                        if ((responseStringReedSwitch = readerReedSwitch.readLine()) == null)
                            break;

                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }

                    sbWater = sbWater.append(responseStringWater);
                    sbSound = sbSound.append(responseStringSound);
                    sbGas = sbGas.append(responseStringGas);
                    sbMotion = sbMotion.append(responseStringMotion);
                    sbReedSwitch = sbReedSwitch.append(responseStringReedSwitch);

                }
            }

            assert sbWater != null;
            assert sbSound != null;
            assert sbGas != null;
            assert sbMotion != null;
            assert sbReedSwitch != null;

            String valuesWater = sbWater.toString();
            String valuesSound = sbSound.toString();
            String valuesGas = sbGas.toString();
            String valuesMotion = sbMotion.toString();
            String valuesReedSwitch = sbReedSwitch.toString();

            Log.d("VALUESWATER", valuesWater);
            Log.d("VALUESSOUND", valuesSound);
            Log.d("VALUESGAS", valuesGas);
            Log.d("VALUESMOTION", valuesMotion);
            Log.d("VALUESREEDSWITCH", valuesReedSwitch);

            try {
                JSONArray jsonArray = new JSONArray(valuesWater);

                if (jsonArray.length() > 0) {
                    JSONObject lastObject = jsonArray.getJSONObject(jsonArray.length() - 1);
                    int lastId = lastObject.getInt("id");
                    watValue = lastObject.getInt("value");

                    Log.d("PUREWATER", "Last ID: " + lastId + ", Value: " + watValue);
                }

            } catch (JSONException e) {
                e.printStackTrace();
                Log.e("PUREWATER", "Error processing JSON");
            }

            try {
                JSONArray jsonArray = new JSONArray(valuesGas);

                if (jsonArray.length() > 0) {
                    JSONObject lastObject = jsonArray.getJSONObject(jsonArray.length() - 1);
                    int lastId = lastObject.getInt("id");
                    coValue = lastObject.getInt("value");

                    Log.d("PUREGAS", "Last ID: " + lastId + ", Value: " + coValue);
                }

            } catch (JSONException e) {
                e.printStackTrace();
                Log.e("PUREGAS", "Error processing JSON");
            }

            try {
                JSONArray jsonArray = new JSONArray(valuesMotion);

                if (jsonArray.length() > 0) {
                    JSONObject lastObject = jsonArray.getJSONObject(jsonArray.length() - 1);
                    int lastId = lastObject.getInt("id");
                    burValue = lastObject.getInt("value");

                    Log.d("PUREMOTION", "Last ID: " + lastId + ", Value: " + burValue);
                }

            } catch (JSONException e) {
                e.printStackTrace();
                Log.e("PUREMOTION", "Error processing JSON");
            }

            try {
                JSONArray jsonArray = new JSONArray(valuesReedSwitch);

                if (jsonArray.length() > 0) {
                    JSONObject lastObject = jsonArray.getJSONObject(jsonArray.length() - 1);
                    int lastId = lastObject.getInt("id");
                    reedValue = lastObject.getInt("value");

                    Log.d("PUREREED", "Last ID: " + lastId + ", Value: " + reedValue);
                }

            } catch (JSONException e) {
                e.printStackTrace();
                Log.e("PUREREED", "Error processing JSON");
            }

            try {
                JSONArray jsonArray = new JSONArray(valuesSound);

                if (jsonArray.length() > 0) {
                    JSONObject lastObject = jsonArray.getJSONObject(jsonArray.length() - 1);
                    int lastId = lastObject.getInt("id");
                    soundValue = lastObject.getInt("value");

                    Log.d("PURESOUND", "Last ID: " + lastId + ", Value: " + soundValue);
                }

            } catch (JSONException e) {
                e.printStackTrace();
                Log.e("PURESOUND", "Error processing JSON");
            }

            return (0L);
        }
    }
}
