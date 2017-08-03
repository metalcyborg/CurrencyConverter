package com.metalcyborg.currencyconverter.model.source.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteQueryBuilder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.metalcyborg.currencyconverter.model.Currency;
import com.metalcyborg.currencyconverter.model.source.GetCurrencyListCallback;

import java.util.ArrayList;
import java.util.List;

public class LocalDataSourceImpl implements LocalDataSource {

    private static volatile LocalDataSource mInstance;
    private final CurrencyDatabaseHelper mDatabaseHelper;

    private LocalDataSourceImpl(@NonNull Context context) {
        mDatabaseHelper = new CurrencyDatabaseHelper(context);
    }

    public static LocalDataSource getInstance(Context context) {
        if(mInstance == null) {
            synchronized (LocalDataSourceImpl.class) {
                if(mInstance == null) {
                    mInstance = new LocalDataSourceImpl(context);
                }
            }
        }

        return mInstance;
    }

    @Nullable
    @Override
    public List<Currency> getCurrencies() {
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(CurrencyPersistenceContract.CurrencyTable.TABLE_NAME);

        Cursor cursor = builder.query(mDatabaseHelper.getReadableDatabase(),
                null, null, null, null, null, null);

        List<Currency> currencyList = new ArrayList<>();
        while (cursor.moveToNext()) {
            Currency currency = new Currency(
                    cursor.getString(cursor.getColumnIndex(CurrencyPersistenceContract.CurrencyTable.COLUMN_VALUTE_ID.trim())),
                    cursor.getInt(cursor.getColumnIndex(CurrencyPersistenceContract.CurrencyTable.COLUMN_NUM_CODE.trim())),
                    cursor.getString(cursor.getColumnIndex(CurrencyPersistenceContract.CurrencyTable.COLUMN_CHAR_CODE.trim())),
                    cursor.getInt(cursor.getColumnIndex(CurrencyPersistenceContract.CurrencyTable.COLUMN_NOMINAL.trim())),
                    cursor.getString(cursor.getColumnIndex(CurrencyPersistenceContract.CurrencyTable.COLUMN_NAME.trim())),
                    cursor.getFloat(cursor.getColumnIndex(CurrencyPersistenceContract.CurrencyTable.COLUMN_VALUE.trim()))
            );
            currencyList.add(currency);
        }

        cursor.close();

        if(currencyList.size() == 0)
            return null;

        return currencyList;
    }

    @Override
    public void updateCurrencyData(@NonNull List<Currency> currencyList) {
        SQLiteDatabase db = null;
        try {
            db = mDatabaseHelper.getWritableDatabase();
            db.beginTransaction();

            // Delete old data. Because may be there are unnecessary currencies that deleted from server
            db.delete(CurrencyPersistenceContract.CurrencyTable.TABLE_NAME, null, null);

            // Add new data
            ContentValues cv = new ContentValues();
            for(Currency currency : currencyList) {
                cv.clear();
                cv.put(CurrencyPersistenceContract.CurrencyTable.COLUMN_VALUTE_ID, currency.getCurrencyId());
                cv.put(CurrencyPersistenceContract.CurrencyTable.COLUMN_NUM_CODE, currency.getNumCode());
                cv.put(CurrencyPersistenceContract.CurrencyTable.COLUMN_CHAR_CODE, currency.getCharCode());
                cv.put(CurrencyPersistenceContract.CurrencyTable.COLUMN_NOMINAL, currency.getNominal());
                cv.put(CurrencyPersistenceContract.CurrencyTable.COLUMN_NAME, currency.getName());
                cv.put(CurrencyPersistenceContract.CurrencyTable.COLUMN_VALUE, currency.getValue());
                db.insertOrThrow(CurrencyPersistenceContract.CurrencyTable.TABLE_NAME,
                        null, cv);
            }

            db.setTransactionSuccessful();
        } catch (SQLiteException e) {
            throw new SQLiteException("Error of currencies data updating");
        } finally {
            if(db != null) {
                db.endTransaction();
                db.close();
            }
        }
    }
}
