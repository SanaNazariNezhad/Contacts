package org.test.myapplication.model;

import androidx.room.ColumnInfo;

public class Name {
    @ColumnInfo(name = "prefix")
    private String mPrefix;

    @ColumnInfo(name = "first")
    private String mFirst;

    @ColumnInfo(name = "middle")
    private String mMiddle;

    @ColumnInfo(name = "last")
    private String mLast;

    @ColumnInfo(name = "suffix")
    private String mSuffix;

    public String getPrefix() {
        return mPrefix;
    }

    public void setPrefix(String prefix) {
        mPrefix = prefix;
    }

    public String getFirst() {
        return mFirst;
    }

    public void setFirst(String first) {
        mFirst = first;
    }

    public String getMiddle() {
        return mMiddle;
    }

    public void setMiddle(String middle) {
        mMiddle = middle;
    }

    public String getLast() {
        return mLast;
    }

    public void setLast(String last) {
        mLast = last;
    }

    public String getSuffix() {
        return mSuffix;
    }

    public void setSuffix(String suffix) {
        mSuffix = suffix;
    }

    public Name() {
    }
}
