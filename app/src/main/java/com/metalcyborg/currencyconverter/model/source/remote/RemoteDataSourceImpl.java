package com.metalcyborg.currencyconverter.model.source.remote;

import android.support.annotation.Nullable;

import com.metalcyborg.currencyconverter.model.Currency;
import com.metalcyborg.currencyconverter.model.source.remote.parsemodel.ValCurs;
import com.metalcyborg.currencyconverter.util.ConverterUtil;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RemoteDataSourceImpl implements RemoteDataSource {

    private static volatile RemoteDataSource mInstance;

    private static final String URL_STRING = "http://www.cbr.ru/scripts/XML_daily.asp";
    private static final int READ_TIMEOUT_MS = 3000;
    private static final int CONNECT_TIMEOUT_MS = 3000;

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

    @Nullable
    @Override
    public List<Currency> getCurrencies() {
        try {
            URL url = new URL(URL_STRING);
            String data = ConverterUtil.loadData(url, READ_TIMEOUT_MS, CONNECT_TIMEOUT_MS,
                    "Cp1251");

            ValCurs valCurs = ConverterUtil.deserializeData(data, ValCurs.class);

            List<Currency> currencyList = new ArrayList<>();
            for(ValCurs.Valute valute : valCurs.getValuteList()) {
                float value;
                try {
                    value = ConverterUtil.convertStringToFloat(valute.getValue());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    value = 0f;
                }

                Currency currency = new Currency(
                        valute.getId(),
                        valute.getNumCode(),
                        valute.getCharCode(),
                        valute.getNominal(),
                        valute.getName(),
                        value
                );
                currencyList.add(currency);
            }

            return currencyList;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
