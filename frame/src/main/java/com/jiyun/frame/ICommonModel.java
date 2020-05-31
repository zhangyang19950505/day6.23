package com.jiyun.frame;

public interface ICommonModel<T>{
    void getData(ICommonPresenter iCommonPresenter, int apiConfig, int loadTypeConfig, T... object);
}
