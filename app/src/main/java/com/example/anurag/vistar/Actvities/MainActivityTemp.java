package com.example.anurag.vistar.Actvities;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.anurag.vistar.ExtraClasses.AlbumsAdapter;
import com.example.anurag.vistar.ExtraClasses.MyAsyncTask;
import com.example.anurag.vistar.ExtraClasses.Utility;
import com.example.anurag.vistar.Interface.ListLoadedListener;
import com.example.anurag.vistar.MyApplication;
import com.example.anurag.vistar.R;

import java.util.ArrayList;
import java.util.List;


public class MainActivityTemp extends AppCompatActivity  implements ListLoadedListener {
    public static final String MY_PREFS_NAME = "myPref";
    static Context context;
    LinearLayout mLayout;
   // Button buttonResult;
    private RecyclerView recyclerView;
    private AlbumsAdapter adapter;
    private List<Album> albumList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_temp);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initCollapsingToolbar();
        mLayout = (LinearLayout) findViewById(R.id.headerProgress);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        context = this;
       // buttonResult = (Button) findViewById(R.id.button_result);
        try {
            Glide.with(this).load(R.drawable.b4).into((ImageView) findViewById(R.id.backdrop));
        } catch (Exception e) {
            e.printStackTrace();
        }

//        buttonResult.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });

        Log.d("karma", "clicked");
        if (Utility.isConnected(getBaseContext())) {
            mLayout.setVisibility(View.VISIBLE);
            // make a call to the Async Task
            SharedPreferences prefs = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
            final String latitude = prefs.getString("currentLat", null);
            final String longitude = prefs.getString("currentLong", null);
            String url = MyApplication.LINK+"/disAPI/submit.php?lat=" + latitude + "&lon=" + longitude;
            MyAsyncTask task = new MyAsyncTask(context, url);
            task.execute();

        } else {
            LinearLayout layout = (LinearLayout) findViewById(R.id.linear);
            Snackbar s = Snackbar.make(layout, "You are not connected to INTERNET", Snackbar.LENGTH_LONG);
            s.show();
        }


        albumList = new ArrayList<>();



//        prepareAlbums();


    }

    /**
     * Initializing collapsing toolbar
     * Will show and hide the toolbar title on scroll
     */
    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle(getString(R.string.app_name));
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }

    /**
     * Adding few albums for testing
     */
    private void prepareAlbums() {
        int[] covers = new int[]{
                R.drawable.ico_mic,
                R.drawable.ico_mic,
                R.drawable.ico_mic,
                R.drawable.ico_mic,
                R.drawable.ico_mic,
                R.drawable.ico_mic,
                R.drawable.ico_mic,
                R.drawable.ico_mic,
                R.drawable.ico_mic,
                R.drawable.ico_mic,
                R.drawable.ico_mic};

        Album a = new Album("True Romance", 13, covers[0]);
        albumList.add(a);

        a = new Album("Xscpae", 8, covers[1]);
        albumList.add(a);

        a = new Album("Maroon 5", 11, covers[2]);
        albumList.add(a);

        a = new Album("Born to Die", 12, covers[3]);
        albumList.add(a);

        a = new Album("Honeymoon", 14, covers[4]);
        albumList.add(a);

        a = new Album("I Need a Doctor", 1, covers[5]);
        albumList.add(a);

        a = new Album("Loud", 11, covers[6]);
        albumList.add(a);

        a = new Album("Legend", 14, covers[7]);
        albumList.add(a);

        a = new Album("Hello", 11, covers[8]);
        albumList.add(a);

        a = new Album("Greatest Hits", 17, covers[9]);
        albumList.add(a);

        adapter.notifyDataSetChanged();
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    @Override
    public void onListLoaded(ArrayList<CustomBusinessObject> list) {

        mLayout.setVisibility(View.GONE);
        if (!list.isEmpty()) {
//            recyclerView.addItemDecoration(new VerticalSpace(10));
//            BusinessListingAdapter adapter = new BusinessListingAdapter(this, list);
//            recyclerView.setAdapter(adapter);
//            LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
//            recyclerView.setLayoutManager(mLayoutManager);
            adapter = new AlbumsAdapter(this, list);

            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(adapter);




        } else {
            Toast.makeText(this, "No result  for the given Location. Hence Try again with something else..", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }
}