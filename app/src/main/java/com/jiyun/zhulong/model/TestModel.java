package com.jiyun.zhulong.model;


import com.jiyun.frame.ApiConfig;
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
                if (object == null || object.length ==0) {
                    //获取引导页面广告的数据
                    mManager.netWork(mManager.getService("https://a.zhulong.com/").getLeadData(), iCommonPresenter, apiConfig, loadTypeConfig);
                } else if (object != null && object.length == 2) {
                    int page = (int) object[0];
                    Map<String, Object> params = (Map<String, Object>) object[1];
                    mManager.netWork(mManager.getService().getFuliData(params, page), iCommonPresenter, apiConfig, loadTypeConfig);
                }
                break;
            case ApiConfig.TWO_TEST_GET:

                break;
        }

    }
}
