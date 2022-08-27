package org.test.myapplication.model.repository;

import android.content.Context;

import androidx.room.Room;

import org.test.myapplication.model.ContactModel;
import org.test.myapplication.model.database.ContactDatabase;
import org.test.myapplication.model.database.ContactDatabaseDAO;

import java.util.List;

public class ContactDBRepository implements IRepository {

    private static ContactDBRepository sInstance;
    private final ContactDatabaseDAO mContactDAO;

    public static ContactDBRepository getInstance(Context context) {
        if (sInstance == null)
            sInstance = new ContactDBRepository(context);

        return sInstance;
    }

    private ContactDBRepository(Context context) {
        Context context1 = context.getApplicationContext();
        ContactDatabase crimeDatabase = Room.databaseBuilder(context1,
                        ContactDatabase.class,
                        "contact.db")
                .allowMainThreadQueries()
                .build();

        mContactDAO = crimeDatabase.getContactDatabaseDAO();
    }


    @Override
    public void updateContact(ContactModel contact) {
        mContactDAO.updateContact(contact);
    }

    @Override
    public void insertContact(ContactModel contact) {
        mContactDAO.insertContact(contact);
    }

    @Override
    public void deleteContact(ContactModel contact) {
        mContactDAO.deleteContact(contact);
    }

    @Override
    public List<ContactModel> getContacts() {
        return mContactDAO.getContacts();
    }

    @Override
    public List<ContactModel> searchContacts(String query) {
        return mContactDAO.searchContacts(query);
    }

    @Override
    public ContactModel getContact(long inputID) {
        return mContactDAO.getContact(inputID);
    }

    @Override
    public void setContactsSelected() {
        mContactDAO.setContactsSelected();
    }

    @Override
    public void setContactsUnSelected() {
        mContactDAO.setContactsUnSelected();
    }

    @Override
    public void deleteSelectedContact() {
        mContactDAO.deleteSelectedContact();
    }

    @Override
    public void setContactSelected(long contact_id) {
        mContactDAO.setContactSelected(contact_id);
    }

    @Override
    public void setContactUnSelected(long contact_id) {
        mContactDAO.setContactUnSelected(contact_id);
    }

}
