package com.raohoulin.myqq.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.raohoulin.myqq.R;
import com.raohoulin.myqq.base.BaseActivity;

import butterknife.Bind;

/**
 * Created by Administrator on 2015.12.4.
 */
public class SecondActivity extends BaseActivity {

    @Bind(R.id.exit) Button exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, SecondActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutID() {
        return R.layout.second_activity;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }
}
