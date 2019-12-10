package com.youth.xframe.utils;

import android.graphics.Rect;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

/**
 * Created by Administrator on 2018/6/7.
 */

public class LineItemDecoration extends RecyclerView.ItemDecoration {
    private int height = 1;
    public LineItemDecoration(int height){
        this.height = height;
    }
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.set(0,0,0,height);
    }
}
