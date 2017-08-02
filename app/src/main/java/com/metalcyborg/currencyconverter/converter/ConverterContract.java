package com.metalcyborg.currencyconverter.converter;

import com.metalcyborg.currencyconverter.BasePresenter;
import com.metalcyborg.currencyconverter.BaseView;
import com.metalcyborg.currencyconverter.model.Currency;

import java.util.List;

public interface ConverterContract {

    interface View extends BaseView<Presenter> {

        void setProgressVisibility(boolean visibility);

        void addCurrencies(List<Currency> currencyList);

        void displaySum(float sumValue);
    }

    interface Presenter extends BasePresenter {

        void calculateAmount(float fromValue);

        void setCurrencyFrom(Currency currency);

        void setCurrencyTo(Currency currency);
    }
}
