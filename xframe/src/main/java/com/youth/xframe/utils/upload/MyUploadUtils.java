package com.youth.xframe.utils.upload;

import android.util.Log;

import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 文件名：
 * 描述：
 * 作者：胡元浩
 * 时间：2019/2/14
 * 版权：
 */
public class MyUploadUtils {
    private OkHttpClient mOkHttpClient;
    private UploadListener mUploadListener;


    public MyUploadUtils(UploadListener uploadListener) {
        mUploadListener = uploadListener;
    }

    /**
     * 开始上传的操作
     */
    public void upload(String originUrl, String key, File file) {
        upload(originUrl,key,file,0);
    }
    public void upload(String originUrl, String key, File file,int position) {
        uploadStart(originUrl, file,position);
    }




    private void uploadStart(final String url, final File file, final int position) {
        mOkHttpClient = new OkHttpClient().newBuilder()
                .build();
        new Thread(new Runnable() {
            @Override
            public void run() {
                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("file", file.getName(),
                                RequestBody.create(MediaType.parse("image/png"), file))
                        .build();
                ProgressRequestBody body = new ProgressRequestBody(requestBody) {
                    @Override
                    public void loading(long current, long total, boolean done) {
                        int progress = getPercent(current,total);
                        mUploadListener.uploading(progress,position);
                        Log.d("UploadUtils",
                                "--Upload-- " + progress + " %  " + "position " + position + "--current " +current + " byte  tatal" + total + " byte  ");
                    }
                };
                Request request = new Request.Builder()
                        .url(url)
                        .post(body)
                        .build();

                Call call = mOkHttpClient.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        mUploadListener.uploadFail(e.toString(),position);
                        Log.d("UploadUtils", "onFailure + position="+ position  +" IOException=" + e.toString());
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        mUploadListener.uploadSuccess(response,position);
                    }
                });
            }
        }).start();
    }

    public int getPercent(long current,long total) {
        if (current <= 0 || total <= 0){ return 0;}
        return (int) ((100 * current) / total);
    }
}
