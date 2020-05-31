package com.jiyun.frame;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public abstract class BaseObesrver implements Observer {
    private Disposable mDisposable;

    @Override
    public void onSubscribe(Disposable d) {
        mDisposable=d;
    }

    @Override
    public void onNext(Object o) {
        onSuccess(o);
        dispose();
    }

    @Override
    public void onError(Throwable e) {
        onFailed(e);
        dispose();
    }

    @Override
    public void onComplete() {
        dispose();
    }

    public abstract void onSuccess(Object values);

    public abstract void onFailed(Throwable throwable);

    private void dispose() {
        if (mDisposable!=null&&!mDisposable.isDisposed()){
            mDisposable.dispose();
        }
    }
}
