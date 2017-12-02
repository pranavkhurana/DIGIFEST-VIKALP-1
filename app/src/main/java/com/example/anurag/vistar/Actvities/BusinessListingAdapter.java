package com.example.anurag.vistar.Actvities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.anurag.vistar.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;

/**
 * Created by anurag on 12/2/2017.
 */

public class BusinessListingAdapter extends RecyclerView.Adapter<BusinessListingAdapter.MyHolder> {
    static ArrayList<CustomBusinessObject> list = new ArrayList<>();
    Context context;
    LayoutInflater inflater;

    public BusinessListingAdapter(Context context, ArrayList<CustomBusinessObject> list) {


        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_business_view, parent, false);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyHolder holder, int position) {
   final CustomBusinessObject current = list.get(position);
        Target target = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                int width = bitmap.getWidth();
                int height = bitmap.getHeight();
                if (width > height) {
                    int crop = (width - height) / 2;
                    Bitmap cropImg = Bitmap.createBitmap(bitmap, crop, 0, height, height);
                    holder.bussImage.setImageBitmap(cropImg);
                } else {
                    int crop = (height - width) / 2;
                    Bitmap cropImg = Bitmap.createBitmap(bitmap, 0, crop, width, width);
                    holder.bussImage.setImageBitmap(cropImg);
                }

            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };

        holder.bussImage.setTag(target);
        Picasso.with(context).load("http://192.168.137.1/img/"+current.getImgUrl()).into( target);
       // holder.bussImage.setImageResource(R.mipmap.ic_launcher);
        holder.bussName.setText(current.getName());
        holder.bussCat.setText(current.getCategory());
        holder.bussRating.setText(current.getRating());
        final String url="http://192.168.137.1/disAPI/submit.php?lat="+current.lat+"&lon="+current.lon+"&bussiness_id"+current.businessId;


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(context,BusinessDetails.class);
                intent.putExtra("link",url);
                intent.putExtra("bussImage","http://192.168.137.1/img/"+current.getImgUrl());
                intent.putExtra("bussDes",current.getDiscription());
                intent.putExtra("bussCat",current.getCategory());
                intent.putExtra("rating",current.getRating());
                intent.putExtra("name",current.getName());
                intent.putExtra("available",current.getTotalAailibility());
                intent.putExtra("distance",current.getDistance()+"");
                context.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{
        ImageView bussImage;
        TextView bussName;
        TextView bussCat;
        TextView bussDes;
        TextView bussRating;

        CardView cardView;

        public MyHolder(View itemView) {
            super(itemView);
            bussImage = (ImageView) itemView.findViewById(R.id.buss_image);
            bussName = (TextView) itemView.findViewById(R.id.buss_name);
            bussCat = (TextView) itemView.findViewById(R.id.buss_cat);
           // bussDes = (TextView) itemView.findViewById(R.id.buss_des);
            cardView = (CardView) itemView.findViewById(R.id.cardView);
            bussRating= (TextView) itemView.findViewById(R.id.buss_rating);

        }


    }

}
