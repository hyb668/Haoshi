package com.haoshi.rxjava;

import android.content.Intent;
import android.view.View;

import com.haoshi.R;
import com.haoshi.hao.BaseActivity;
import com.haoshi.hao.IndexActivity;


public class RxJavaActivity extends BaseActivity {

    @Override
    public void initView() {
        findViewById(R.id.button).setOnClickListener(this);
        findViewById(R.id.button1).setOnClickListener(this);
    }
    
    @Override
    public void setData() {
        
    }

    @Override
    public int setContentViewID() {
        return R.layout.activity_rx_java;
    }

    @Override
    public String setTitle() {
        return TAG = IndexActivity.class.getSimpleName();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        Intent intent = null;
        switch (v.getId()) {
            case R.id.button:
                intent = new Intent(this, RxJava1Activity.class);
                break;
            case R.id.button1:
                intent = new Intent(this, RxJava2Activity.class);
                break;
        }
        startActivity(intent);
    }
}
