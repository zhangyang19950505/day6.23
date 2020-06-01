package com.jiyun.frame;

import com.jiyun.bean.FuliBean;
import com.jiyun.bean.LeadBean;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface ApiService {
    @FormUrlEncoded
    @POST(".")
    //c=api&a=getList&page_id=0
    Observable<FuliBean> getFuliData(@FieldMap Map<String, Object> map, @Field("page_id") int pageId);


    @FormUrlEncoded
    @POST("openapi/ad/getAd")
    //positions_id=APP_QD_01&is_show=0&w=1080&h=2160&specialty_id=21
    Observable<LeadBean> getLeadData(@FieldMap Map<String,Object>map);

}
