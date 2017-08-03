package com.metalcyborg.currencyconverter.model.source.remote;

import android.support.annotation.Nullable;
import android.util.Log;

import com.metalcyborg.currencyconverter.model.Currency;
import com.metalcyborg.currencyconverter.model.source.GetCurrencyListCallback;
import com.metalcyborg.currencyconverter.model.source.remote.parsemodel.ValCurs;
import com.metalcyborg.currencyconverter.util.ConverterUtil;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.net.ssl.HttpsURLConnection;

import static android.R.attr.value;

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

            Log.d("Result", data);

            Serializer serializer = new Persister();
            ValCurs valCurs = serializer.read(ValCurs.class, data);

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
                result = readStream(is);
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

    private String readStream(InputStream is)
            throws IOException {
        Reader reader = null;
        reader = new InputStreamReader(is, "Cp1251");
        char[] rawBuffer = new char[1024];
        int readSize;
        StringBuffer buffer = new StringBuffer();
        while(((readSize = reader.read(rawBuffer)) != -1)) {
            buffer.append(rawBuffer, 0, readSize);
        }
        return buffer.toString();
    }
}
