package com.metalcyborg.currencyconverter.model.source;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.metalcyborg.currencyconverter.model.Currency;

import java.util.List;

public class CurrencyListLoader extends AsyncTaskLoader<List<Currency>> {

    private final CurrencyModel mCurrencyModel;

    public CurrencyListLoader(Context context, CurrencyModel currencyModel) {
        super(context);
        mCurrencyModel = currencyModel;
    }

    @Override
    public List<Currency> loadInBackground() {
        return mCurrencyModel.getCurrencies();
    }
}
