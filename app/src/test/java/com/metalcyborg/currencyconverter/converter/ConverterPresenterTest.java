package com.metalcyborg.currencyconverter.converter;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import com.metalcyborg.currencyconverter.model.Currency;
import com.metalcyborg.currencyconverter.model.source.CurrencyListLoader;
import com.metalcyborg.currencyconverter.model.source.CurrencyModel;
import com.metalcyborg.currencyconverter.util.ConverterUtil;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ConverterPresenterTest {

    private static List<Currency> CURRENCIES;
    private static final float TEST_VALUE = 10f;

    private ConverterPresenter mPresenter;

    @Mock
    private CurrencyModel mCurrencyModel;

    @Mock
    private ConverterContract.View mView;

    @Mock
    private CurrencyListLoader mLoader;

    @Mock
    private LoaderManager mLoaderManager;

    @Before
    public void setupConverterPresenter() {
        MockitoAnnotations.initMocks(this);

        mPresenter = new ConverterPresenter(
                mLoader, mLoaderManager, mCurrencyModel, mView);

        CURRENCIES = new ArrayList<>();
        CURRENCIES.add(new Currency("id0", 0, "code0", 1, "Currency0", 1f));
        CURRENCIES.add(new Currency("id1", 1, "code1", 10, "Currency1", 10f));
        CURRENCIES.add(new Currency("id2", 2, "code2", 100, "Currency2", 100f));
    }

    @Test
    public void startPresenter() {
        mPresenter.start();

        verify(mView).setProgressVisibility(true);
        verify(mLoaderManager).initLoader(anyInt(), any(Bundle.class),
                any(LoaderManager.LoaderCallbacks.class));
    }

    @Test
    public void loadCurrenciesFromModelAndDisplayIntoSpinners() {
        mPresenter.onLoadFinished(mock(Loader.class), CURRENCIES);

        verify(mView).setProgressVisibility(false);
        verify(mView).addCurrencies(CURRENCIES);
    }

    @Test
    public void loadCurrenciesFromModel_DataNotAvailable() {
        mPresenter.onLoadFinished(mock(Loader.class), null);

        verify(mView).setProgressVisibility(false);
        verify(mView).showLoadingErrorMessage();
    }

    @Test
    public void calculateResult() {
        mPresenter.setCurrencyFrom(CURRENCIES.get(0));
        mPresenter.setCurrencyTo(CURRENCIES.get(1));
        mPresenter.calculateAmount(TEST_VALUE);

        float sum = ConverterUtil.calculateAmount(CURRENCIES.get(0), CURRENCIES.get(1),
                TEST_VALUE);

        verify(mView).displaySum(sum);
    }
}
