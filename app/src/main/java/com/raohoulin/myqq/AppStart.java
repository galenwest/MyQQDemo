package com.raohoulin.myqq;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.raohoulin.myqq.base.BaseActivity;
import com.raohoulin.myqq.ui.activity.MainActivity;

/**
 * Created by Administrator on 2015.12.4.
 */
public class AppStart extends BaseActivity {
    TextView ai;
    TextView zhe;
    TextView li;
    TextView dou;
    TextView ni;
    TextView hao;
    TextView a;

    @Override
    public int getLayoutID() {
        return R.layout.app_start;
    }

    @Override
    public void initView() { // 防止第三方跳转时出现双实例
        Activity aty = AppManager.getActivity(MainActivity.class);
        if (aty != null && !aty.isFinishing()) {
            finish();
        }
        ai = findViewById(R.id.ai);
        zhe = findViewById(R.id.zhe);
        li = findViewById(R.id.li);
        dou = findViewById(R.id.dou);
        ni = findViewById(R.id.ni);
        hao = findViewById(R.id.hao);
        a = findViewById(R.id.a);
        myDelay(ai, 100);
        myDelay(zhe, 200);
        myDelay(li, 300);
        myDelay(dou, 400);
        myDelay(ni, 500);
        myDelay(hao, 600);
        myDelay(a, 700);
    }

    @Override
    public void initData() {
    }

    public void myDelay(final View v, long startTime) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                v.setVisibility(View.VISIBLE);
                fromXml(v);
            }
        }, startTime);
    }

    private void fromXml(View v) {
        AnimationSet sets = (AnimationSet) AnimationUtils.loadAnimation(this, R.anim.alphaanim);
        v.startAnimation(sets);
        if (v.getId() == R.id.a) {
            sets.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            redirectTo();
                        }
                    }, 100);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
        }
    }

    /**
     * 跳转到...
     */
    private void redirectTo() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
//      注意：此方法只能在startActivity和finish方法之后调用。
        overridePendingTransition(R.anim.activity_in, R.anim.activity_out_to_right);
    }

}
