package com.haoshi.rxjava;

import android.view.View;

import com.haoshi.R;
import com.haoshi.hao.BaseActivity;
import com.haoshi.utils.L;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;

public class RxJava1Activity extends BaseActivity {

    private Observable observable;
    private List<String> list = new ArrayList<>();

    @Override
    public void initView() {
        findViewById(R.id.button).setOnClickListener(this);
    }

    @Override
    public int setContentViewID() {
        return R.layout.activity_rx_java1;
    }

    @Override
    public void setData() {
        TAG = RxJava1Activity.class.getSimpleName();
        Collections.addAll(list, "0", "1", "2", "3", "4");
        //方法1
        observable = Observable.create(new Observable.OnSubscribe<String>() {

            @Override
            public void call(Subscriber<? super String> subscriber) {
                observable = Observable.from(list);
            }
        });
        //方法2,等同方法1
        observable = Observable.from(list);
        //方法3,最多10个参数，内部自动封装成数组
        observable = Observable.just("0", "1", "2", "3", "4");
        //方法4,只回调onCompleted();
        observable = Observable.empty();
        //方法5,不回调任何方法
        observable = Observable.never();
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.button:
                observable.subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                        L.d(TAG, "onCompleted()");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String s) {
                        L.d(TAG, s);
                    }
                });
                break;
        }
    }
}
