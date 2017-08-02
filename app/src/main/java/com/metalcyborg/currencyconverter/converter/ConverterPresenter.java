package com.metalcyborg.currencyconverter.converter;

import android.support.annotation.NonNull;

import com.metalcyborg.currencyconverter.model.Currency;
import com.metalcyborg.currencyconverter.model.source.CurrencyModel;
import com.metalcyborg.currencyconverter.util.ConverterUtil;

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
        // Load currency data

    }

    @Override
    public void stop() {

    }

    @Override
    public void calculateAmount(float fromValue) {
        float amount = ConverterUtil.calculateAmount(mCurrencyFrom, mCurrencyTo, fromValue);
        mView.displaySum(amount);
    }

    @Override
    public void setCurrencyFrom(Currency currency) {
        mCurrencyFrom = currency;
    }

    @Override
    public void setCurrencyTo(Currency currency) {
        mCurrencyTo = currency;
    }
}
