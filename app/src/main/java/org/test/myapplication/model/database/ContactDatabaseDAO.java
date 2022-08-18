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

    @Query("SELECT * FROM contactTable WHERE id =:inputID")
    ContactModel getContact(long inputID);

}
