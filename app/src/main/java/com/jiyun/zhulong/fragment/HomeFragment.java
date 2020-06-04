package com.jiyun.zhulong.fragment;


import com.jiyun.frame.mvp.ICommonModel;
import com.jiyun.zhulong.R;
import com.jiyun.zhulong.base.BaseMvpFragment;
import com.jiyun.zhulong.design.BottomTabView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;

/**
 * 作者：dell  张扬
 * 创建于： 2020/5/31 03:21
 * 作者邮箱：1214476635@qq.com
 */
public class HomeFragment extends BaseMvpFragment {
    @BindView(R.id.bottom_tab)
    BottomTabView bottomTab;
    private List<Integer>unSelectedIcon=new ArrayList<>();
    private List<Integer>selectedIcon=new ArrayList<>();
    private List<String>tabText=new ArrayList<>();

    @Override
    protected int setLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected ICommonModel setModel() {
        return null;
    }

    @Override
    protected void initView() {
        Collections.addAll(unSelectedIcon,R.mipmap.ic_home_unselect_new,R.mipmap.ic_course_unselect_new,R.mipmap.vip11,R.mipmap.tab_data_normal,R.mipmap.ic_mine_unselect_new);
        Collections.addAll(selectedIcon,R.mipmap.ic_home_select_new,R.mipmap.ic_course_select_new,R.mipmap.vip,R.mipmap.tab_data_select,R.mipmap.ic_mine_select_new);
        Collections.addAll(tabText,"主页","课程","VIP","资料","我的");
        bottomTab.setTabContent(tabText,unSelectedIcon,selectedIcon,5,1);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onSuccess(int apiConfig, int loadTypeConfig, Object[] object) {

    }
}
