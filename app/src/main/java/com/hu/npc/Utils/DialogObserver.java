package com.hu.npc.Utils;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.allen.library.base.BaseObserver;
import com.allen.library.bean.BaseData;
import com.allen.library.utils.ToastUtils;
import com.hu.npc.http.Api;
import com.hu.npc.ui.login.LoginActivity;
import com.youth.xframe.utils.XPreferencesUtils;
import com.youth.xframe.widget.XLoadingDialog;

import io.reactivex.disposables.Disposable;

public abstract class DialogObserver<T> extends BaseObserver<BaseData<T>> {

    private XLoadingDialog dialog;
    private Context mContext;

    public DialogObserver(Context context) {
        mContext = context;
    }

    public DialogObserver(Context context, boolean hasDialog) {
        mContext = context;
        if (hasDialog) {
            initDialog(context);
        }
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
            case 301:
                onError(data.getMsg());
                XPreferencesUtils.put(Api.LoginStatu, 2);
                mContext.startActivity(new Intent(mContext, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                break;
            case 302:
                onError(data.getMsg());
                XPreferencesUtils.put(Api.LoginStatu, 3);
                mContext.startActivity(new Intent(mContext, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
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
