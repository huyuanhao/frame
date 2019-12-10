package com.youth.xframe.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.youth.xframe.R;

import java.io.File;

/**
 * Created by Administrator on 2018/6/13.
 */

public class ImageLoad {

    //    public static void setFile(Context context, File file, ImageView viewId) {
//        RequestOptions options = new RequestOptions()
//                .centerCrop()
//                .placeholder(R.drawable.public_img_loading)// 正在加载中的图片  
//                .error(R.drawable.public_img_failed)// 加载失败的图片  
//                .diskCacheStrategy(DiskCacheStrategy.ALL);// 磁盘缓存策略  
//        Glide.with(context)
//                .load(file)// 图片地址  
//                .apply(options)// 参数  
//                .into(viewId);// 需要显示的ImageView控件 
//    }
//
    public static void setUrl(Context context, String url, ImageView viewId) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.sample_footer_loading)// 正在加载中的图片  
                .error(R.drawable.xloading_empty)// 加载失败的图片   
                .diskCacheStrategy(DiskCacheStrategy.ALL);// 磁盘缓存策略  
        Glide.with(context)
                .load(url)// 图片地址  
                .apply(options)// 参数  
                .into(viewId);// 需要显示的ImageView控件 
    }

    public static void setUrl(Context context, String url, ImageView viewId, boolean isfitCenter) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        RequestOptions options = new RequestOptions();
        if (isfitCenter) {
            options.fitCenter();
        } else {
            options.centerCrop();
        }
        options
                .placeholder(R.drawable.sample_footer_loading)// 正在加载中的图片  
                .error(R.drawable.xloading_empty)// 加载失败的图片   
                .diskCacheStrategy(DiskCacheStrategy.ALL);// 磁盘缓存策略  
        Glide.with(context)
                .load(url)// 图片地址  
                .apply(options)// 参数  
                .into(viewId);// 需要显示的ImageView控件
    }

//
//    public static void setImg(Context context, String imgUrl, ImageView viewId) {
//        RequestOptions options = new RequestOptions()
//                .centerCrop()
//                .placeholder(R.drawable.public_img_loading)// 正在加载中的图片  
//                .error(R.drawable.public_img_failed)// 加载失败的图片   
//                .diskCacheStrategy(DiskCacheStrategy.ALL);// 磁盘缓存策略  
//        Glide.with(context)
//                .load(imgUrl)// 图片地址  
//                .apply(options)// 参数  
//                .into(viewId);// 需要显示的ImageView控件 
//    }
//
//
//    public static void setImgNoMemory(Context context, String imgUrl, ImageView viewId) {
//        RequestOptions options = new RequestOptions()
//                .centerCrop()
//                .placeholder(R.drawable.public_img_loading)// 正在加载中的图片  
//                .error(R.drawable.public_img_failed)// 加载失败的图片   
//                .skipMemoryCache(true) // 不使用内存缓存
//                .diskCacheStrategy(DiskCacheStrategy.NONE);// 磁盘缓存策略  
//        Glide.with(context)
//                .load(imgUrl)// 图片地址  
//                //.skipMemoryCache(true) // 不使用内存缓存
//                .apply(options)// 参数  
//                .into(viewId);// 需要显示的ImageView控件 
//    }
//
//
//    public static void setImg(Context context, String imgUrl, ImageView viewId, boolean fitCenter) {
//        RequestOptions options = new RequestOptions();
//        if (fitCenter) {
//            options.fitCenter();
//        } else {
//            options.centerCrop();
//        }
//        options.placeholder(R.drawable.public_img_loading)// 正在加载中的图片  
//                .error(R.drawable.public_img_failed)// 加载失败的图片   
//                .diskCacheStrategy(DiskCacheStrategy.ALL);// 磁盘缓存策略  
//        Glide.with(context)
//                .load(imgUrl)// 图片地址  
//                .apply(options)// 参数  
//                .into(viewId);// 需要显示的ImageView控件 
//    }
//
//    public static void setTest(Context context, ImageView viewId) {
//        RequestOptions options = new RequestOptions()
//                .centerCrop()
//                .placeholder(R.drawable.public_img_loading)// 正在加载中的图片  
//                .error(R.drawable.public_img_failed)// 加载失败的图片  
//                .diskCacheStrategy(DiskCacheStrategy.ALL);// 磁盘缓存策略  
//        Glide.with(context)
//                .load("http://img.bimg.126.net/photo/ZZ5EGyuUCp9hBPk6_s4Ehg==/5727171351132208489.jpg")// 图片地址  
//                .apply(options)// 参数  
//                .into(viewId);// 需要显示的ImageView控件 
//    }
//
//
//    public static void setCircleUrl(Context context, String url, ImageView viewId, int defaultRes) {
//        Glide.with(context)
//                .load(url)
//                .into(viewId);
////        Picasso.with(context).load(url).fit().centerCrop().into(viewId);
//    }

}
