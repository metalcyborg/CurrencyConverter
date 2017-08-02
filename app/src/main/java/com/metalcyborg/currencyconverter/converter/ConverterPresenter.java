package com.metalcyborg.currencyconverter.converter;

import android.support.annotation.NonNull;

import com.metalcyborg.currencyconverter.model.source.CurrencyModel;

public class ConverterPresenter implements ConverterContract.Presenter {

    private CurrencyModel mModel;
    private ConverterContract.View mView;

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
}
