package com.metalcyborg.currencyconverter.model.source;

import android.support.annotation.Nullable;

import com.metalcyborg.currencyconverter.model.Currency;

import java.util.List;

public interface CurrencyModel {

    void loadCurrenciesData(GetCurrencyListCallback callback);

    @Nullable
    List<Currency> getCurrencies();
}
