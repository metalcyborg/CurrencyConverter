package com.metalcyborg.currencyconverter.converter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import com.metalcyborg.currencyconverter.model.Currency;
import com.metalcyborg.currencyconverter.model.source.CurrencyListLoader;
import com.metalcyborg.currencyconverter.model.source.CurrencyModel;
import com.metalcyborg.currencyconverter.util.ConverterUtil;

import java.util.List;

public class ConverterPresenter implements ConverterContract.Presenter,
        LoaderManager.LoaderCallbacks<List<Currency>> {

    private static final int CURRENCY_QUERY = 1;

    private final CurrencyListLoader mLoader;
    private final LoaderManager mLoaderManager;
    private final CurrencyModel mModel;
    private final ConverterContract.View mView;
    private Currency mCurrencyFrom;
    private Currency mCurrencyTo;

    public ConverterPresenter(@NonNull CurrencyListLoader loader,
                              @NonNull LoaderManager loaderManager,
                              @NonNull CurrencyModel model,
                              @NonNull ConverterContract.View view) {
        mLoader = loader;
        mLoaderManager = loaderManager;
        mModel = model;
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void start() {
        // Load currency data
        mLoaderManager.initLoader(CURRENCY_QUERY, null, this).forceLoad();
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

    @Override
    public Loader<List<Currency>> onCreateLoader(int id, Bundle args) {
        mView.setProgressVisibility(true);
        return mLoader;
    }

    @Override
    public void onLoadFinished(Loader<List<Currency>> loader, List<Currency> data) {
        mView.addCurrencies(data);
        mView.setProgressVisibility(false);
    }

    @Override
    public void onLoaderReset(Loader<List<Currency>> loader) {

    }
}
