package com.youth.xframe.base;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.youth.xframe.R;
import com.youth.xframe.common.XActivityStack;
import com.youth.xframe.utils.permission.XPermission;
import com.youth.xframe.utils.statusbar.MyStatusBarUtil;

import butterknife.ButterKnife;
import butterknife.Unbinder;


public abstract class XActivity extends AppCompatActivity implements ICallback {
    private Unbinder mUnbinder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        XActivityStack.getInstance().addActivity(this);
        if (getLayoutId()>0) {
            setContentView(getLayoutId());
            mUnbinder = ButterKnife.bind(this);
        }
        MyStatusBarUtil.setStatusBarColor(this, R.color.theme_color);
        initData(savedInstanceState);
        initView();
    }

    /**
     * 设置状态栏透明
     * @param fitWindow 是否有间距
     */
    public void setStatusBarTransparent(boolean fitWindow){
        MyStatusBarUtil.setStatusBarTransparent(this, fitWindow);
    }
    /**
     * 设置状态栏颜色
     * @param color
     */
    public void setStatusBarColor(int color){
        MyStatusBarUtil.setStatusBarColor(this, color);
    }
    /**
     * 设置状态栏字体为黑色
     */
    public void setStatusBarTextBlack(){
        MyStatusBarUtil.setStatusBarTextColor(this,true);
    }

    /**
     * Android M 全局权限申请回调
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[]
            grantResults) {
        XPermission.onRequestPermissionsResult(requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null && mUnbinder != Unbinder.EMPTY) {
            mUnbinder.unbind();
        }
        this.mUnbinder = null;
        XActivityStack.getInstance().finishActivity(this);
    }
}
