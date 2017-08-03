package com.metalcyborg.currencyconverter.model.source;

import com.metalcyborg.currencyconverter.model.Currency;

import java.util.List;

public interface GetCurrencyListCallback {

    void onDataLoaded(List<Currency> currencyList);

    void onDataNotAvailable();
}
