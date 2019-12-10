package com.youth.xframe.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.youth.xframe.widget.XToast;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//import cn.sharesdk.framework.Platform;
//import cn.sharesdk.framework.PlatformActionListener;
//import cn.sharesdk.onekeyshare.OnekeyShare;
//import cn.sharesdk.onekeyshare.ShareContentCustomizeCallback;
//import cn.sharesdk.sina.weibo.SinaWeibo;
//import cn.sharesdk.tencent.qq.QQ;
//import cn.sharesdk.wechat.friends.Wechat;
//import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * @author PC
 * @date 2019/02/21 14:22
 */
public class ShareUtils {
    private static Context mContext;
    public static void share(Context context, String title, String text) {
        share(context, title, text, null);
    }

    public static void share(Context context, String title, File file) {
        share(context, title, "", file);
    }

    static Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    break;
                case 1:
                    Toast.makeText(mContext, "无分享内容", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    Toast.makeText(mContext, "暂无可分享应用", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
    public static void share(Context context, String title, String text, File file) {
        mContext = context;
        new Thread(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
// 查询所有可以分享的Activity
                List<ResolveInfo> resInfo = context.getPackageManager().queryIntentActivities(intent,
                        PackageManager.MATCH_DEFAULT_ONLY);
                if (!resInfo.isEmpty()) {
                    List<Intent> targetedShareIntents = new ArrayList<Intent>();
                    for (ResolveInfo info : resInfo) {
                        Intent targeted = new Intent(Intent.ACTION_SEND);
                        ActivityInfo activityInfo = info.activityInfo;
                        Log.v("logcat", "packageName=" + activityInfo.packageName + "Name=" + activityInfo.name);

                        if (null != file && file.exists()) {
                            targeted.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
                            targeted.setType(getMimeType(file.getAbsolutePath()));//此处可发送多种文件
                        } else if (!TextUtils.isEmpty(text)) {
                            // 分享出去的内容
                            targeted.putExtra(Intent.EXTRA_TEXT, text);
                            targeted.setType("text/plain");
                        } else {
                            mHandler.sendEmptyMessage(1);
                        }
                        // 分享出去的标题
                        if (activityInfo.packageName.equals("com.sina.weibo") ||
                                activityInfo.packageName.equals("com.tencent.mobileqq") ||
                                activityInfo.packageName.equals("com.tencent.mm")) {

//                    if(activityInfo.name.equals("cooperation.qqfav.widget.QfavJumpActivity")){
//                        return;
//                    }
                            targeted.putExtra(Intent.EXTRA_SUBJECT, title);
                            targeted.setPackage(activityInfo.packageName);
                            targeted.setClassName(activityInfo.packageName, info.activityInfo.name);
                            targetedShareIntents.add(targeted);
                        }
                        PackageManager pm = context.getPackageManager();
                        // 微信有2个怎么区分-。- 朋友圈还有微信
//                        if (info.activityInfo.applicationInfo.loadLabel(pm).toString().equals("微信") &&
//                                !info.activityInfo.applicationInfo.loadLabel(pm).toString().contains("收藏")) {
//                            targetedShareIntents.add(targeted);
//                        }
//                        if (info.activityInfo.applicationInfo.loadLabel(pm).toString().equals("微信") && !info.loadLabel(pm).toString().contains("添加到微信收藏")) {
//                            targetedShareIntents.add(targeted);
//                        }
//                        if (info.activityInfo.applicationInfo.loadLabel(pm).toString().contains("朋友圈")) {
//                            targetedShareIntents.add(targeted);
//                        }
////只是为了显示效果，如果只有一个可以分享的，会直接跳转，没有Dialog
//                if (info.activityInfo.applicationInfo.loadLabel(pm).toString().equals("QQ")) {
//                    targetedShareIntents.add(targeted);
//                }
//                targetedShareIntents.add(targeted);
                    }
                    if(targetedShareIntents.size()==0){
                        mHandler.sendEmptyMessage(2);
                        return;
                    }
                    // 选择分享时的标题
                    Intent chooserIntent = Intent.createChooser(targetedShareIntents.remove(0), "选择分享");
                    if (chooserIntent == null) {
                        return;
                    }
                    chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, targetedShareIntents.toArray(new Parcelable[]{}));
                    try {
                        context.startActivity(chooserIntent);
                    } catch (android.content.ActivityNotFoundException ex) {
                        mHandler.sendEmptyMessage(2);
                    }
                }
            }
        }).start();
    }

    // 調用系統方法分享文件
    public static void shareFile(Context context, File file) {
        if (null != file && file.exists()) {
            Intent share = new Intent(Intent.ACTION_SEND);
            share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
            share.setType(getMimeType(file.getAbsolutePath()));//此处可发送多种文件
            share.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            context.startActivity(Intent.createChooser(share, "分享文件"));
        } else {
            XToast.info("分享文件不存在");
        }
    }

    // 根据文件后缀名获得对应的MIME类型。
    private static String getMimeType(String filePath) {
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        String mime = "*/*";
        if (filePath != null) {
            try {
                mmr.setDataSource(filePath);
                mime = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_MIMETYPE);
            } catch (IllegalStateException e) {
                return mime;
            } catch (IllegalArgumentException e) {
                return mime;
            } catch (RuntimeException e) {
                return mime;
            }
        }
        return mime;
    }


    /**
     * shared第三方分享
     * @param context 对象
     * @param title 标题
     * @param text 介绍
     * @param imageUrl 图片地址
     * @param url 网页地址
     */
    public static void showShare(Context context,String title, String text, String imageUrl, String url) {
//        OnekeyShare oks = new OnekeyShare();
//        //关闭sso授权
//        oks.disableSSOWhenAuthorize();
//        oks.setTitle(title);
//        oks.setText(text);
//        oks.setImageUrl(imageUrl);
//        oks.setUrl(url);
//        oks.setTitleUrl(url);//适配QQ分享，作用同setImageUrl，其他用setImageUrl
//        oks.setCallback(new PlatformActionListener() {
//            @Override
//            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
//                if (QQ.NAME.equals(platform.getName())) {//qq
//                } else if (SinaWeibo.NAME.equals(platform.getName())) {//新浪微博
//                } else if (Wechat.NAME.equals(platform.getName())) {//微信
//                } else if (WechatMoments.NAME.equals(platform.getName())) {//微信朋友圈
//                }
////                ToastUtils.showToast(context,"分享成功");
//            }
//
//            @Override
//            public void onError(Platform platform, int i, Throwable throwable) {
//                ToastUtils.showToast(context,"分享失败");
//                Log.i("hyh", "onError: " + platform.toString() + "throwable" + throwable);
//            }
//
//            @Override
//            public void onCancel(Platform platform, int i) {
////                ToastUtils.showToast(context,"分享取消");
//            }
//        });
//        oks.setShareContentCustomizeCallback(new ShareContentCustomizeCallback() {
//            @Override
//            public void onShare(Platform platform, Platform.ShareParams shareParams) {
//                shareParams.setShareType(Platform.SHARE_WEBPAGE);//卡片形式
//                Log.i("hyh", "onShare: " + platform.getName());
//                if (QQ.NAME.equals(platform.getName())) {//qq
//                } else if (SinaWeibo.NAME.equals(platform.getName())) {//新浪微博
//                } else if (Wechat.NAME.equals(platform.getName())) {//微信
//                } else if (WechatMoments.NAME.equals(platform.getName())) {//微信朋友圈
//                }
//            }
//        });
//// 启动分享GUI
//        oks.show(context);
    }
}
