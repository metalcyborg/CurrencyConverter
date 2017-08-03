package com.metalcyborg.currencyconverter.converter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.metalcyborg.currencyconverter.Injection;
import com.metalcyborg.currencyconverter.R;
import com.metalcyborg.currencyconverter.model.source.CurrencyListLoader;
import com.metalcyborg.currencyconverter.model.source.CurrencyModel;

public class ConverterActivity extends AppCompatActivity {

    private ConverterPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_converter);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ConverterFragment fragment = (ConverterFragment) getSupportFragmentManager()
                .findFragmentById(R.id.content);
        if(fragment == null) {
            fragment = ConverterFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.content, fragment)
                    .commit();
        }

        CurrencyModel model = Injection.provideCurrencyModel(getApplicationContext());
        CurrencyListLoader loader = new CurrencyListLoader(getApplicationContext(),
                model);

        // Model, view and presenter binding
        mPresenter = new ConverterPresenter(loader, getSupportLoaderManager(), model, fragment);
    }
}
