package com.metalcyborg.currencyconverter.model.source;

import com.metalcyborg.currencyconverter.model.Currency;
import com.metalcyborg.currencyconverter.model.source.local.LocalDataSource;
import com.metalcyborg.currencyconverter.model.source.remote.RemoteDataSource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CurrencyModelTest {

    private static List<Currency> CURRENCIES;

    private CurrencyModelImpl mCurrencyModel;

    @Mock
    private LocalDataSource mLocalDataSource;

    @Mock
    private RemoteDataSource mRemoteDataSource;

    @Before
    public void setupCurrencyModel() {
        MockitoAnnotations.initMocks(this);

        mCurrencyModel = CurrencyModelImpl.getInstance(mLocalDataSource, mRemoteDataSource);

        CURRENCIES = new ArrayList<>();
        CURRENCIES.add(new Currency("id0", 0, "code0", 1, "Currency0", 1f));
        CURRENCIES.add(new Currency("id1", 1, "code1", 10, "Currency1", 10f));
        CURRENCIES.add(new Currency("id2", 2, "code2", 100, "Currency2", 100f));
    }

    @After
    public void destroyModelInstance() {
        CurrencyModelImpl.destroyInstance();
    }

    @Test
    public void loadCurrencyListFromServer() {
        when(mRemoteDataSource.getCurrencies()).thenReturn(CURRENCIES);

        mCurrencyModel.getCurrencies();

        verify(mRemoteDataSource).getCurrencies();
        verify(mLocalDataSource).updateCurrencyData(CURRENCIES);
    }

    @Test
    public void loadCurrencyListFromDataBase() {
        when(mRemoteDataSource.getCurrencies()).thenReturn(null);

        mCurrencyModel.getCurrencies();

        verify(mLocalDataSource).getCurrencies();
    }
}
