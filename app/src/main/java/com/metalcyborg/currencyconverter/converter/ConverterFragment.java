package com.metalcyborg.currencyconverter.converter;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.metalcyborg.currencyconverter.R;
import com.metalcyborg.currencyconverter.model.Currency;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConverterFragment extends Fragment implements ConverterContract.View {

    private ConverterContract.Presenter mPresenter;
    private Spinner mCurrencyFromSpinner;
    private Spinner mCurrencyToSpinner;
    private EditText mValueFromText;
    private TextView mAmountValueText;
    private ProgressBar mProgressBar;
    private LinearLayout mLayoutContainer;
    private CurrencyAdapter mCurrencyFromAdapter;
    private CurrencyAdapter mCurrencyToAdapter;

    public ConverterFragment() {
        // Required empty public constructor
    }

    public static ConverterFragment newInstance() {
        Bundle args = new Bundle();
        ConverterFragment fragment = new ConverterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_converter, container, false);

        mCurrencyFromSpinner = (Spinner) view.findViewById(R.id.currencyFrom);
        mCurrencyToSpinner = (Spinner) view.findViewById(R.id.currencyTo);
        mValueFromText = (EditText) view.findViewById(R.id.valueFrom);
        mAmountValueText = (TextView) view.findViewById(R.id.valueTo);
        mProgressBar = (ProgressBar) view.findViewById(R.id.progress);
        mLayoutContainer = (LinearLayout) view.findViewById(R.id.layout_container);

        mCurrencyFromSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Currency currency = (Currency) parent.getItemAtPosition(position);
                mPresenter.setCurrencyFrom(currency);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mCurrencyToSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Currency currency = (Currency) parent.getItemAtPosition(position);
                mPresenter.setCurrencyTo(currency);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.stop();
    }

    @Override
    public void setPresenter(ConverterContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void setProgressVisibility(boolean visibility) {
        if(visibility) {
            mLayoutContainer.setVisibility(View.GONE);
            mProgressBar.setVisibility(View.VISIBLE);
        } else {
            mProgressBar.setVisibility(View.GONE);
            mLayoutContainer.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void addCurrencies(List<Currency> currencyList) {

        if(mCurrencyFromAdapter == null) {
            mCurrencyFromAdapter = new CurrencyAdapter(currencyList);
            mCurrencyFromSpinner.setAdapter(mCurrencyFromAdapter);
        } else {
            mCurrencyFromAdapter.setItems(currencyList);
            mCurrencyFromAdapter.notifyDataSetChanged();
        }

        if(mCurrencyToAdapter == null) {
            mCurrencyToAdapter = new CurrencyAdapter(currencyList);
            mCurrencyToSpinner.setAdapter(mCurrencyToAdapter);
        } else {
            mCurrencyToAdapter.setItems(currencyList);
            mCurrencyToAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void displaySum(float sumValue) {
        // TODO: add locale
        mAmountValueText.setText(String.format("%f", sumValue));
    }

    private class CurrencyAdapter extends BaseAdapter {

        private List<Currency> mCurrencyList;

        public CurrencyAdapter(List<Currency> currencyList) {
            mCurrencyList = currencyList;
        }

        @Override
        public int getCount() {
            if(mCurrencyList == null)
                return 0;

            return mCurrencyList.size();
        }

        @Override
        public Object getItem(int position) {
            return mCurrencyList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return mCurrencyList.get(position).hashCode();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final View view;
            final CurrencyViewHolder viewHolder;

            if(convertView == null) {
                LayoutInflater inflater = getActivity().getLayoutInflater();
                view = inflater.inflate(R.layout.currency_spinner_item, parent, false);
                viewHolder = new CurrencyViewHolder();
                viewHolder.mCodeText = (TextView) view.findViewById(R.id.code);
                viewHolder.mNameText = (TextView) view.findViewById(R.id.name);
                view.setTag(viewHolder);
            } else {
                view = convertView;
                viewHolder = (CurrencyViewHolder) view.getTag();
            }

            Currency currency = (Currency) getItem(position);
            viewHolder.mCodeText.setText(currency.getCharCode());
            viewHolder.mNameText.setText(currency.getName());

            return view;
        }

        public void setItems(List<Currency> currencyList) {
            mCurrencyList = currencyList;
        }
    }

    private static class CurrencyViewHolder {
        private TextView mCodeText;
        private TextView mNameText;
    }
}
