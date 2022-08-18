package org.test.myapplication.model;

public class ContactModel {

    private String mContactName;
    private String mContactNumber;
    private String mContactEmail;

    public ContactModel(String contactName, String contactNumber, String contactEmail) {
        mContactName = contactName;
        mContactNumber = contactNumber;
        mContactEmail = contactEmail;
    }

    public String getContactName() {
        return mContactName;
    }

    public void setContactName(String contactName) {
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
