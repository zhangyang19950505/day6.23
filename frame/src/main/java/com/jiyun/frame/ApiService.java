package com.jiyun.frame;

import com.jiyun.bean.FuliBean;
import com.jiyun.bean.LeadBean;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface ApiService {
    @GET(".")
    //c=api&a=getList&page_id=0
    Observable<FuliBean> getFuliData(@QueryMap Map<String, Object> params, @Query("page_id") int pageId);



    @GET("openapi/ad/getAd?positions_id=APP_QD_01&is_show=0&w=1080&h=2160&specialty_id=21")
    Observable<LeadBean> getLeadData();

}
