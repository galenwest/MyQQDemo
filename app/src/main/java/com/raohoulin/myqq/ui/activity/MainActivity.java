package com.raohoulin.myqq.ui.activity;

import android.content.Intent;
import android.os.Bundle;
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
import com.raohoulin.myqq.ui.view.MainView;

import butterknife.Bind;

import static com.raohoulin.myqq.ui.activity.SecondActivity.actionStart;

/**
 * Created by Administrator on 2015.12.4.
 */
public class MainActivity extends BaseActivity implements MainView, View.OnClickListener {
    private MainPresenter presenter;
    private boolean isTurn;

    @Bind(R.id.progress) ProgressBar progressBar;
    @Bind(R.id.is_turn) Switch is_turn;
    @Bind(R.id.button) Button button;

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

    @Override
    public void showErrorToast() {
        Toast.makeText(this, "跳转错误", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        presenter.goToSecondActivity(isTurn);
    }
}
