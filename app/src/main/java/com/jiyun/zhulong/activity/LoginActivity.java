package com.jiyun.zhulong.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiyun.bean.BaseInfo;
import com.jiyun.bean.LoginInfo;
import com.jiyun.bean.PersonHeader;
import com.jiyun.bean.ThirdLoginData;
import com.jiyun.frame.api.ApiConfig;
import com.jiyun.frame.api.LoadTypeConfig;
import com.jiyun.frame.constants.ConstantKey;
import com.jiyun.frame.mvp.ICommonModel;
import com.jiyun.zhulong.R;
import com.jiyun.zhulong.base.BaseMvpActiviy;
import com.jiyun.zhulong.design.LoginView;
import com.jiyun.zhulong.model.AccountModel;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.yiyatech.utils.newAdd.SharedPrefrenceUtils;
import com.zhulong.eduvideo.wxapi.WXEntryActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LoginActivity extends BaseMvpActiviy implements LoginView.LoginViewCallBack {
    @BindView(R.id.login_view)
    LoginView mLoginView;
    @BindView(R.id.close_login)
    ImageView closeLogin;
    @BindView(R.id.register_press)
    TextView registerPress;
    private Disposable mSubscribe;
    private String phoneNum;
    private long time = 59l;
    private Intent intent;
    private String activityName;


    @Override
    protected int setLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected ICommonModel setModel() {
        return new AccountModel();
    }

    @Override
    protected void initView() {
        intent = getIntent();
        mLoginView.setLoginViewCallBack(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onSuccess(int apiConfig, int loadTypeConfig, Object[] objects) {
        switch (apiConfig) {
            case ApiConfig.SEND_VERIFY:
                BaseInfo<String> info = (BaseInfo<String>) objects[0];
                if (info.result != null) {
                    showToast(info.result);
                }
                goTime();
                break;
            case ApiConfig.VERIFY_LOGIN:
            case ApiConfig.ACCOUNT_LOGIN:
            case ApiConfig.POST_WE_CHAT_LOGIN_INFO:
                BaseInfo<LoginInfo> baseInfo = (BaseInfo<LoginInfo>) objects[0];
                LoginInfo loginInfo = baseInfo.result;
                if (!TextUtils.isEmpty(phoneNum))
                    loginInfo.login_name = phoneNum;
                mApplication.setLoginInfo(loginInfo);
                mPresenter.getData(ApiConfig.GET_HEADER_INFO, LoadTypeConfig.NORMAL);
                break;
            case ApiConfig.GET_HEADER_INFO:
                PersonHeader personHeader = ((BaseInfo<PersonHeader>) objects[0]).result;
                mApplication.getLoginInfo().personHeader = personHeader;
                //将用户信息保存到本地，下次将 不再登录
                SharedPrefrenceUtils.putObject(this, ConstantKey.LOGIN_INFO, mApplication.getLoginInfo());
                showLog("登录成功，-------用户信息：" + SharedPrefrenceUtils.getObject(this, ConstantKey.LOGIN_INFO));
                jump();
                break;
            case ApiConfig.GET_WE_CHAT_TOKEN:
                JSONObject allJson = null;
                try {
                    allJson = new JSONObject(objects[0].toString());
                } catch (JSONException pE) {
                    pE.printStackTrace();
                }
                ThirdLoginData thirdData = new ThirdLoginData(3);
                thirdData.setOpenid(allJson.optString("openid"));
                thirdData.token = allJson.optString("access_token");
                thirdData.refreshToken = allJson.optString("refresh_token");
                thirdData.utime = allJson.optLong("expires_in") * 1000;
                thirdData.unionid = allJson.optString("unionid");
                mPresenter.getData(ApiConfig.POST_WE_CHAT_LOGIN_INFO, LoadTypeConfig.NORMAL, thirdData);
                break;
        }
    }

    @Override
    public void initListener() {
        super.initListener();
        //点击了注册账号,跳转到注册页面
        registerPress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                finish();
            }
        });

        //点击 X 图片
        closeLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //取消登录时如果上个页面有传过来数据就跳转到首页，没有传过来数据就直接返回上个页面
                if (!TextUtils.isEmpty(intent.getStringExtra(getApplicationContext().getString(R.string.activity_name)))) {
                    String string = getApplicationContext().getString(R.string.activity_name);
                    startActivity(new Intent(LoginActivity.this, MyHomeActivity.class));
                }
                finish();
            }
        });
    }

    //登陆成功如果上个页面有传过来数据就跳转到首页，没有传过来数据就直接让他返回到上个页面
    private void jump() {

        if (!TextUtils.isEmpty(getIntent().getStringExtra(getApplicationContext().getString(R.string.activity_name)))) {
            if (SharedPrefrenceUtils.getObject(this, ConstantKey.IS_SELECTDE) != null) {
                startActivity(new Intent(LoginActivity.this, MyHomeActivity.class));
            } else {
                startActivity(new Intent(this, SpecialtyActivity.class));
            }
        }
        finish();
    }

    private void goTime() {
        mSubscribe = Observable.interval(1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(goTime -> {
                    mLoginView.tvSecurityGetAuthcode.setText(time - goTime + "s");
                    if (time - goTime < 1) {
                        doPre();
                        mLoginView.tvSecurityGetAuthcode.setText("获取验证码");
                    }
                });
    }

    private void doPre() {
        if (mSubscribe != null && !mSubscribe.isDisposed()) mSubscribe.dispose();
    }


    @Override
    public void sendVerifyCode(String phoneNum) {
        this.phoneNum = phoneNum;
        mPresenter.getData(ApiConfig.SEND_VERIFY, LoadTypeConfig.NORMAL, phoneNum);
    }

    //登录
    @Override
    public void loginPress(int type, String userName, String pwd) {
        doPre();
        //点击验证码登录
        if (mLoginView.mCurrentLoginType == mLoginView.SECURITY_TYPE)
            mPresenter.getData(ApiConfig.VERIFY_LOGIN, LoadTypeConfig.NORMAL, userName, pwd);
        else mPresenter.getData(ApiConfig.ACCOUNT_LOGIN, LoadTypeConfig.NORMAL, userName, pwd);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        doPre();
    }

    @OnClick({R.id.login_by_qq, R.id.login_by_wx})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login_by_qq:
                break;
            case R.id.login_by_wx:
                doWechatLogin();
                break;
        }
    }

    private void doWechatLogin() {
        WXEntryActivity.setOnWeChatLoginResultListener(it -> {
            int errorCode = it.getIntExtra("errorCode", 0);
            String normalCode = it.getStringExtra("normalCode");
            switch (errorCode) {
                case 0:
                    showLog("用户已同意微信登录");
                    mPresenter.getData(ApiConfig.GET_WE_CHAT_TOKEN, LoadTypeConfig.NORMAL, normalCode);
                    break;
                case -4:
                    showToast("用户拒绝授权");
                    break;
                case -2:
                    showToast("用户取消");
                    break;

            }
        });
        IWXAPI weChatApi = WXAPIFactory.createWXAPI(this, null);
        weChatApi.registerApp(ConstantKey.WX_APP_ID);
        if (weChatApi.isWXAppInstalled()) {
            doWeChatLogin();
        } else showToast("请先安装微信");
    }

    private void doWeChatLogin() {
        SendAuth.Req request = new SendAuth.Req();
//        snsapi_base 和snsapi_userinfo  静态获取和同意后获取
        request.scope = "snsapi_userinfo";
        request.state = "com.zhulong.eduvideo";
        IWXAPI weChatApi = WXAPIFactory.createWXAPI(this, ConstantKey.WX_APP_ID);
        weChatApi.sendReq(request);
    }
}
