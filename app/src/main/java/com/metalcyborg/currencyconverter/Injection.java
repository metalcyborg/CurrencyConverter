package com.metalcyborg.currencyconverter;

import android.content.Context;
import android.support.annotation.NonNull;

import com.metalcyborg.currencyconverter.model.source.CurrencyModel;
import com.metalcyborg.currencyconverter.model.source.CurrencyModelImpl;
import com.metalcyborg.currencyconverter.model.source.local.LocalDataSourceImpl;
import com.metalcyborg.currencyconverter.model.source.remote.RemoteDataSourceImpl;

public class Injection {

    public static CurrencyModel provideCurrencyModel(@NonNull Context context) {
        return CurrencyModelImpl.getInstance(
                LocalDataSourceImpl.getInstance(context),
                RemoteDataSourceImpl.getInstance()
        );
    }
}
