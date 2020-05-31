package com.jiyun.frame;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetManager {
    public NetManager() {
    }

    public static volatile NetManager sNetManager;

    //单例懒汉创建对象
    public static NetManager getInstance() {
        if (sNetManager == null) {
            synchronized (NetManager.class) {
                sNetManager = new NetManager();
            }
        }
        return sNetManager;
    }

    public <T> ApiService getService(T...ts){
        String baseUrl=ServerAddressConfig.BASE_URL;
        if (ts!=null&&ts.length!=0){
            baseUrl= (String) ts[0];
        }
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(new OkHttpClient())
                .build()
                .create(ApiService.class);
    }

    public <T,O> void netWork(Observable<T> localTestInfo,ICommonPresenter pPresenter,int whichApi,int dataType,O...os){
        localTestInfo.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObesrver() {

                    @Override
                    public void onSubscribe(Disposable d) {
                        super.onSubscribe(d);
                        pPresenter.addObserver(d);
                    }

                    @Override
                    public void onSuccess(Object values) {
                        pPresenter.netSuccess(whichApi,dataType,values);
                    }

                    @Override
                    public void onFailed(Throwable throwable) {
                        pPresenter.netFailed(whichApi,throwable);
                    }
                });
    }


    public <T,O> void netWorkByConsumer(Observable<T> localTestInfo,ICommonPresenter pPresenter,int whichApi,int dataType,O...os){
        Disposable subscribe = localTestInfo.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(t -> pPresenter.netSuccess(whichApi,dataType,os),
                        throwable -> pPresenter.netFailed(whichApi,throwable));
        pPresenter.addObserver(subscribe);
    }
}
