package com.jiyun.zhulong.fragment;


import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.jiyun.bean.VIPBannerBean;
import com.jiyun.bean.VIPBottomDataBean;
import com.jiyun.frame.api.ApiConfig;
import com.jiyun.frame.api.LoadTypeConfig;
import com.jiyun.frame.bean.SpecialtyBean;
import com.jiyun.frame.constants.ConstantKey;
import com.jiyun.frame.mvp.ICommonModel;
import com.jiyun.frame.utils.ParamHashMap;
import com.jiyun.vip.adapter.BottomRvAdapter;
import com.jiyun.vip.adapter.HoriaontalRvAdapter;
import com.jiyun.zhulong.R;
import com.jiyun.zhulong.base.BaseMvpFragment;
import com.jiyun.zhulong.interfaces.DataListener;
import com.jiyun.zhulong.model.CourseModel;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.yiyatech.utils.newAdd.SharedPrefrenceUtils;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 作者：dell  张扬
 * 创建于： 2020/5/31 03:21
 * 作者邮箱：1214476635@qq.com
 */
public class VIPFragment extends BaseMvpFragment {
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.rv_horiaontal)
    RecyclerView rvHoriaontal;
    @BindView(R.id.rv_bottom)
    RecyclerView rvBottom;
    private SpecialtyBean.ResultBean.DataBean dataBean;
    private int page = 1;
    private ParamHashMap map;
    private String specialty_id;
    private List<String> imgs = new ArrayList<>();
    private HoriaontalRvAdapter horiaontalRvAdapter;
    private BottomRvAdapter bottomRvAdapter;

    @Override
    protected int setLayout() {
        return R.layout.fragment_vip;
    }

    @Override
    protected ICommonModel setModel() {
        return new CourseModel();
    }

    @Override
    protected void initView(View view) {
        if (SharedPrefrenceUtils.getObject(getActivity(), ConstantKey.IS_SELECTDE) != null) {
            dataBean = SharedPrefrenceUtils.getObject(getActivity(), ConstantKey.IS_SELECTDE);
            specialty_id = dataBean.getSpecialty_id();
        }
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvHoriaontal.setLayoutManager(manager);
        horiaontalRvAdapter = new HoriaontalRvAdapter(getActivity());
        rvHoriaontal.setAdapter(horiaontalRvAdapter);

        initRecyclerView(rvBottom);
        rvBottom.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        bottomRvAdapter = new BottomRvAdapter(getActivity());
        rvBottom.setAdapter(bottomRvAdapter);

    }

    @Override
    protected void initData() {
        initBannerData();
        initBottomData();
    }

    private void initBottomData() {
        map = new ParamHashMap().add("specialty_id", specialty_id).add("page", page);
        mPresenter.getData(ApiConfig.VIP_BOTTOM_DATA_INFO, LoadTypeConfig.NORMAL, map);
    }

    private void initBannerData() {
        mPresenter.getData(ApiConfig.VIP_BANNER_DATA_INFO, LoadTypeConfig.NORMAL);
    }

    @Override
    protected void onSuccess(int apiConfig, int loadTypeConfig, Object[] object) {
        switch (apiConfig) {
            case ApiConfig.VIP_BANNER_DATA_INFO:
                if (((VIPBannerBean) object[0]).getResult() != null) {
                    VIPBannerBean vipBannerBean = (VIPBannerBean) object[0];
                    VIPBannerBean.ResultBean result = vipBannerBean.getResult();
                    List<VIPBannerBean.ResultBean.LiveBeanX.LiveBean> live = result.getLive().getLive();
                    horiaontalRvAdapter.initData(live);
                    List<VIPBannerBean.ResultBean.LunbotuBean> lunbotu = result.getLunbotu();
                    for (VIPBannerBean.ResultBean.LunbotuBean lunbotuBean : lunbotu) {
                        imgs.add(lunbotuBean.getImg());
                    }
                    banner.setImages(imgs)
                            .setDelayTime(5000)
                            .isAutoPlay(true)
                            .setImageLoader(new ImageLoader() {
                                @Override
                                public void displayImage(Context context, Object path, ImageView imageView) {
                                    Glide.with(context).load(path).into(imageView);
                                }
                            }).start();
                }
                break;
            case ApiConfig.VIP_BOTTOM_DATA_INFO:
                if (((VIPBottomDataBean) object[0]).getResult() != null) {
                    if (loadTypeConfig == LoadTypeConfig.LOADMORE) {
                        refreshLayout.finishLoadMore();
                    } else if (loadTypeConfig == LoadTypeConfig.REFRESH) {
                        if (bottomRvAdapter.getList().size() > 0) {
                            bottomRvAdapter.getList().clear();
                        }
                        refreshLayout.finishRefresh();
                    }
                    VIPBottomDataBean vipBottomDataBean = (VIPBottomDataBean) object[0];
                    List<VIPBottomDataBean.ResultBean.ListBean> list = vipBottomDataBean.getResult().getList();
                    bottomRvAdapter.initData(list);
                }
                break;
        }
    }

    //上拉加载，下拉刷新
    @Override
    public void initListener() {
        super.initListener();
        setSmartListener(refreshLayout, new DataListener() {
            @Override
            public void dataType(int loadTypeConfig) {
                if (loadTypeConfig == LoadTypeConfig.LOADMORE) {
                    page++;
                    map = new ParamHashMap().add("specialty_id", specialty_id).add("page", page);
                    mPresenter.getData(ApiConfig.VIP_BOTTOM_DATA_INFO, LoadTypeConfig.LOADMORE, map);
                }
                if (loadTypeConfig == LoadTypeConfig.REFRESH) {
                    page = 1;
                    map = new ParamHashMap().add("specialty_id", specialty_id).add("page", page);
                    mPresenter.getData(ApiConfig.VIP_BOTTOM_DATA_INFO, LoadTypeConfig.REFRESH, map);
                }
            }
        });
    }

    //由于涉及到选择专业的业务，当选择了专业需要及时更新数据所以在onResume生命周期方法中再次请求数据
    @Override
    public void onResume() {
        super.onResume();
        if (SharedPrefrenceUtils.getObject(getActivity(), ConstantKey.IS_SELECTDE) != null) {
            SpecialtyBean.ResultBean.DataBean dataBean = SharedPrefrenceUtils.getObject(getActivity(), ConstantKey.IS_SELECTDE);
            if (this.dataBean.getSpecialty_id().equals(dataBean.getSpecialty_id())) {
                return;
            } else {
                specialty_id = dataBean.getSpecialty_id();
                page = 1;
                bottomRvAdapter.getList().clear();
                map = new ParamHashMap().add("specialty_id", specialty_id).add("page", page);
                mPresenter.getData(ApiConfig.VIP_BOTTOM_DATA_INFO, LoadTypeConfig.NORMAL, map);
            }
        }
    }
}
