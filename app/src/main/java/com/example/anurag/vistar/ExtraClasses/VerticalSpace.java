package com.example.anurag.vistar.ExtraClasses;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by anurag on 12/2/2017.
 */
public class VerticalSpace extends RecyclerView.ItemDecoration {
    int space;
   public VerticalSpace(int space){
        this.space=space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.bottom=space;
        outRect.right=space;
        outRect.left=space;
        if(parent.getChildLayoutPosition(view)==0){
            outRect.top=space;
        }
    }
}
