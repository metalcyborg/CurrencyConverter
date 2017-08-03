package com.metalcyborg.currencyconverter.model.source.local;

import android.content.Context;
import android.support.annotation.NonNull;

import com.metalcyborg.currencyconverter.model.Currency;
import com.metalcyborg.currencyconverter.model.source.GetCurrencyListCallback;

public class LocalDataSourceImpl implements LocalDataSource {

    private static volatile LocalDataSource mInstance;
    private final Context mContext;

    private LocalDataSourceImpl(@NonNull Context context) {
        mContext = context;
    }

    public static LocalDataSource getInstance(Context context) {
        if(mInstance == null) {
            synchronized (LocalDataSourceImpl.class) {
                if(mInstance == null) {
                    mInstance = new LocalDataSourceImpl(context);
                }
            }
        }

        return mInstance;
    }

    @Override
    public void loadCurrenciesData(GetCurrencyListCallback callback) {

    }

    @Override
    public void updateCurrencyData(String currencyId, Currency currency) {

    }
}
