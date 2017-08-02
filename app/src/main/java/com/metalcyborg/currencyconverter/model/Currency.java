package com.metalcyborg.currencyconverter.model;

public class Currency {

    private String mCurrencyId;
    private int mNumCode;
    private String mCharCode;
    private int mNominal;
    private String mName;
    private float mValue;

    public Currency(String currencyId, int numCode, String charCode, int nominal, String name,
                    float value) {
        mCurrencyId = currencyId;
        mNumCode = numCode;
        mCharCode = charCode;
        mNominal = nominal;
        mName = name;
        mValue = value;
    }

    public String getCurrencyId() {
        return mCurrencyId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Currency)) return false;

        Currency currency = (Currency) o;

        if (mNumCode != currency.mNumCode) return false;
        if (mNominal != currency.mNominal) return false;
        if (Float.compare(currency.mValue, mValue) != 0) return false;
        if (!mCurrencyId.equals(currency.mCurrencyId)) return false;
        if (!mCharCode.equals(currency.mCharCode)) return false;
        return mName.equals(currency.mName);

    }

    @Override
    public int hashCode() {
        int result = mCurrencyId.hashCode();
        result = 31 * result + mNumCode;
        result = 31 * result + mCharCode.hashCode();
        result = 31 * result + mNominal;
        result = 31 * result + mName.hashCode();
        result = 31 * result + (mValue != +0.0f ? Float.floatToIntBits(mValue) : 0);
        return result;
    }
}
