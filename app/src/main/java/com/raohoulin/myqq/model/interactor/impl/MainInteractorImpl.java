package com.raohoulin.myqq.model.interactor.impl;

import com.raohoulin.myqq.model.interactor.MainInteractor;
import com.raohoulin.myqq.presenter.OnMainFinishedListener;

import rx.Observable;

/**
 * Created by Administrator on 2015.12.7.
 */
public class MainInteractorImpl implements MainInteractor {
    @Override
    public void toSecondActivity(boolean isTurn, OnMainFinishedListener onMainFinishedListener) {
        if (isTurn) {
            onMainFinishedListener.onSuccess();
        } else {
            onMainFinishedListener.onFailure();
        }
    }
}
