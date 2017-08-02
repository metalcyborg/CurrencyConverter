package com.metalcyborg.currencyconverter.model.source.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CurrencyDatabaseHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "CurrencyDB";
    public static final int DB_VERSION = 1;

    private static final String COMMA = ", ";
    private static final String TYPE_TEXT = " TEXT";
    private static final String TYPE_INTEGER = " INTEGER";
    private static final String TYPE_BOOLEAN = " INTEGER";
    private static final String TYPE_FLOAT = " FLOAT";

    private static final String CREATE_CURRENCY_TABLE = "CREATE TABLE" +
            CurrencyPersistenceContract.CurrencyTable.TABLE_NAME + " (" +
            CurrencyPersistenceContract.CurrencyTable._ID + TYPE_INTEGER +
            " PRIMARY KEY AUTOINCREMENT" + COMMA +
            CurrencyPersistenceContract.CurrencyTable.COLUMN_VALUTE_ID + TYPE_TEXT + COMMA +
            CurrencyPersistenceContract.CurrencyTable.COLUMN_NUM_CODE + TYPE_INTEGER + COMMA +
            CurrencyPersistenceContract.CurrencyTable.COLUMN_CHAR_CODE + TYPE_TEXT + COMMA +
            CurrencyPersistenceContract.CurrencyTable.COLUMN_NOMINAL + TYPE_INTEGER + COMMA +
            CurrencyPersistenceContract.CurrencyTable.COLUMN_NAME + TYPE_TEXT + COMMA +
            CurrencyPersistenceContract.CurrencyTable.COLUMN_VALUE + TYPE_FLOAT +
            " )";

    public CurrencyDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CURRENCY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
