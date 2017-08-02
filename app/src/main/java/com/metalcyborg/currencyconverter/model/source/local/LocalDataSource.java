package com.metalcyborg.currencyconverter.model.source.local;

import com.metalcyborg.currencyconverter.model.Currency;

import java.util.List;

public interface LocalDataSource {

    interface LoadCurrencyListCallback {

        void onDataLoaded(List<Currency> currencyList);

        void onDataNotAvailable();
    }

    void loadCurrenciesData();

    void updateCurrencyData(String currencyId, Currency currency);
}
