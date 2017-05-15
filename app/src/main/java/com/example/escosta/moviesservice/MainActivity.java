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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.escosta.moviesservice.model.DataItem;
import com.example.escosta.moviesservice.services.MyService;
import com.example.escosta.moviesservice.utils.NetworkHelper;

public class MainActivity extends AppCompatActivity {

    private static String JSON_URL = "http://www.omdbapi.com/?t=";

    private boolean networkOk;

    TextView title, year, plot, genre, actors, awards,runtime;

    EditText search;

    String s;

    DataItem dataItems = new DataItem();

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            dataItems = (DataItem) intent
                    .getParcelableExtra(MyService.MY_SERVICE_PAYLOAD);

            if (dataItems.getTitle() != null) {
                title.setText(dataItems.getTitle());
                year.setText(dataItems.getYear());
                runtime.setText(dataItems.getRuntime());
                plot.setText(dataItems.getPlot());
                actors.setText(dataItems.getActors());
                genre.setText(dataItems.getGenre());
                awards.setText(dataItems.getAwards());
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        title = (TextView) findViewById(R.id.title);
        year = (TextView) findViewById(R.id.year);
        plot = (TextView) findViewById(R.id.plot);
        runtime = (TextView) findViewById(R.id.runtime);
        genre = (TextView) findViewById(R.id.genre);
        actors = (TextView) findViewById(R.id.actors);
        awards = (TextView) findViewById(R.id.awards);


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

}

