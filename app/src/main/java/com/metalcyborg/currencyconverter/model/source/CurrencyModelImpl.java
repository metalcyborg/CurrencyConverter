package com.metalcyborg.currencyconverter.model.source;

import android.support.annotation.NonNull;

import com.metalcyborg.currencyconverter.model.Currency;
import com.metalcyborg.currencyconverter.model.source.local.LocalDataSource;
import com.metalcyborg.currencyconverter.model.source.remote.RemoteDataSource;

import java.util.List;

public class CurrencyModelImpl implements CurrencyModel {

    private static volatile CurrencyModelImpl mInstance;
    private LocalDataSource mLocalDataSource;
    private RemoteDataSource mRemoteDataSource;

    private CurrencyModelImpl(@NonNull LocalDataSource localDataSource,
                              @NonNull RemoteDataSource remoteDataSource) {
        mLocalDataSource = localDataSource;
        mRemoteDataSource = remoteDataSource;
    }

    public static CurrencyModelImpl getInstance(LocalDataSource localDataSource,
                                                RemoteDataSource remoteDataSource) {
        if(mInstance == null) {
            synchronized (CurrencyModelImpl.class) {
                if(mInstance == null) {
                    mInstance = new CurrencyModelImpl(localDataSource, remoteDataSource);
                }
            }
        }

        return mInstance;
    }

    @Override
    public void loadCurrenciesData(final GetCurrencyListCallback callback) {
        // Load from server
        // If data not available load from DB
        mRemoteDataSource.loadCurrenciesData(new GetCurrencyListCallback() {
            @Override
            public void onDataLoaded(List<Currency> currencyList) {
                // Update local data
                mLocalDataSource.updateCurrencyData(currencyList);
                callback.onDataLoaded(currencyList);
            }

            @Override
            public void onDataNotAvailable() {
                // Load data from DB
                mLocalDataSource.loadCurrenciesData(new GetCurrencyListCallback() {
                    @Override
                    public void onDataLoaded(List<Currency> currencyList) {
                        callback.onDataLoaded(currencyList);
                    }

                    @Override
                    public void onDataNotAvailable() {
                        callback.onDataNotAvailable();
                    }
                });
            }
        });
    }
}
