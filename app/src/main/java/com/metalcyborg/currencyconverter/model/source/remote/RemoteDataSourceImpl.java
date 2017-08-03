package com.metalcyborg.currencyconverter.model.source.remote;

import com.metalcyborg.currencyconverter.model.source.GetCurrencyListCallback;

public class RemoteDataSourceImpl implements RemoteDataSource {

    private static volatile RemoteDataSource mInstance;

    private RemoteDataSourceImpl() {

    }

    public static RemoteDataSource getInstance() {
        if(mInstance == null) {
            synchronized (RemoteDataSourceImpl.class) {
                if(mInstance == null) {
                    mInstance = new RemoteDataSourceImpl();
                }
            }
        }

        return mInstance;
    }


    @Override
    public void loadCurrenciesData(GetCurrencyListCallback callback) {

    }
}
