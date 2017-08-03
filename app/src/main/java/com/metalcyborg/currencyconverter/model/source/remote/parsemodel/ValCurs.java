package com.metalcyborg.currencyconverter.model.source.remote.parsemodel;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name="ValCurs")
public class ValCurs {

    @Attribute(name = "Date")
    private String mDate;

    @Attribute(name = "name")
    private String mName;

    @ElementList(required = true, inline = true)
    private List<Valute> mValuteList;

    public String getDate() {
        return mDate;
    }

    public String getName() {
        return mName;
    }

    public List<Valute> getValuteList() {
        return mValuteList;
    }

    @Root(name = "Valute")
    public static class Valute {

        @Attribute(name = "ID")
        private String mId;

        @Element(name = "NumCode")
        private int mNumCode;

        @Element(name = "CharCode")
        private String mCharCode;

        @Element(name = "Nominal")
        private int mNominal;

        @Element(name = "Name")
        private String mName;

        // Float with comma is parsed like a String
        @Element(name = "Value")
        private String mValue;

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

        public String getValue() {
            return mValue;
        }
    }
}
