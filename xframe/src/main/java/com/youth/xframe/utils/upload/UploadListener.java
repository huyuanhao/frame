package com.youth.xframe.utils.upload;

import okhttp3.Response;

/**
 */
public interface UploadListener {
    void uploadFail(String message, int position);
    void uploadSuccess(Response response, int position);
    void uploading(int process, int position);
}
