package com.jiyun.zhulong.fragment;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.jiyun.course.adapter.CourseVpAdapter;
import com.jiyun.course.fragment.ChildCourseFragment;
import com.jiyun.frame.mvp.ICommonModel;
import com.jiyun.zhulong.R;
import com.jiyun.zhulong.base.BaseMvpFragment;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 作者：dell  张扬
 * 创建于： 2020/5/31 03:21
 * 作者邮箱：1214476635@qq.com
 */
public class CourseFragment extends BaseMvpFragment {
    @BindView(R.id.tab_course)
    TabLayout tabCourse;
    @BindView(R.id.vp_course)
    ViewPager vpCourse;

    @Override
    protected int setLayout() {
        return R.layout.fragment_course;
    }

    @Override
    protected ICommonModel setModel() {
        return null;
    }

    @Override
    protected void initView() {
        ArrayList<String> tabTitles = new ArrayList<>();
        tabTitles.add("训练营");
        tabTitles.add("精品课");
        tabTitles.add("公开课");
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(ChildCourseFragment.getInstance(3));
        fragments.add(ChildCourseFragment.getInstance(1));
        fragments.add(ChildCourseFragment.getInstance(2));
        tabCourse.setupWithViewPager(vpCourse);
        CourseVpAdapter myFragmentPagerAdapter = new CourseVpAdapter(getChildFragmentManager(), tabTitles, fragments);
        vpCourse.setAdapter(myFragmentPagerAdapter);

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onSuccess(int apiConfig, int loadTypeConfig, Object[] object) {

    }
}
