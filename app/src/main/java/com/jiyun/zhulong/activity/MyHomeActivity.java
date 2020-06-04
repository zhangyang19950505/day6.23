package com.jiyun.zhulong.activity;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.jiyun.frame.mvp.ICommonModel;
import com.jiyun.zhulong.R;
import com.jiyun.zhulong.base.BaseMvpActiviy;

public class MyHomeActivity extends BaseMvpActiviy {

    private NavController navController;

    @Override
    protected int setLayout() {
        return R.layout.activity_my_home;
    }

    @Override
    protected ICommonModel setModel() {
        return null;
    }

    @Override
    protected void initView() {
        navController = Navigation.findNavController(this, R.id.project_fragment_control);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onSuccess(int apiConfig, int loadTypeConfig, Object[] objects) {

    }
}