package com.raohoulin.myqq.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import com.raohoulin.myqq.base.interf.BaseViewInterface;

import java.util.Map;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2015.12.4.
 */
public abstract class BaseActivity extends Activity implements BaseViewInterface {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getLayoutID() != 0) {
            setContentView(getLayoutID());
        }

        ButterKnife.bind(this);
        initView();
        initData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    public int getLayoutID() {
        return 0;
    }

}
