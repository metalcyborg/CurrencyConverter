package com.metalcyborg.currencyconverter.model.source.remote;

import android.support.annotation.Nullable;
import android.util.Log;

import com.metalcyborg.currencyconverter.model.Currency;
import com.metalcyborg.currencyconverter.model.source.GetCurrencyListCallback;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

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


    @Override
    public void loadCurrenciesData(GetCurrencyListCallback callback) {

    }

    @Nullable
    @Override
    public List<Currency> getCurrencies() {
        try {
            URL url = new URL(URL_STRING);
            String data = loadData(url);
            Log.d("Result", "Result: " + data);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String loadData(URL url) throws IOException {
        InputStream is = null;
        HttpURLConnection connection = null;
        String result = null;
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(READ_TIMEOUT_MS);
            connection.setConnectTimeout(CONNECT_TIMEOUT_MS);
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.connect();
            int responseCode = connection.getResponseCode();
            if(responseCode != HttpsURLConnection.HTTP_OK) {
                throw new IOException("Http error code: " + responseCode);
            }

            is = connection.getInputStream();
            if(is != null) {
                result = readStream(is, 500);
            }

        } finally {
            if(is != null) {
                is.close();
            }
            if(connection != null) {
                connection.disconnect();
            }
        }

        return result;
    }

    private String readStream(InputStream is, int maxReadSize)
            throws IOException, UnsupportedEncodingException {
        Reader reader = null;
        reader = new InputStreamReader(is, "UTF-8");
        char[] rawBuffer = new char[maxReadSize];
        int readSize;
        StringBuffer buffer = new StringBuffer();
        while(((readSize = reader.read(rawBuffer)) != -1) && maxReadSize > 0) {
            if(readSize > maxReadSize) {
                readSize = maxReadSize;
            }
            buffer.append(rawBuffer, 0, readSize);
            maxReadSize -= readSize;
        }
        return buffer.toString();
    }
}
