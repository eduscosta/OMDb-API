package com.example.escosta.omdbservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.escosta.omdbservice.databinding.ActivityMainBinding;
import com.example.escosta.omdbservice.model.DataItem;
import com.example.escosta.omdbservice.services.MyService;
import com.example.escosta.omdbservice.utils.NetworkHelper;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = "EDUARDO";
    private static String JSON_URL = "http://www.omdbapi.com/?t=";

    private boolean networkOk;

    ActivityMainBinding binding;

    String title, year, plot, genre, actors, awards,runtime;

    EditText search;

    String s;

    DataItem dataItems = new DataItem();

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            dataItems = (DataItem) intent
                    .getParcelableExtra(MyService.MY_SERVICE_PAYLOAD);

            title = (dataItems.getTitle());
            year = (dataItems.getYear());
            runtime = (dataItems.getRuntime());
            plot = (dataItems.getPlot());
            actors = (dataItems.getActors());
            genre = (dataItems.getGenre());
            awards = (dataItems.getAwards());

            Movie movie = new Movie(title, year, runtime, genre, actors, plot, awards);

            binding.setMovie(movie);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        search = (EditText) findViewById(R.id.edit_search);

        LocalBroadcastManager.getInstance(getApplicationContext())
                .registerReceiver(mBroadcastReceiver,
                        new IntentFilter(MyService.MY_SERVICE_MESSAGE));

        networkOk = NetworkHelper.hasNetworkAccess(this);


//        Movie movie = new Movie("Eduardo", "1996", "20", "fiction", "Eduardo Costa", "Intern at IBM", "Best Intern");

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
