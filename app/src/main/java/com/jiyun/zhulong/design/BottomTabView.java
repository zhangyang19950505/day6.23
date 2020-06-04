package com.jiyun.zhulong.design;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jiyun.zhulong.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者：dell  张扬
 * 创建于： 2020/6/4 18:47
 * 作者邮箱：1214476635@qq.com
 */
public class BottomTabView extends RelativeLayout {
    @BindView(R.id.tab_one)
    TextView tabOne;
    @BindView(R.id.tab_two)
    TextView tabTwo;
    @BindView(R.id.tab_three)
    TextView tabThree;
    @BindView(R.id.tab_fore)
    TextView tabFore;
    @BindView(R.id.tab_five)
    TextView tabFive;
    private Context mContext;
    private final int unSelectedColor;//未选中的颜色
    private final int selectedColor;//选中的颜色
    private int tabCount;//tab的数量
    private List<TextView> allTabView = new ArrayList<>();//所有tab的集合
    private List<Integer> selectedIcon =new ArrayList<>();//选中的图片集合
    private List<Integer> unSelectedIcon =new ArrayList<>();//未选中的图片集合
    private List<String> tabText=new ArrayList<>();//tab中的内容集合
    private int defaultShowTab=1;//默认显示的是第几个tab

    public BottomTabView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.bottom_tab_view, this);
        ButterKnife.bind(this);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BottomTabView, 0, 0);
        //获取默认的数量，未选中的颜色，选中后的颜色
        tabCount = typedArray.getInt(R.styleable.BottomTabView_tabCount, 5);
        unSelectedColor = typedArray.getColor(R.styleable.BottomTabView_unSelectedColor, getContext().getColor(R.color.gray));
        selectedColor = typedArray.getColor(R.styleable.BottomTabView_selectedColor, getContext().getColor(R.color.red423a));
        Collections.addAll(allTabView, tabOne, tabTwo, tabThree, tabFore, tabFive);
        if (tabCount<5){
            for (int i = 5; i >tabCount; i--) {
                allTabView.get(i-1).setVisibility(GONE);
                allTabView.remove(i-1);
            }
        }
    }

    /**
     *调用此方法给tab设置内容
     * @param tabText tab 的text
     * @param unSelectedIcon 未选中的图片集合
     * @param selectedIcon 选中的图片集合
     * @param tabCount 要显示的tab数量
     * @param defaultShowTab 默认显示的是第几个tab
     */
    public void setTabContent(List<String> tabText, List<Integer> unSelectedIcon, List<Integer> selectedIcon, int tabCount, int defaultShowTab){
        if (defaultShowTab<=0){
            Toast.makeText(mContext, "一个都不选中？", Toast.LENGTH_SHORT).show();
        }
        if (allTabView.size()!= unSelectedIcon.size()||allTabView.size()!= selectedIcon.size()||allTabView.size()!=tabText.size()){
            Toast.makeText(mContext, "传递的数量不一致", Toast.LENGTH_SHORT).show();
        }
        if (tabCount<=0){
            Toast.makeText(mContext, "起码给我一个tab数量把", Toast.LENGTH_SHORT).show();
        }
        this.tabCount=tabCount;this.tabText=tabText;this.selectedIcon = selectedIcon;this.unSelectedIcon = unSelectedIcon;this.defaultShowTab=defaultShowTab;
        for (int i = 0; i < tabText.size(); i++) {
            allTabView.get(i).setText(tabText.get(i));
        }
        setStyle();
    }

    private void setStyle() {
        for (int i = 0; i < allTabView.size(); i++) {
            if (i==defaultShowTab-1){
                allTabView.get(i).setTextColor(selectedColor);
                allTabView.get(i).setCompoundDrawablesWithIntrinsicBounds(0,selectedIcon.get(i),0,0);
            }else {
                allTabView.get(i).setTextColor(unSelectedColor);
                allTabView.get(i).setCompoundDrawablesWithIntrinsicBounds(0,unSelectedIcon.get(i),0,0);
            }
        }
    }

    @OnClick({R.id.tab_one, R.id.tab_two, R.id.tab_three, R.id.tab_fore, R.id.tab_five})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.tab_one) {
            defaultShowTab = 1;
        } else if (id == R.id.tab_two) {
            defaultShowTab = 2;
        } else if (id == R.id.tab_three) {
            defaultShowTab = 3;
        } else if (id == R.id.tab_fore) {
            defaultShowTab = 4;
        } else if (id == R.id.tab_five) {
            defaultShowTab = 5;
        }
        setStyle();
    }
}
