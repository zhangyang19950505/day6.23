package com.jiyun.zhulong.base;

import android.os.Bundle;
import android.text.TextUtils;

import androidx.annotation.Nullable;


import com.jiyun.frame.CommonPresenter;
import com.jiyun.frame.ICommonModel;
import com.jiyun.frame.ICommonView;

import butterknife.ButterKnife;

public abstract class BaseMvpActiviy<M extends ICommonModel> extends BaseActivity implements ICommonView {
    private M mModel;
    public CommonPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayout());
        ButterKnife.bind(this);
        mModel = setModel();
        if (mModel != null)
            mPresenter = new CommonPresenter(this, mModel);
        initView();
        initData();
        initListener();
    }

    protected abstract int setLayout();

    protected abstract M setModel();

    protected abstract void initView();

    protected abstract void initData();

    public void initListener() {
    }

    protected abstract void onSuccess(int apiConfig, int loadTypeConfig, Object[] objects);

    public void onFailed(int apiConfig, Throwable throwable) {

    }

    @Override
    public void netSuccess(int apiConfig, int loadTypeConfig, Object... object) {
        onSuccess(apiConfig, loadTypeConfig, object);
    }

    @Override
    public void netFailed(int apiConfig, Throwable throwable) {
        showLog("错误：" + apiConfig + "，error content" + throwable != null && !TextUtils.isEmpty(throwable.getMessage()) ? throwable.getMessage() : "不明错误类型");
        onFailed(apiConfig, throwable);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.clear();
    }
}
