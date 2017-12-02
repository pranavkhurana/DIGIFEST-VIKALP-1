package com.example.anurag.vistar;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.anurag.vistar.Actvities.BusinessListingAdapter;
import com.example.anurag.vistar.Actvities.CustomBusinessObject;
import com.example.anurag.vistar.ExtraClasses.MyAsyncTask;
import com.example.anurag.vistar.ExtraClasses.Utility;
import com.example.anurag.vistar.ExtraClasses.VerticalSpace;
import com.example.anurag.vistar.Interface.ListLoadedListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements  ListLoadedListener {
    public static final String MY_PREFS_NAME = "myPref";
    RecyclerView recyclerView;
    Button getLocationBtn;
    //LocationManager locationManager;
    ArrayList<CustomBusinessObject> list = new ArrayList<>();
    Toolbar toolbar;
    LinearLayout mLayout;
    Button buttonResult;
    static Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        mLayout = (LinearLayout) findViewById(R.id.headerProgress);
        context=this;

        buttonResult = (Button) findViewById(R.id.button_result);
        getLocationBtn = (Button) findViewById(R.id.button_location);

        getLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("karma", "clicked");
                if (Utility.isConnected(getBaseContext())) {
                   // mLayout.setVisibility(View.VISIBLE);
//                    getLocation();
                } else {

                }

            }
        });
  buttonResult.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
          Log.d("karma", "clicked");
          if (Utility.isConnected(getBaseContext())) {
              mLayout.setVisibility(View.VISIBLE);
              // make a call to the Async Task
              SharedPreferences prefs = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
              final String latitude = prefs.getString("currentLat", null);
              final String longitude=prefs.getString("currentLong",null);
              String url=MyApplication.LINK+"/disAPI/submit.php?lat="+latitude+"&lon="+longitude;
              MyAsyncTask task = new MyAsyncTask(context,url);
              task.execute();

          } else {
              LinearLayout layout = (LinearLayout) findViewById(R.id.linear);
              Snackbar s = Snackbar.make(layout, "You are not connected to INTERNET", Snackbar.LENGTH_LONG);
              s.show();
          }
      }
  });
        recyclerView = (RecyclerView) findViewById(R.id.recycler);


    }






    // Interface ListLoaded Listener
    @Override
    public void onListLoaded(ArrayList<CustomBusinessObject> list) {
        mLayout.setVisibility(View.GONE);
        if (!list.isEmpty()) {
            recyclerView.addItemDecoration(new VerticalSpace(10));
            BusinessListingAdapter adapter = new BusinessListingAdapter(this, list);
            recyclerView.setAdapter(adapter);
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(mLayoutManager);
        } else {
            Toast.makeText(this, "No result  for the given Location. Hence Try again with something else..", Toast.LENGTH_LONG).show();
        }

    }
}
