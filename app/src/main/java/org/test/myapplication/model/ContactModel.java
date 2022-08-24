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

    public ContactModel(Name contactName, String contactNumber, String contactEmail) {
        mContactName = contactName;
        mContactNumber = contactNumber;
        mContactEmail = contactEmail;
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
}
