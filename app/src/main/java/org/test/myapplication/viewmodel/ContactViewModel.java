package org.test.myapplication.viewmodel;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.AndroidViewModel;

import org.test.myapplication.model.ContactModel;
import org.test.myapplication.model.repository.ContactDBRepository;
import org.test.myapplication.view.activity.CreateNewContactActivity;
import org.test.myapplication.view.activity.DetailActivity;
import org.test.myapplication.view.fragment.CreateNewContactFragment;

import java.util.ArrayList;
import java.util.List;

public class ContactViewModel extends AndroidViewModel {
    private Context mContext;
    private ContactDBRepository mRepository;

    public ContactViewModel(@NonNull Application application) {
        super(application);

        mRepository = ContactDBRepository.getInstance(application);
    }

    public void setContext(Context context) {
        mContext = context;
    }

    public List<ContactModel> getContactList() {
        return mRepository.getContacts();
    }

    public void insertContact(ContactModel contact) {
        mRepository.insertContact(contact);
    }

    public void onClickContactListItems(ContactModel contact) {
        mContext.startActivity(DetailActivity.newIntent(mContext,contact));
    }

    public void onClickCreateNewContact() {
        mContext.startActivity(CreateNewContactActivity.newIntent(mContext));
    }

    public ContactModel getContact(long id) {
        return mRepository.getContact(id);
    }

    public void sendMessage(String contactNumber) {
        // in this method we are calling an intent to send sms.
        // on below line we are passing our contact number.
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + contactNumber));
        intent.putExtra("sms_body", "Enter your message");
        mContext.startActivity(intent);
    }

    public void makeCall(String contactNumber) {
        // this method is called for making a call.
        // on below line we are calling an intent to make a call.
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        // on below line we are setting data to it.
        callIntent.setData(Uri.parse("tel:" + contactNumber));
        // on below line we are checking if the calling permissions are granted not.
        if (ActivityCompat.checkSelfPermission(mContext,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        // at last we are starting activity.
        mContext.startActivity(callIntent);
    }
}