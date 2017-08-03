package com.metalcyborg.currencyconverter.model.source.remote;

import com.metalcyborg.currencyconverter.model.source.GetCurrencyListCallback;

public interface RemoteDataSource {

    void loadCurrenciesData(GetCurrencyListCallback callback);
}
