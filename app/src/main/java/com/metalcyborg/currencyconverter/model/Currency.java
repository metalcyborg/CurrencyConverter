package com.metalcyborg.currencyconverter.model;

public class Currency {

    private String mId;
    private int mNumCode;
    private String mCharCode;
    private int mNominal;
    private String mName;
    private float mValue;

    public Currency(String id, int numCode, String charCode, int nominal, String name,
                    float value) {
        mId = id;
        mNumCode = numCode;
        mCharCode = charCode;
        mNominal = nominal;
        mName = name;
        mValue = value;
    }

    public String getId() {
        return mId;
    }

    public int getNumCode() {
        return mNumCode;
    }

    public String getCharCode() {
        return mCharCode;
    }

    public int getNominal() {
        return mNominal;
    }

    public String getName() {
        return mName;
    }

    public float getValue() {
        return mValue;
    }
}
