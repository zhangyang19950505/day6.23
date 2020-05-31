package com.jiyun.frame.utils;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

/**
 * 作者：dell  张扬
 * 创建于： 2020/5/31 08:47
 * 作者邮箱：1214476635@qq.com
 */
public class TabLayoutUtil {

    public TabLayoutUtil(){}

    public static volatile TabLayoutUtil tabLayoutUtil;

    public static TabLayoutUtil getInstance(){
        if (tabLayoutUtil==null){
            synchronized (TabLayoutUtil.class){
                tabLayoutUtil=new TabLayoutUtil();
            }
        }
        return tabLayoutUtil;
    }

    //tablayout结合framelayout的二连动时使用
    public void TabAddFrameLayout(int containerID, FragmentManager manager, TabLayout tab, ArrayList<String> tabTitles, ArrayList<Fragment>fragments, ArrayList<Integer> iconID){
        FragmentTransaction begin = manager.beginTransaction();
        if (iconID ==null&& iconID.size()==0) {
            for (int i = 0; i < tabTitles.size(); i++) {
                tab.addTab(tab.newTab().setText(tabTitles.get(i)));
                begin.add(containerID,fragments.get(i)).hide(fragments.get(i));
            }
            begin.show(fragments.get(0));
        }else {
            for (int i = 0; i < tabTitles.size(); i++) {
                tab.addTab(tab.newTab().setText(tabTitles.get(i)).setIcon(iconID.get(i)));
                begin.add(containerID,fragments.get(i)).hide(fragments.get(i));
            }
            begin.show(fragments.get(0));
        }
        begin.commit();
    }

    //tablayout结合viewpager实现三联动时使用
    public void TabAddViewPager(TabLayout tab, ViewPager vp,ArrayList<String>tabTitles,ArrayList<Fragment>fragments,ArrayList<Integer>IconID){
        tab.setupWithViewPager(vp);
        if (IconID==null&&IconID.size()==0){
            for (int i = 0; i < fragments.size(); i++) {
                tab.getTabAt(i).setText(tabTitles.get(i));
            }
        }else {
            for (int i = 0; i < fragments.size(); i++) {
                tab.getTabAt(i).setText(tabTitles.get(i)).setIcon(IconID.get(i));
            }
        }
    }


    //点击显示隐藏
    public void TabListener(TabLayout tab,FragmentManager manager,ArrayList<Fragment> fragments){
        tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                FragmentTransaction begin = manager.beginTransaction();
                for (int i = 0; i < fragments.size(); i++) {
                    begin.hide(fragments.get(i));
                }
                for (int i = 0; i < fragments.size(); i++) {
                    if (tab.getPosition()==i){
                        begin.show(fragments.get(i));
                    }
                }
                begin.commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
