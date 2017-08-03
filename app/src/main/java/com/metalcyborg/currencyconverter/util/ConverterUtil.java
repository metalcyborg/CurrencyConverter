package com.metalcyborg.currencyconverter.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.metalcyborg.currencyconverter.model.Currency;

public class ConverterUtil {

    public static float calculateAmount(Currency currencyFrom, Currency currencyTo,
                                        float fromValue) {
        if(currencyFrom.getNominal() == 0 || currencyTo.getNominal() == 0) {
            throw new IllegalArgumentException("Currency nominal cannot be 0!");
        }
        return fromValue * (currencyFrom.getValue() / currencyFrom.getNominal()) /
                (currencyTo.getValue() / currencyTo.getNominal());
    }

    public static float convertStringToFloat(@NonNull String numberString)
            throws NumberFormatException, NullPointerException {

        checkNotNull(numberString, "String cannot be null");

        if(numberString.isEmpty()){
            throw new IllegalArgumentException("String cannot be empty");
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
