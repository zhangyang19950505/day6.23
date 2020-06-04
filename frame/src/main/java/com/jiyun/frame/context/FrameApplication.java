package com.jiyun.frame.context;

import android.content.Context;
import android.text.TextUtils;

import com.jiyun.frame.bean.SpecialtyBean;
import com.jiyun.frame.bean.Device;
import com.jiyun.frame.bean.LoginInfo;
import com.yiyatech.utils.UtilsApplication;


public class FrameApplication extends UtilsApplication {
    private static FrameApplication application;
    private Device mDeviceInfo;
    private LoginInfo mLoginInfo;
    private String cookie;
    private SpecialtyBean.ResultBean.DataBean selectedInfo;

    public SpecialtyBean.ResultBean.DataBean getSelectedInfo() {
        return selectedInfo;
    }

    public void setSelectedInfo(SpecialtyBean.ResultBean.DataBean selectedInfo) {
        this.selectedInfo = selectedInfo;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public LoginInfo getLoginInfo() {
        return mLoginInfo;
    }

    public boolean isLogin(){
        return mLoginInfo!=null&&!TextUtils.isEmpty(mLoginInfo.getUid());
    }

    public void setLoginInfo(LoginInfo mLoginInfo) {
        this.mLoginInfo = mLoginInfo;
    }

    public Device getDeviceInfo() {
        return mDeviceInfo;
    }

    public void setDeviceInfo(Device mDeviceInfo) {
        this.mDeviceInfo = mDeviceInfo;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
    }

    public static FrameApplication getFrameApplication(){
        return application;
    }

    public static Context getFrameApplicationContext(){
        return application.getApplicationContext();
    }
}
