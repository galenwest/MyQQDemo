package com.raohoulin.myqq.model.interactor;

import com.raohoulin.myqq.presenter.OnMainFinishedListener;

/**
 * Created by Administrator on 2015.12.7.
 */
public interface MainInteractor {
    public void toSecondActivity(boolean isTurn, OnMainFinishedListener onMainFinishedListener);
}
