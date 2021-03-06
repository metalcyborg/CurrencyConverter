package com.metalcyborg.currencyconverter.model.source.remote;

import android.support.annotation.Nullable;

import com.metalcyborg.currencyconverter.model.Currency;
import com.metalcyborg.currencyconverter.model.source.GetCurrencyListCallback;

import java.util.List;

public interface RemoteDataSource {

    @Nullable
    List<Currency> getCurrencies();
}
