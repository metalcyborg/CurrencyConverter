package com.metalcyborg.currencyconverter.model.source.remote;

import com.metalcyborg.currencyconverter.model.Currency;

import java.util.List;

public interface RemoteDataSource {

    interface CurrencyListCallback {

        void onDataLoaded(List<Currency> currencyList);

        void onDataNotAvailable();
    }

    void loadCurrenciesData(CurrencyListCallback callback);
}
