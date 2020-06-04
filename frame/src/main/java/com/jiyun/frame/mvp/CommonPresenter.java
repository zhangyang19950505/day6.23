package com.jiyun.frame.mvp;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;

public class CommonPresenter<V extends ICommonView, M extends ICommonModel> implements ICommonPresenter {
    public SoftReference<V> mView;
    public SoftReference<M> mModel;
    private List<Disposable> mDisposable;

    //SoftReference   软引用对象，为了在内存不足时将对象回收
    public CommonPresenter(V mView, M mModel) {
        mDisposable = new ArrayList<>();
        this.mView = new SoftReference<>(mView);
        this.mModel = new SoftReference<>(mModel);
    }


    //发起普通网络请求
    @Override
    public void getData(int apiConfig, int loadTypeConfig, Object... objects) {
        if (mModel != null && mModel.get() != null)
            mModel.get().getData(this, apiConfig, loadTypeConfig, objects);
    }

    /**
     * 将所有网络请求的订阅关系，统一到中间层的集合中，用于view销毁时，统一处理
     *
     * @param disposable 订阅对象
     */
    @Override
    public void addObserver(Disposable disposable) {
        if (disposable == null) return;
        mDisposable.add(disposable);
    }

    //回调V层成功方法
    @Override
    public void netSuccess(int apiConfig, int loadTypeConfig, Object[] object) {
        if (mView != null && mView.get() != null)
            mView.get().netSuccess(apiConfig, loadTypeConfig, object);
    }

    //回调V层失败方法
    @Override
    public void netFailed(int apiConfig, Throwable throwable) {
        if (mView != null && mView.get() != null)
            mView.get().netFailed(apiConfig, throwable);
    }


    /**
     * 当activity页面销毁执行ondestroy时，这个时候已经没有展示数据的需求了，所以首先要将在该页面进行的所有网络请求
     * 终止，以免gc回收时发现view仍被持有不能回收导致内存泄漏。当然这个即使不处理，这个泄漏的时间会很短暂，当gc线程
     * 下一次检测到该对象，网络任务如果已经完成，并不影响activity的回收
     * <p>
     * 在MVP使用中，为了实现视图和数据的解耦，我们通过中间层presenter来进行双向绑定和处理，但是当view销毁时，由于P层仍然
     * 持有view的引用，也可能会发生view不能被回收导致内存泄漏的情况，所以当页面销毁时，将presenter同view和model进行解绑。
     */
    public void clear() {
        for (Disposable disposable : mDisposable) {
            if (disposable != null && !disposable.isDisposed())
                disposable.dispose();
        }
        if (mView != null) {
            mView.clear();
            mView = null;
        }
        if (mModel != null) {
            mModel.clear();
            mModel = null;
        }
    }
}
