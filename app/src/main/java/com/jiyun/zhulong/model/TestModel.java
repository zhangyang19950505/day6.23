package com.jiyun.zhulong.model;


import android.content.Context;
import android.util.Log;

import com.jiyun.frame.bean.LoginInfo;
import com.jiyun.frame.api.ApiConfig;
import com.jiyun.frame.mvp.ICommonModel;
import com.jiyun.frame.mvp.ICommonPresenter;
import com.jiyun.frame.interceptor.NetManager;
import com.jiyun.zhulong.R;
import com.jiyun.zhulong.base.Application1907;

import java.util.Map;

public class TestModel implements ICommonModel {
    NetManager mManager = NetManager.getInstance();
    private Context context= Application1907.get07ApplicationContext();

    @Override
    public void getData(ICommonPresenter iCommonPresenter, int apiConfig, int loadTypeConfig, Object[] object) {
        switch (apiConfig) {
            case ApiConfig.LEAD_URL:
                if (object.length == 2) {
                    int page = (int) object[0];
                    Map<String, Object> params = (Map<String, Object>) object[1];
                    mManager.netWork(mManager.getService().getFuliData(params, page), iCommonPresenter, apiConfig, loadTypeConfig);
                } else if (object.length == 1) {
                    //获取引导页面广告的数据
                    Map<String, Object> map = (Map<String, Object>) object[0];
                    mManager.netWork(mManager.getService(context.getString(R.string.ad_openapi)).getLeadData(map), iCommonPresenter, apiConfig, loadTypeConfig);
                }else {
                    Log.e("错误：", "错误原因: 没有相同参数的接口" );
                }
                break;
            case ApiConfig.SPECIALTY_URL:

                break;
            default:
        }

    }
}
