package com.metalcyborg.currencyconverter.converter;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.metalcyborg.currencyconverter.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConverterFragment extends Fragment implements ConverterContract.View {

    private ConverterContract.Presenter mPresenter;

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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_converter, container, false);
    }

    @Override
    public void setPresenter(ConverterContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
