package com.metalcyborg.currencyconverter.model.source;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.metalcyborg.currencyconverter.model.Currency;

import java.util.List;

public class CurrencyListLoader extends AsyncTaskLoader<List<Currency>> {

    private final CurrencyModel mCurrencyModel;
    private List<Currency> mCurrencyList;

    public CurrencyListLoader(Context context, CurrencyModel currencyModel) {
        super(context);
        mCurrencyModel = currencyModel;
    }

    @Override
    public List<Currency> loadInBackground() {
        return mCurrencyModel.getCurrencies();
    }

    @Override
    public void deliverResult(List<Currency> data) {
        mCurrencyList = data;

        if(isStarted()) {
            super.deliverResult(data);
        }
    }

    @Override
    protected void onStartLoading() {
        if(mCurrencyList != null) {
            deliverResult(mCurrencyList);
        }

        if(takeContentChanged() || mCurrencyList == null) {
            forceLoad();
        }
    }

    @Override
    protected void onStopLoading() {
        cancelLoad();
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
    }
}
