package com.metalcyborg.currencyconverter.util;

import android.support.annotation.Nullable;

import com.metalcyborg.currencyconverter.model.Currency;

public class ConverterUtil {

    public static float calculateAmount(Currency currencyFrom, Currency currencyTo,
                                        float fromValue) {
        return fromValue * (currencyFrom.getValue() / currencyFrom.getNominal()) /
                (currencyTo.getValue() / currencyTo.getNominal());
    }

    public static float convertStringToFloat(String numberString)
            throws NumberFormatException, NullPointerException{
        if(numberString == null || numberString.isEmpty()){
            throw new NullPointerException("Empty or null number string");
        }

        if(numberString.contains(",")) {
            numberString = numberString.replace(",", ".");
        }

        float result;
        try {
            result = Float.parseFloat(numberString);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Invalid number string");
        }

        return result;
    }

    public static <E> E checkNotNull(E object, @Nullable String message) {
        if(object == null) {
            throw new NullPointerException(message);
        } else {
            return object;
        }
    }
}
