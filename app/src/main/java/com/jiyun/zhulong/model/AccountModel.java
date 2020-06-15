package com.jiyun.zhulong.model;

import android.content.Context;

import com.jiyun.bean.ThirdLoginData;
import com.jiyun.frame.api.ApiConfig;
import com.jiyun.frame.constants.ConstantKey;
import com.jiyun.frame.context.FrameApplication;
import com.jiyun.frame.interceptor.NetManager;
import com.jiyun.frame.mvp.ICommonModel;
import com.jiyun.frame.mvp.ICommonPresenter;
import com.jiyun.frame.utils.ParamHashMap;
import com.jiyun.frame.utils.RsaUtil;
import com.jiyun.zhulong.R;
import com.jiyun.zhulong.base.Application1907;

import java.lang.reflect.Method;
import java.util.Map;

public class AccountModel implements ICommonModel {
    private NetManager mManager = NetManager.getInstance();
    private Context mContext = Application1907.get07ApplicationContext();

    @Override
    public void getData(ICommonPresenter iCommonPresenter, int apiConfig, int loadTypeConfig, Object[] object) {
        switch (apiConfig) {
            case ApiConfig.SEND_VERIFY:
                mManager.netWork(mManager.getService(mContext.getString(R.string.passport_openapi_user)).getLoginVerify((String) object[0]), iCommonPresenter, apiConfig, loadTypeConfig);
                break;
            case ApiConfig.VERIFY_LOGIN:
                mManager.netWork(mManager.getService(mContext.getString(R.string.passport_openapi_user)).loginByVerify(new ParamHashMap().add("mobile", object[0]).add("code", object[1])), iCommonPresenter, apiConfig, loadTypeConfig);
                break;
            case ApiConfig.GET_HEADER_INFO:
                String uid = FrameApplication.getFrameApplication().getLoginInfo().getUid();
                mManager.netWork(mManager.getService(mContext.getString(R.string.passport_api)).getHeaderInfo(new ParamHashMap().add("zuid", uid).add("uid", uid)), iCommonPresenter, apiConfig, loadTypeConfig);
                break;
            //账号登录
            case ApiConfig.ACCOUNT_LOGIN:
                ParamHashMap add = new ParamHashMap().add("ZLSessionID", "").add("seccode", "").add("loginName", object[0])
                        .add("passwd", RsaUtil.encryptByPublic((String) object[1])).add("cookieday", "")
                        .add("fromUrl", "android").add("ignoreMobile", "0");
                mManager.netWork(mManager.getService(mContext.getString(R.string.passport_openapi)).accountLogin(add), iCommonPresenter, apiConfig, loadTypeConfig);
                break;
            //注册时验证手机号是否已经注册了
            case ApiConfig.VERIFY_PHONE_ISREGISTER:
                mManager.netWork(mManager.getService(mContext.getString(R.string.passport_api)).verifyPhoneisRegister(object[0]), iCommonPresenter, apiConfig, loadTypeConfig);
                break;
            //发送验证码
            case ApiConfig.SEND_REGISTER_VERIFY:
                mManager.netWork(mManager.getService(mContext.getString(R.string.passport_api)).sendRegisterVerify(object[0]), iCommonPresenter, apiConfig, loadTypeConfig);
                break;
            //注册手机号
            case ApiConfig.REGISTER_PHONE:
                Map<String, Object> map = (Map<String, Object>) object[0];
                mManager.netWork(mManager.getService(mContext.getString(R.string.passport_api)).checkVerifyCode(map), iCommonPresenter, apiConfig, loadTypeConfig);
                break;
            //注册用户名
            case ApiConfig.NET_CHECK_USERNAME:
                mManager.netWork(mManager.getService(mContext.getString(R.string.passport)).checkName(object[0]), iCommonPresenter, apiConfig, loadTypeConfig);
                break;
            //注册账号
            case ApiConfig.COMPLETE_REGISTER_WITH_SUBJECT:
                ParamHashMap param = new ParamHashMap().add("username", object[0]).add("password", RsaUtil.encryptByPublic((String) object[1]))
                        .add("tel", object[2]).add("specialty_id", FrameApplication.getFrameApplication().getSelectedInfo().getSpecialty_id())
                        .add("province_id", 0).add("city_id", 0).add("sex", 0).add("from_reg_name", 0).add("from_reg", 0);
                mManager.netWork(mManager.getService(mContext.getString(R.string.passport_api)).registerCompleteWithSubject(param), iCommonPresenter, apiConfig, loadTypeConfig);
                break;
            case ApiConfig.GET_WE_CHAT_TOKEN:
                ParamHashMap wxParams = new ParamHashMap().add("appid", ConstantKey.WX_APP_ID).add("secret", ConstantKey.WX_APP_SECRET).add("code", object[0]).add("grant_type", "authorization_code");
                mManager.netWork(mManager.getService(mContext.getString(R.string.WX_OAUTH)).getWechatToken(wxParams), iCommonPresenter, apiConfig, loadTypeConfig);
                break;
            case ApiConfig.POST_WE_CHAT_LOGIN_INFO:
                ThirdLoginData data = (ThirdLoginData) object[0];
                ParamHashMap add1 = new ParamHashMap().add("openid", data.openid).add("type", data.type).add("url", "android");
                mManager.netWork(mManager.getService(mContext.getString(R.string.passport_api)).loginByWechat(add1), iCommonPresenter, apiConfig, loadTypeConfig);
                break;
        }
    }
}
