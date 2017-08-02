package com.metalcyborg.currencyconverter.model.source.local;

import android.provider.BaseColumns;

public final class CurrencyPersistenceContract {

    public static class CurrencyTable implements BaseColumns {
        public static final String TABLE_NAME = "Currencies";
        public static final String COLUMN_VALUTE_ID = "ValuteId";
        public static final String COLUMN_NUM_CODE = "NumCode";
        public static final String COLUMN_CHAR_CODE = "CharCode";
        public static final String COLUMN_NOMINAL = "Nominal";
        public static final String COLUMN_NAME = "Name";
        public static final String COLUMN_VALUE = "Value";
    }
}
