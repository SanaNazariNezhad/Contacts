package org.test.myapplication.model;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "contactTable")
public class ContactModel {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long primaryId;

    @Embedded
    private Name mContactName;

    @ColumnInfo(name = "contactNumber")
    private String mContactNumber;

    @ColumnInfo(name = "contactEmail")
    private String mContactEmail;

    @ColumnInfo(name = "telephoneType")
    private String mTelephoneType;

    @ColumnInfo(name = "selectedContact")
    private int mCheck_Select;

    public ContactModel(Name contactName, String contactNumber, String contactEmail, String telephoneType) {
        mContactName = contactName;
        mContactNumber = contactNumber;
        mContactEmail = contactEmail;
        mTelephoneType = telephoneType;
        mCheck_Select = 0;
    }

    public String getTelephoneType() {
        return mTelephoneType;
    }

    public void setTelephoneType(String telephoneType) {
        mTelephoneType = telephoneType;
    }

    public long getPrimaryId() {
        return primaryId;
    }

    public void setPrimaryId(long primaryId) {
        this.primaryId = primaryId;
    }

    public Name getContactName() {
        return mContactName;
    }

    public void setContactName(Name contactName) {
        mContactName = contactName;
    }

    public String getContactNumber() {
        return mContactNumber;
    }

    public void setContactNumber(String contactNumber) {
        mContactNumber = contactNumber;
    }

    public String getContactEmail() {
        return mContactEmail;
    }

    public void setContactEmail(String contactEmail) {
        mContactEmail = contactEmail;
    }

    public int getCheck_Select() {
        return mCheck_Select;
    }

    public void setCheck_Select(int check_Select) {
        mCheck_Select = check_Select;
    }
}
