package com.example.securitysystemapp;
import static kotlin.io.ConsoleKt.readLine;
import java.io.IOException;
import androidx.appcompat.app.AppCompatActivity;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
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
import java.util.ArrayList;import android.app.FragmentTransaction;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import org.json.JSONException;
import java.net.MalformedURLException;
import java.io.IOException;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView status = findViewById(R.id.status);
        TextView burIcon = findViewById(R.id.burIcon);
        TextView coIcon = findViewById(R.id.coIcon);
        TextView watIcon = findViewById(R.id.watIcon);
        TextView soundIcon = findViewById(R.id.soundIcon);

        //status.setText("Alarm");
        //status.setText("OK");

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

            return (0L);
        }
    }
}
