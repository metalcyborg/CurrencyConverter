package com.metalcyborg.currencyconverter.model.source;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

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

    public static void destroyInstance() {
        mInstance = null;
    }

    @Nullable
    @Override
    public List<Currency> getCurrencies() {
        // Load from server
        // If data not available load from DB
        List<Currency> currencyList = mRemoteDataSource.getCurrencies();
        if(currencyList == null) {
            return mLocalDataSource.getCurrencies();
        } else {
            mLocalDataSource.updateCurrencyData(currencyList);
            return currencyList;
        }
    }
}
