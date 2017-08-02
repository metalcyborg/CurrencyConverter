package com.metalcyborg.currencyconverter.converter;

import android.support.annotation.NonNull;

import com.metalcyborg.currencyconverter.model.Currency;
import com.metalcyborg.currencyconverter.model.source.CurrencyModel;

public class ConverterPresenter implements ConverterContract.Presenter {

    private CurrencyModel mModel;
    private ConverterContract.View mView;
    private Currency mCurrencyFrom;
    private Currency mCurrencyTo;

    public ConverterPresenter(@NonNull CurrencyModel model,
                              @NonNull ConverterContract.View view) {
        mModel = model;
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void calculateAmount(float fromValue) {

    }

    @Override
    public void setCurrencyFrom(Currency currency) {

    }

    @Override
    public void setCurrencyTo(Currency currency) {

    }
}
