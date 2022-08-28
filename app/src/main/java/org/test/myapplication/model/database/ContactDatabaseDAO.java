package org.test.myapplication.model.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import org.test.myapplication.model.ContactModel;

import java.util.List;

@Dao
public interface ContactDatabaseDAO {

    @Update
    void updateContact(ContactModel contact);

    @Insert
    void insertContact(ContactModel contact);

    @Delete
    void deleteContact(ContactModel contact);

    @Query("SELECT * FROM contactTable")
    List<ContactModel> getContacts();

    @Query("SELECT * FROM contactTable WHERE prefix LIKE '%' || :word || '%' OR first LIKE '%' || :word || '%' OR middle LIKE '%' || :word || '%' OR last LIKE '%' || :word || '%' OR suffix LIKE '%' || :word || '%' OR contactNumber LIKE '%' || :word || '%' OR contactEmail LIKE '%' || :word || '%'")
    List<ContactModel> searchContacts(String word);

    @Query("SELECT * FROM contactTable WHERE id =:inputID")
    ContactModel getContact(long inputID);

    @Query("UPDATE contactTable SET selectedContact = 1")
    void setContactsSelected();

    @Query("UPDATE contactTable SET selectedContact = 0")
    void setContactsUnSelected();

    @Query("DELETE FROM contactTable WHERE selectedContact = 1")
    void deleteSelectedContact();

    @Query("UPDATE contactTable SET selectedContact = 1 WHERE id =:contact_id")
    void setContactSelected(long contact_id);

    @Query("UPDATE contactTable SET selectedContact = 0 WHERE id =:contact_id")
    void setContactUnSelected(long contact_id);

    @Query("SELECT COUNT(selectedContact) FROM contactTable WHERE selectedContact = 1")
    int getNumberOfSelectedContacts();

}
