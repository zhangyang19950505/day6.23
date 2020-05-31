package com.jiyun.zhulong.activity;


import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.tabs.TabLayout;
import com.jiyun.frame.ICommonModel;
import com.jiyun.frame.utils.TabLayoutUtil;
import com.jiyun.zhulong.R;
import com.jiyun.zhulong.base.BaseMvpActiviy;
import com.jiyun.zhulong.fragment.CourseFragment;
import com.jiyun.zhulong.fragment.HomeFragment;
import com.jiyun.zhulong.fragment.MineFragment;
import com.jiyun.zhulong.fragment.TabDataFragment;
import com.jiyun.zhulong.fragment.VipFragment;

import java.util.ArrayList;

import butterknife.BindView;

public class HomeActivity extends BaseMvpActiviy {

    @BindView(R.id.tv_career)
    TextView tvCareer;
    @BindView(R.id.ll_career)
    LinearLayout llCareer;
    @BindView(R.id.rl_seek)
    RelativeLayout rlSeek;
    @BindView(R.id.img_home_msg)
    ImageView imgHomeMsg;
    @BindView(R.id.img_qrcode_scan)
    ImageView imgQrcodeScan;
    @BindView(R.id.ll_header)
    LinearLayout headerLl;
    @BindView(R.id.tab)
    TabLayout tab;
    private FragmentManager manager;
    private ArrayList<Fragment> fragments;
    private long exitTime = 0;

    @Override
    protected int setLayout() {
        return R.layout.activity_home;
    }

    @Override
    protected ICommonModel setModel() {
        return null;
    }

    @Override
    protected void initView() {
        ArrayList<String> tabTitles = new ArrayList<>();
        tabTitles.add("主页");
        tabTitles.add("课程");
        tabTitles.add("VIP");
        tabTitles.add("资料");
        tabTitles.add("我的");
        fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new CourseFragment());
        fragments.add(new VipFragment());
        fragments.add(new TabDataFragment());
        fragments.add(new MineFragment());
        ArrayList<Integer> icons = new ArrayList<>();
        icons.add(R.drawable.home_selector);
        icons.add(R.drawable.course_selector);
        icons.add(R.drawable.vip_selector);
        icons.add(R.drawable.data_selector);
        icons.add(R.drawable.mine_selector);
        manager = getSupportFragmentManager();
        TabLayoutUtil.getInstance().TabAddFrameLayout(R.id.fl, manager, tab, tabTitles, fragments, icons);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onSuccess(int apiConfig, int loadTypeConfig, Object[] objects) {

    }

    @Override
    public void initListener() {
        super.initListener();
        tabListener();
    }

    private void tabListener() {
        TabLayoutUtil.getInstance().TabListener(tab, manager, fragments);
        //由于业务需求需要又隐藏显示所以需要再次监听
        tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == fragments.size() - 1) {
                    headerLl.setVisibility(View.GONE);
                } else {
                    headerLl.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    //连按两次返回键回退到桌面
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一下返回桌面",
                        Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                Intent i = new Intent(Intent.ACTION_MAIN);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.addCategory(Intent.CATEGORY_HOME);
                startActivity(i);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
