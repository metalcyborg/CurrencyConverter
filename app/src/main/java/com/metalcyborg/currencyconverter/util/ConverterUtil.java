package com.metalcyborg.currencyconverter.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.metalcyborg.currencyconverter.model.Currency;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

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

    public static String loadData(URL url, int readTimeout,
                                  int connectTimeout, String charSet) throws IOException {
        InputStream is = null;
        HttpURLConnection connection = null;
        String result = null;
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(readTimeout);
            connection.setConnectTimeout(connectTimeout);
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.connect();
            int responseCode = connection.getResponseCode();
            if(responseCode != HttpsURLConnection.HTTP_OK) {
                throw new IOException("Http error code: " + responseCode);
            }

            is = connection.getInputStream();
            if(is != null) {
                result = readStream(is, charSet);
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

    private static String readStream(InputStream is, String charSet)
            throws IOException {
        Reader reader = null;
        reader = new InputStreamReader(is, charSet);
        char[] rawBuffer = new char[1024];
        int readSize;
        StringBuffer buffer = new StringBuffer();
        while(((readSize = reader.read(rawBuffer)) != -1)) {
            buffer.append(rawBuffer, 0, readSize);
        }
        return buffer.toString();
    }

    public static <E> E deserializeData(String data, Class<? extends E> clazz) throws Exception {
        Serializer serializer = new Persister();
        return serializer.read(clazz, data);
    }
}
