package com.metalcyborg.currencyconverter.converter;

import com.metalcyborg.currencyconverter.BasePresenter;
import com.metalcyborg.currencyconverter.BaseView;

public interface ConverterContract {

    interface View extends BaseView<Presenter> {

    }

    interface Presenter extends BasePresenter {

    }
}
