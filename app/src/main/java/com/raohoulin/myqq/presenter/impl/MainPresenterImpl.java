package com.raohoulin.myqq.presenter.impl;

import com.raohoulin.myqq.model.interactor.MainInteractor;
import com.raohoulin.myqq.model.interactor.impl.MainInteractorImpl;
import com.raohoulin.myqq.presenter.MainPresenter;
import com.raohoulin.myqq.presenter.OnMainFinishedListener;
import com.raohoulin.myqq.ui.view.MainView;

/**
 * Created by Administrator on 2015.12.4.
 */
public class MainPresenterImpl implements MainPresenter, OnMainFinishedListener {
    private MainView mainView;
    private MainInteractor mainInteractor;

    public MainPresenterImpl(MainView mainView) {
        this.mainView = mainView;
        this.mainInteractor = new MainInteractorImpl();
    }

    @Override
    public void goToSecondActivity(boolean isTurn) {
        mainView.showProgress();
        mainInteractor.toSecondActivity(isTurn, this);
    }

    @Override
    public void onSuccess() {
        mainView.hideProgress();
        mainView.navigateToSecond();
    }

    @Override
    public void onFailure() {
        mainView.hideProgress();
        mainView.showErrorToast();
    }
}
