package com.jiyun.zhulong.model;


import android.util.Log;

import com.jiyun.bean.Device;
import com.jiyun.bean.LoginInfo;
import com.jiyun.frame.ApiConfig;
import com.jiyun.frame.FrameApplication;
import com.jiyun.frame.ICommonModel;
import com.jiyun.frame.ICommonPresenter;
import com.jiyun.frame.NetManager;

import java.util.Map;

public class TestModel implements ICommonModel {
    NetManager mManager = NetManager.getInstance();

    @Override
    public void getData(ICommonPresenter iCommonPresenter, int apiConfig, int loadTypeConfig, Object[] object) {
        switch (apiConfig) {
            case ApiConfig.ONE_TEST_GET:
                if (object != null && object.length == 2) {
                    int page = (int) object[0];
                    Map<String, Object> params = (Map<String, Object>) object[1];
                    mManager.netWork(mManager.getService("http://static.owspace.com/").getFuliData(params, page), iCommonPresenter, apiConfig, loadTypeConfig);
                } else if (object != null && object.length == 1) {
                    //获取引导页面广告的数据
                    Map<String, Object> map = (Map<String, Object>) object[0];
                    LoginInfo loginInfo = new LoginInfo();
                    loginInfo.setUid("1");
                    Device device = new Device();
                    device.setDeviceName("aa");
                    FrameApplication.getFrameApplication().setLoginInfo(loginInfo);
                    FrameApplication.getFrameApplication().setDeviceInfo(device);
                    mManager.netWork(mManager.getService().getLeadData(map), iCommonPresenter, apiConfig, loadTypeConfig);
                }else {
                    Log.e("111", "object: 没有相对应的参数个数" );
                }
                break;
            case ApiConfig.TWO_TEST_GET:

                break;
            default:
        }

    }
}
