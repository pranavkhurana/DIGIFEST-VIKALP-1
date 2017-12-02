package com.example.anurag.vistar.ExtraClasses;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anurag.vistar.Actvities.Album;
import com.example.anurag.vistar.Actvities.BusinessDetails;
import com.example.anurag.vistar.Actvities.CustomBusinessObject;
import com.example.anurag.vistar.MyApplication;
import com.example.anurag.vistar.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anurag on 12/2/2017.
 */

public class AlbumsAdapter extends RecyclerView.Adapter<AlbumsAdapter.MyViewHolder> {


    static ArrayList<CustomBusinessObject> list = new ArrayList<>();

    LayoutInflater inflater;



    private Context mContext;
    private List<Album> albumList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, distance,rating;
        public ImageView thumbnail, overflow;
        public CardView cardView;

        public MyViewHolder(View view) {
            super(view);
            rating= (TextView) view.findViewById(R.id.rating);
            title = (TextView) view.findViewById(R.id.title);
            distance = (TextView) view.findViewById(R.id.distance);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            overflow = (ImageView) view.findViewById(R.id.overflow);
            cardView= (CardView) view.findViewById(R.id.card_view);
        }
    }


    public AlbumsAdapter(Context mContext,ArrayList<CustomBusinessObject> list) {
        this.mContext = mContext;
        this.list = list;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.album_card, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final CustomBusinessObject current = list.get(position);
        Target target = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                int width = bitmap.getWidth();
                int height = bitmap.getHeight();
                if (width > height) {
                    int crop = (width - height) / 2;
                    Bitmap cropImg = Bitmap.createBitmap(bitmap, crop, 0, height, height);
                    holder.thumbnail.setImageBitmap(cropImg);
                } else {
                    int crop = (height - width) / 2;
                    Bitmap cropImg = Bitmap.createBitmap(bitmap, 0, crop, width, width);
                    holder.thumbnail.setImageBitmap(cropImg);
                }

            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };

        holder.thumbnail.setTag(target);
        Picasso.with(mContext).load(MyApplication.IMAGE_LINK+"/"+current.getImgUrl()).into( target);

        // holder.bussImage.setImageResource(R.mipmap.ic_launcher);
        holder.title.setText(current.getName());
        holder.rating.setText("Rating "+current.getRating());
        holder.distance.setText("Distance: "+current.getDistance()/1000.0 +"kms");
        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(holder.overflow);
            }
        });

//       holder.bussName.setText(current.getName());
//        holder.bussCat.setText(current.getCategory());
//        holder.bussRating.setText(current.getRating());

       final String url=MyApplication.LINK+"/disAPI/submit.php?lat="+current.lat+"&lon="+current.lon+"&bussiness_id"+current.businessId;


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(mContext,BusinessDetails.class);
                intent.putExtra("link",url);
                intent.putExtra("bussImage",MyApplication.IMAGE_LINK+"/"+current.getImgUrl());
                intent.putExtra("bussDes",current.getDiscription());
                intent.putExtra("bussCat",current.getCategory());
                intent.putExtra("rating",current.getRating());
                intent.putExtra("name",current.getName());
                intent.putExtra("available",current.getTotalAailibility());
                intent.putExtra("distance",current.getDistance()/1000.0+"");
                intent.putExtra("id",current.getBusinessId());
                mContext.startActivity(intent);

            }
        });




//
//        // loading album cover using Glide library
//        Glide.with(mContext).load(album.getThumbnail()).into(holder.thumbnail);
//
//        holder.overflow.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showPopupMenu(holder.overflow);
//            }
//        });
    }

    /**
     * Showing popup menu when tapping on 3 dots
     */
    private void showPopupMenu(View view) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_album, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popup.show();
    }

    /**
     * Click listener for popup menu items
     */
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public MyMenuItemClickListener() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_add_favourite:
                    Toast.makeText(mContext, "Add to favourite", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.action_play_next:
                    Toast.makeText(mContext, "Play next", Toast.LENGTH_SHORT).show();
                    return true;
                default:
            }
            return false;
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}