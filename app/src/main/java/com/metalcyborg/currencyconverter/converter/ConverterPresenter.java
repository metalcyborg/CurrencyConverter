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

import static com.metalcyborg.currencyconverter.util.ConverterUtil.checkNotNull;

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
        mLoader = checkNotNull(loader, "Loader cannot be null!");
        mLoaderManager = checkNotNull(loaderManager, "Loader manager cannot be null!");
        mModel = checkNotNull(model, "Model cannot be null!");
        mView = checkNotNull(view, "View cannot be null!");
        mView.setPresenter(this);
    }

    @Override
    public void start() {
        // Load currency data
        mView.setProgressVisibility(true);
        mLoaderManager.initLoader(CURRENCY_QUERY, null, this);
    }

    @Override
    public void stop() {

    }

    @Override
    public void calculateAmount(float fromValue) {
        if(fromValue < 0) {
            throw new IllegalArgumentException("Currency value cannot be less than 0!");
        }

        float amount = ConverterUtil.calculateAmount(mCurrencyFrom, mCurrencyTo, fromValue);
        mView.displaySum(amount);
    }

    @Override
    public void setCurrencyFrom(@NonNull Currency currency) {
        mCurrencyFrom = checkNotNull(currency, "Currency cannot be null!");
    }

    @Override
    public void setCurrencyTo(@NonNull Currency currency) {
        mCurrencyTo = checkNotNull(currency, "Currency cannot be null!");
    }

    @Override
    public Loader<List<Currency>> onCreateLoader(int id, Bundle args) {
        return mLoader;
    }

    @Override
    public void onLoadFinished(Loader<List<Currency>> loader, List<Currency> data) {
        mView.setProgressVisibility(false);
        if(data == null) {
            mView.showLoadingErrorMessage();
        } else {
            mView.addCurrencies(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Currency>> loader) {

    }
}
