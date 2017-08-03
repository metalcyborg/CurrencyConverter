package com.metalcyborg.currencyconverter.util;

import com.metalcyborg.currencyconverter.model.Currency;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertSame;

public class ConverterUtilTest {

    @Test
    public void calculateAmountTest() {
        final Currency currencyFrom = new Currency("id1", 1, "Code1", 10, "Name1", 123f);
        final Currency currencyTo = new Currency("id2", 2, "Code2", 100, "Name1", 456f);
        final float value = 42f;
        final float expected = 113.2895f;

        float actual = ConverterUtil.calculateAmount(currencyFrom, currencyTo, value);
        assertEquals(expected, actual, 0.0001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void calculateAmount_ZeroNominal() {
        final Currency currencyFrom = new Currency("id1", 1, "Code1", 10, "Name1", 123f);
        final Currency currencyTo = new Currency("id2", 2, "Code2", 0, "Name2", 456f);
        final float value = 42f;

        ConverterUtil.calculateAmount(currencyFrom, currencyTo, value);
    }

    @Test
    public void convertStringToFloat() {
        final String floatString = "123,4567";
        final float expected = 123.4567f;

        float actual = ConverterUtil.convertStringToFloat(floatString);
        assertEquals(expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void convertStringToFloat_NullString() {
        ConverterUtil.convertStringToFloat(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void convertStringToFloat_EmptyString() {
        ConverterUtil.convertStringToFloat("");
    }

    @Test(expected = NumberFormatException.class)
    public void convertStringToFloat_InvalidString() {
        ConverterUtil.convertStringToFloat("123.456.789");
    }

    @Test(expected = NullPointerException.class)
    public void checkNotNull_NullObject() {
        ConverterUtil.checkNotNull(null, "Message");
    }

    @Test
    public void checkNotNull_NotNullObject() {
        Integer expected = 10;
        Integer actual = ConverterUtil.checkNotNull(expected, "Message");
        assertSame(expected, actual);
    }
}
