package com.metalcyborg.currencyconverter.model.source.local;

import com.metalcyborg.currencyconverter.model.Currency;
import com.metalcyborg.currencyconverter.model.source.GetCurrencyListCallback;

import java.util.List;

public interface LocalDataSource {

    void loadCurrenciesData(GetCurrencyListCallback callback);

    void updateCurrencyData(String currencyId, Currency currency);
}
