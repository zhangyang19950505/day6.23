package com.jiyun.zhulong.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.jiyun.bean.LeadBean;
import com.jiyun.frame.ApiConfig;
import com.jiyun.frame.ICommonModel;
import com.jiyun.frame.LoadTypeConfig;
import com.jiyun.zhulong.R;
import com.jiyun.zhulong.base.BaseMvpActiviy;
import com.jiyun.zhulong.model.TestModel;

import butterknife.BindView;

public class MainActivity extends BaseMvpActiviy {

    @BindView(R.id.bt_home_time)
    Button mHomeTimeBt;
    @BindView(R.id.img_home_top)
    ImageView mHomeTopImg;
    @BindView(R.id.img_home_bottom)
    ImageView mHomeBottomImg;
    private int time = 7;
    private boolean isClick;
    private boolean isDestroy;
    private int widthPixels;
    private int heightPixels;
    private String jump_url;
    private String info_url;

    Handler handler = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(@NonNull Message message) {


            int what = message.what;
            if (what == 0) {
                mHomeTimeBt.setText("跳过" + time);
                time--;
                if (time < 5) {
                    mHomeTimeBt.setVisibility(View.VISIBLE);
                    mHomeBottomImg.setVisibility(View.VISIBLE);
                    getScreenWH();
                    mHomeTopImg.setMaxHeight(heightPixels - 100);
                    //如果本界面已经销毁不再加载
                    if (!isDestroy) {
                        Glide.with(MainActivity.this).load(info_url).into(mHomeTopImg);
                    }
                    if (time == -1) {
                        startActivity(new Intent(MainActivity.this, HomeActivity.class));
                        finish();
                    }
                }
            }
            return false;
        }
    });

    //在本页面销毁时给isDestroy设为true，用来在销毁时将加载资源的功能停止掉
    @Override
    protected void onDestroy() {
        super.onDestroy();
        isDestroy = true;
    }

    //获取屏幕的宽和高
    private void getScreenWH() {
        WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
        Display defaultDisplay = manager.getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        defaultDisplay.getMetrics(displayMetrics);
        heightPixels = displayMetrics.heightPixels;
        widthPixels = displayMetrics.widthPixels;
    }

    @Override
    public void initListener() {
        super.initListener();
        //广告点击
        mHomeTopImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //在广告没有加载到imageview上时，不予许点击，且只有第一次点击有效
                if (jump_url != null && time < 5 && !isClick) {
                    time = -2;
                    Intent intent = new Intent(getApplicationContext(), WebActivity.class);
                    intent.putExtra("url", jump_url);
                    startActivity(intent);
                    isClick = true;
                    finish();
                }
            }
        });
        //跳转按钮点击
        mHomeTimeBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isClick) {
                    time = -2;
                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                    isClick = true;
                    finish();
                }
            }
        });
    }

    @Override
    protected int setLayout() {
        return R.layout.activity_home_main;
    }

    @Override
    protected ICommonModel setModel() {
        return new TestModel();
    }

    @Override
    protected void initView() {
        Glide.with(this).load(R.mipmap.ic_splash).into(mHomeTopImg);
    }

    @Override
    protected void initData() {
        //获取广告数据
        mPresenter.getData(ApiConfig.ONE_TEST_GET, LoadTypeConfig.NORMAL);

        //启用一个线程来做倒计时
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 8; i++) {
                    try {
                        Thread.sleep(1000);
                        handler.sendEmptyMessage(0);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    //获取数据成功
    @Override
    protected void onSuccess(int apiConfig, int loadTypeConfig, Object[] objects) {
        switch (apiConfig) {
            case ApiConfig.ONE_TEST_GET:
                if (loadTypeConfig == LoadTypeConfig.NORMAL) {
                    LeadBean bean = (LeadBean) objects[0];
                    info_url = bean.getResult().getInfo_url();
                    jump_url = bean.getResult().getJump_url();
                }
                break;
        }
    }
}
