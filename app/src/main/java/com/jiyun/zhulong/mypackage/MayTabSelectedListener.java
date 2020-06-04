package com.jiyun.zhulong.mypackage;

import com.google.android.material.tabs.TabLayout;

/**
 * 作者：dell  张扬
 * 创建于： 2020/6/4 18:40
 * 作者邮箱：1214476635@qq.com
 */
//将tablayout监听的3个重写方法抽为一个方法
public abstract class MayTabSelectedListener implements TabLayout.BaseOnTabSelectedListener {
    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        onMayTabSelected(tab);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    public abstract void onMayTabSelected(TabLayout.Tab tab);
}