package org.test.myapplication.model.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import org.test.myapplication.model.ContactModel;

@Database(entities = {ContactModel.class}, version = 1)
public abstract class ContactDatabase extends RoomDatabase {

    public abstract ContactDatabaseDAO getContactDatabaseDAO();
}
