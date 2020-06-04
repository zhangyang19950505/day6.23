package com.jiyun.frame.mvp;

public interface ICommonModel<T>{
    void getData(ICommonPresenter iCommonPresenter, int apiConfig, int loadTypeConfig, T... object);
}
