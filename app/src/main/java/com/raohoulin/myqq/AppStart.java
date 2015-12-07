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

import butterknife.Bind;

/**
 * Created by Administrator on 2015.12.4.
 */
public class AppStart extends BaseActivity {
    @Bind(R.id.ai) TextView ai;
    @Bind(R.id.zhe) TextView zhe;
    @Bind(R.id.li) TextView li;
    @Bind(R.id.dou) TextView dou;
    @Bind(R.id.ni) TextView ni;
    @Bind(R.id.hao) TextView hao;
    @Bind(R.id.a) TextView a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 防止第三方跳转时出现双实例
        Activity aty = AppManager.getActivity(MainActivity.class);
        if (aty != null && !aty.isFinishing()) {
            finish();
        }

    }

    @Override
    public int getLayoutID() {
        return R.layout.app_start;
    }

    @Override
    public void initView() {
        myDelay(ai, 300);
        myDelay(zhe, 600);
        myDelay(li, 900);
        myDelay(dou, 1200);
        myDelay(ni, 1500);
        myDelay(hao, 1800);
        myDelay(a, 2100);
    }

    @Override
    public void initData() {}

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
                    }, 800);
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
