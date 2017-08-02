package com.metalcyborg.currencyconverter.model.source;

import android.support.annotation.NonNull;

import com.metalcyborg.currencyconverter.model.source.local.LocalDataSource;
import com.metalcyborg.currencyconverter.model.source.remote.RemoteDataSource;

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
}
