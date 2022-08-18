package org.test.myapplication.model.repository;

import org.test.myapplication.model.ContactModel;
import java.util.List;

public interface IRepository {

    void updateContact(ContactModel contact);
    void insertContact(ContactModel contact);
    void deleteContact(ContactModel contact);
    List<ContactModel> getContacts();
    ContactModel getContact(long inputID);

}
