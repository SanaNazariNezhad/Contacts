package org.test.myapplication;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class ContactViewModel extends AndroidViewModel {
    private List<String> mContactsList = new ArrayList<>();
    private Context mContext;
    // TODO: Implement the ViewModel


    public ContactViewModel(@NonNull Application application) {
        super(application);
        mContactsList.add("Sana");
        mContactsList.add("Zahra");
        mContactsList.add("Yegane");
        mContactsList.add("Mehmet");
        mContactsList.add("Baris");
        mContactsList.add("Fereshte");
        mContactsList.add("Soraya");
        mContactsList.add("Poyraz");
        mContactsList.add("Aysa");
        mContactsList.add("John");
    }

    public void setContext(Context context) {
        mContext = context;
    }

    public List<String> getContactList() {
        return mContactsList;
    }
}