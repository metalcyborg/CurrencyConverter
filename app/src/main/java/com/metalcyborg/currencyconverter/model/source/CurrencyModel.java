package com.metalcyborg.currencyconverter.model.source;

public interface CurrencyModel {

    void loadCurrenciesData(GetCurrencyListCallback callback);
}
