package com.raohoulin.myqq.ui.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.Toast;

import com.raohoulin.myqq.R;
import com.raohoulin.myqq.base.BaseActivity;
import com.raohoulin.myqq.presenter.MainPresenter;
import com.raohoulin.myqq.presenter.impl.MainPresenterImpl;
import com.raohoulin.myqq.ui.helper.DoubleClickExitHelper;
import com.raohoulin.myqq.ui.view.MainView;
import com.raohoulin.myqq.widget.SlidingMenu;

import butterknife.Bind;

import static com.raohoulin.myqq.ui.activity.SecondActivity.actionStart;

/**
 * Created by Administrator on 2015.12.4.
 */
public class MainActivity extends BaseActivity implements MainView, View.OnClickListener {
    private MainPresenter presenter;
    private boolean isTurn;
    private DoubleClickExitHelper mDoubleClickExit;

    @Bind(R.id.progress) ProgressBar progressBar;
    @Bind(R.id.is_turn) Switch is_turn;
    @Bind(R.id.button) Button button;
    @Bind(R.id.change_view) Button change_view;
    @Bind(R.id.menu_id) SlidingMenu slidingMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public int getLayoutID() {
        return R.layout.main_activity;
    }

    @Override
    public void initView() {
//        progressBar = (ProgressBar) findViewById(R.id.progress);
        button.setOnClickListener(this);
        change_view.setOnClickListener(this);
        is_turn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isTurn = isChecked;
            }
        });

    }

    @Override
    public void initData() {
        presenter = new MainPresenterImpl(this);
        isTurn = is_turn.isChecked();
        mDoubleClickExit = new DoubleClickExitHelper(this);
    }

    @Override public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override public void navigateToSecond() {
        actionStart(this);
//        finish();
    }

    /**
     * 监听返回--是否退出程序
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // 是否退出应用
                return mDoubleClickExit.onKeyDown(keyCode, event);
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void showErrorToast() {
        Toast.makeText(this, "跳转错误", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                presenter.goToSecondActivity(isTurn);
                break;
            case R.id.change_view:
                slidingMenu.toggle();
                break;
            default:
                break;
        }

    }
}
