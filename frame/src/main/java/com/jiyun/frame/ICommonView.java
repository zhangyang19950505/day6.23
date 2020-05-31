package com.jiyun.frame;

public interface ICommonView <D>{
    void netSuccess(int apiConfig, int loadTypeConfig, D... object);
    void netFailed(int apiConfig, Throwable throwable);
}
