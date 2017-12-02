package com.example.anurag.vistar.Actvities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anurag.vistar.CommunicateActivity;
import com.example.anurag.vistar.R;
import com.example.anurag.vistar.WebView.WebViewActivity;
import com.squareup.picasso.Picasso;

import java.util.Locale;

public class BusinessDetails extends AppCompatActivity {
    public static final String MY_PREFS_NAME = "myPref";
    Toolbar toolbar;
    CollapsingToolbarLayout mToolbarLayout;
    Button communicate, findWayButton, buyProd;
    ImageView imageView;
    FloatingActionButton floatingActionButton;
    CoordinatorLayout coordinatorLayout;
    TextView category, distance, title, description;
    Context context;
    String imageUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_details);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.above_collapsing_toolbar);
        context = this;
        mToolbarLayout.setTitle(" ");
        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);

        imageView = (ImageView) findViewById(R.id.headerImage);
        title = (TextView) findViewById(R.id.title);
        buyProd= (Button) findViewById(R.id.buy);
        category = (TextView) findViewById(R.id.name);
        distance = (TextView) findViewById(R.id.date);
        description = (TextView) findViewById(R.id.description);
        findWayButton = (Button) findViewById(R.id.find_way);
        communicate = (Button) findViewById(R.id.communicate);
        communicate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), CommunicateActivity.class));
            }
        });



        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        final String latitude = prefs.getString("currentLat", null);
        final String longitude = prefs.getString("currentLong", null);

        findWayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (latitude == null || longitude == null) {
                    Toast.makeText(getBaseContext(), "Please Enable your location first", Toast.LENGTH_LONG);
                } else {
                    String uri = String.format(Locale.ENGLISH, "geo:%f,%f", Double.parseDouble(latitude), Double.parseDouble(longitude));

                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                    startActivity(intent);
                }
            }
        });


        //LoadData

        final Intent intent = getIntent();

        String imageUrl = intent.getStringExtra("bussImage");
        String des = intent.getStringExtra("bussDes");
        String cat = intent.getStringExtra("bussCat");
        String name = intent.getStringExtra("name");
        String rating = intent.getStringExtra("rating");
        String available = intent.getStringExtra("available");
        String distance = intent.getStringExtra("distance");
        imageUrl=intent.getStringExtra("bussImage");
        Log.d("karma","image url is "+imageUrl);
        Picasso.with(this).load(imageUrl).into( imageView);

        buyProd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.putExtra("id",intent.getIntExtra("id",0));
                startActivity(new Intent(getBaseContext(),WebViewActivity.class));
            }
        });

        title.setText(name);
        category.setText("Category: " + cat + "                     rating: " + rating);
        this.distance.setText("Distance: " + distance + "kms             availableity Status" + available);
        description.setText(des);
        Picasso.with(this).load(imageUrl).into(imageView);


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, RatingActivity.class));
            }
        });

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator);

    }


}
