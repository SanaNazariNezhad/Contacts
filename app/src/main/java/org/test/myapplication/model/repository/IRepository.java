package org.test.myapplication.model.repository;

import org.test.myapplication.model.ContactModel;

import java.util.List;

public interface IRepository {

    void updateContact(ContactModel contact);

    void insertContact(ContactModel contact);

    void deleteContact(ContactModel contact);

    List<ContactModel> getContacts();

    List<ContactModel> searchContacts(String query);

    ContactModel getContact(long inputID);

    void setContactsSelected();

    void setContactsUnSelected();

    void deleteSelectedContact();

    void setContactSelected(long contact_id);

    void setContactUnSelected(long contact_id);

}
