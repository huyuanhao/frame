package com.youth.xframe.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

/**
 * Created by wujian on 2016/10/5.
 */

public class SpaceItemDecoration extends RecyclerView.ItemDecoration {
    private int space;
    private boolean includeEdge;
    public SpaceItemDecoration(int spacing, boolean includeEdge) {
        this.space = spacing;
        this.includeEdge = includeEdge;
    }
    public SpaceItemDecoration(int space) {
        this.space = space;
    }

    Paint dividerPaint = null;
    public SpaceItemDecoration(int space, Context context) {
        dividerPaint = new Paint();
        //设置分割线颜色
        dividerPaint.setColor(Color.parseColor("#d8d8d8"));

        this.space = space;
    }

    public SpaceItemDecoration(int space, Context context, int color) {
        dividerPaint = new Paint();
        //设置分割线颜色
        dividerPaint.setColor(context.getResources().getColor(color));
        this.space = space;
    }
    public SpaceItemDecoration(int space, Context context, int color, boolean includeEdge) {
        dividerPaint = new Paint();
        //设置分割线颜色
        dividerPaint.setColor(context.getResources().getColor(color));
        this.space = space;
        this.includeEdge = includeEdge;
    }
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position=parent.getChildAdapterPosition(view);
        if(position!= 0){
            outRect.top = space;
            if(position==parent.getChildCount()&&includeEdge){
                outRect.bottom = space;
            }

        }
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if(dividerPaint == null){
            super.onDraw(c,parent,state);
        }else{
            int childCount = parent.getChildCount();
            int left = parent.getPaddingLeft();
            int right = parent.getWidth() - parent.getPaddingRight();

            for (int i = 0; i < childCount - 1; i++) {
                View view = parent.getChildAt(i);
                float top = view.getBottom();
                float bottom = view.getBottom() + space;
                c.drawRect(left, top, right, bottom, dividerPaint);
            }
        }

    }


}
