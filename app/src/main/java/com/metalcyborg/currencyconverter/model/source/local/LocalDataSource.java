package com.metalcyborg.currencyconverter.model.source.local;

import android.support.annotation.Nullable;

import com.metalcyborg.currencyconverter.model.Currency;
import com.metalcyborg.currencyconverter.model.source.GetCurrencyListCallback;

import java.util.List;

public interface LocalDataSource {

    void loadCurrenciesData(GetCurrencyListCallback callback);

    @Nullable
    List<Currency> getCurrencies();

    void updateCurrencyData(List<Currency> currencyList);
}
