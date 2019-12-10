package com.youth.xframe.utils.http;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.allen.library.base.BaseObserver;
import com.allen.library.bean.BaseData;
import com.allen.library.utils.ToastUtils;
import com.youth.xframe.widget.XLoadingDialog;

import io.reactivex.disposables.Disposable;

public abstract class MyObserver<T> extends BaseObserver<BaseData<T>> {

    private XLoadingDialog dialog;
    private Context mContext;

    public MyObserver(Context context) {
        mContext = context;
    }

    public MyObserver(Context context, boolean hasDialog) {
        mContext = context;
        initDialog(context);
    }

    private void initDialog(Context context) {
        if (dialog == null) {
            dialog = new XLoadingDialog(context);
//            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setMessage("加载中...");
        }
    }

    /**
     * 失败回调
     *
     * @param errorMsg 错误信息
     */
    protected abstract void onError(String errorMsg);

    /**
     * 成功回调
     *
     * @param data 结果
     */
    protected abstract void onSuccess(T data);


    @Override
    public void onSubscribe(Disposable d) {
        super.onSubscribe(d);
        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
        }
    }

    @Override
    public void doOnSubscribe(Disposable d) {
    }

    @Override
    public void doOnError(String errorMsg) {
        if (!isHideToast() && !TextUtils.isEmpty(errorMsg)) {
            ToastUtils.showToast(errorMsg);
        }
        onError(errorMsg);
    }

    @Override
    public void doOnNext(BaseData<T> data) {
//        onSuccess(data.getData());
        //可以根据需求对code统一处理
        switch (data.getCode()) {
            case 200:
                onSuccess(data.getData());
                break;
            default:
                onError(data.getMsg());
        }
    }

    @Override
    public void doOnCompleted() {
    }

    @Override
    public void onError(Throwable e) {
        super.onError(e);
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    @Override
    public void onComplete() {
        super.onComplete();
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}
