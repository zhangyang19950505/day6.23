package com.jiyun.zhulong.model;

import android.content.Context;

import com.jiyun.frame.api.ApiConfig;
import com.jiyun.frame.context.FrameApplication;
import com.jiyun.frame.interceptor.NetManager;
import com.jiyun.frame.mvp.ICommonModel;
import com.jiyun.frame.mvp.ICommonPresenter;
import com.jiyun.frame.utils.ParamHashMap;
import com.jiyun.zhulong.R;
import com.jiyun.zhulong.base.Application1907;

public class AccountModel implements ICommonModel {
    private NetManager mManager=NetManager.getInstance();
    private Context mContext = Application1907.get07ApplicationContext();

    @Override
    public void getData(ICommonPresenter iCommonPresenter, int apiConfig, int loadTypeConfig, Object[] object) {
        switch (apiConfig){
            case ApiConfig.SEND_VERIFY:
                mManager.netWork(mManager.getService(mContext.getString(R.string.passport_openapi_user)).getLoginVerify((String) object[0]), iCommonPresenter,apiConfig,loadTypeConfig,object);
                break;
            case ApiConfig.VERIFY_LOGIN:
                mManager.netWork(mManager.getService(mContext.getString(R.string.passport_openapi_user)).loginByVerify(new ParamHashMap().add("mobile",object[0]).add("code",object[1])), iCommonPresenter,apiConfig,loadTypeConfig,object);
                break;
            case ApiConfig.GET_HEADER_INFO:
                String uid = FrameApplication.getFrameApplication().getLoginInfo().getUid();
                mManager.netWork(mManager.getService(mContext.getString(R.string.passport_api)).getHeaderInfo(new ParamHashMap().add("zuid",uid).add("uid",uid)), iCommonPresenter,apiConfig,loadTypeConfig,object);
                break;
        }
    }
}
