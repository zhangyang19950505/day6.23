package com.jiyun.fuli.activity;


import androidx.recyclerview.widget.RecyclerView;

import com.jiyun.frame.bean.FuliBean;
import com.jiyun.frame.api.ApiConfig;
import com.jiyun.frame.mvp.ICommonModel;
import com.jiyun.frame.api.LoadTypeConfig;
import com.jiyun.frame.utils.ParamHashMap;
import com.jiyun.fuli.adapter.FuliRvAdapter;
import com.jiyun.zhulong.R;
import com.jiyun.zhulong.base.BaseMvpActiviy;
import com.jiyun.zhulong.interfaces.DataListener;
import com.jiyun.zhulong.model.TestModel;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class FuliActivity extends BaseMvpActiviy {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    private FuliRvAdapter adapter;
    private int pageId = 0;
    private Map<String, Object> map;

    @Override
    protected int setLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected ICommonModel setModel() {
        return new TestModel();
    }

    @Override
    protected void initView() {
        map = new ParamHashMap().add("c", "api").add("a", "getList");
        initRecyclerView(mRecyclerView);
        adapter = new FuliRvAdapter(this);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void initListener() {
        super.initListener();
        initSmartListener(mRefreshLayout, new DataListener() {
            @Override
            public void dataType(int loadTypeConfig) {
                if (loadTypeConfig == LoadTypeConfig.LOADMORE) {
                    pageId++;
                    mPresenter.getData(ApiConfig.LEAD_URL, LoadTypeConfig.LOADMORE, pageId, map);
                }
                if (loadTypeConfig == LoadTypeConfig.REFRESH) {
                    pageId=0;
                    adapter.getDatas().clear();
                    mPresenter.getData(ApiConfig.LEAD_URL, LoadTypeConfig.REFRESH, pageId, map);
                }
            }
        });
    }

    @Override
    protected void initData() {
        mPresenter.getData(ApiConfig.LEAD_URL, LoadTypeConfig.NORMAL, pageId, map);
    }

    @Override
    protected void onSuccess(int apiConfig, int loadType, Object[] objects) {
        switch (apiConfig) {
            case ApiConfig.LEAD_URL:
                if (loadType == LoadTypeConfig.LOADMORE) {
                    mRefreshLayout.finishLoadMore();
                } else if (loadType == LoadTypeConfig.REFRESH) {
                    mRefreshLayout.finishRefresh();
                }
                List<FuliBean.DatasBean> datas = ((FuliBean) objects[0]).getDatas();
                adapter.initData(datas);
                break;
        }
    }
}
