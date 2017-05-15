package com.example.escosta.moviesservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.escosta.moviesservice.model.DataItem;
import com.example.escosta.moviesservice.services.MyService;
import com.example.escosta.moviesservice.utils.NetworkHelper;

public class MainActivity extends AppCompatActivity {

    private static String JSON_URL = "http://www.omdbapi.com/?t=";

    private boolean networkOk;

    TextView output;

    EditText search;

    String s;

    WebView mWebView;

    DataItem dataItems = new DataItem();

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            dataItems = (DataItem) intent
                    .getParcelableExtra(MyService.MY_SERVICE_PAYLOAD);
            if (dataItems.getTitle() != null) {
                output.append("\n"+ dataItems.getTitle() + "\n");
                output.append("Year: " + dataItems.getYear() + "   Runtime: " + dataItems.getRuntime() +"\n");
                output.append(dataItems.getPlot() + "\n");
                output.append("Actors: ".toUpperCase() +dataItems.getActors()+ "\n");
                output.append("Genre:  ".toUpperCase() +dataItems.getGenre()+ "\n");
                output.append("Awards: ".toUpperCase()  +dataItems.getAwards()+ "\n");
            } else {
                output.append("Not found");
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        output = (TextView) findViewById(R.id.output);
        search = (EditText) findViewById(R.id.edit_search);

        LocalBroadcastManager.getInstance(getApplicationContext())
                .registerReceiver(mBroadcastReceiver,
                        new IntentFilter(MyService.MY_SERVICE_MESSAGE));

        networkOk = NetworkHelper.hasNetworkAccess(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        LocalBroadcastManager.getInstance(getApplicationContext())
                .unregisterReceiver(mBroadcastReceiver);
    }

    public void runClickHandler(View view) {
        output.setText("");
        s = search.getText().toString().replace(" ", "+");
        String URL = "";
        if (networkOk) {
            if (s != null) {
                URL = JSON_URL + s;
            }
            Intent intent = new Intent(this, MyService.class);
            intent.setData(Uri.parse(URL));
            startService(intent);
        } else {
            Toast.makeText(this, "Network not available!", Toast.LENGTH_SHORT).show();
        }
    }

    public void clearClickHandler(View view) {
        output.setText("");
    }
}

