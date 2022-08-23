package org.test.myapplication.viewmodel;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.AndroidViewModel;
import org.test.myapplication.model.ContactModel;
import org.test.myapplication.model.repository.ContactDBRepository;
import org.test.myapplication.utils.QueryPreferences;
import org.test.myapplication.view.activity.CreateNewContactActivity;
import org.test.myapplication.view.activity.DetailActivity;
import java.util.List;

public class ContactViewModel extends AndroidViewModel {
    private Context mContext;
    private final ContactDBRepository mRepository;

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

    public ContactModel getContact(long id) {
        return mRepository.getContact(id);
    }

    public void insertContact(ContactModel contact) {
        mRepository.insertContact(contact);
    }

    public void deleteContact(ContactModel contact) {
        mRepository.deleteContact(contact);
    }

    public void updateContact(ContactModel contact) {
        mRepository.updateContact(contact);
    }

    public void onClickContactListItems(ContactModel contact) {
        mContext.startActivity(DetailActivity.newIntent(mContext,contact));
    }

    public void onClickCreateNewContact() {
        mContext.startActivity(CreateNewContactActivity.newIntent(mContext));
    }

    public void sendMessage(ContactModel contact) {
        // in this method we are calling an intent to send sms.
        // on below line we are passing our contact number.
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + contact.getContactNumber()));
        intent.putExtra("sms_body", "Enter your message");
        mContext.startActivity(intent);
    }

    public void sendEmail(ContactModel contact) {
        // in this method we are calling an intent to send email.
        // on below line we are passing our contact email.
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto:" + contact.getContactEmail()));
        emailIntent.putExtra(Intent.EXTRA_EMAIL, contact.getContactEmail());
        mContext.startActivity(Intent.createChooser(emailIntent,"Choose an Email client :"));
    }

    public void makeCall(ContactModel contact) {
        // this method is called for making a call.
        // on below line we are calling an intent to make a call.
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        // on below line we are setting data to it.
        callIntent.setData(Uri.parse("tel:" + contact.getContactNumber()));
        // on below line we are checking if the calling permissions are granted not.
        if (ActivityCompat.checkSelfPermission(mContext,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        // at last we are starting activity.
        mContext.startActivity(callIntent);
    }

    public List<ContactModel> searchContact(String query){
        return mRepository.searchContacts(query);
    }

    public void setQueryInPreferences(String query) {
        QueryPreferences.setSearchQuery(getApplication(), query);
    }

    public String getQueryFromPreferences() {
        return QueryPreferences.getSearchQuery(getApplication());
    }
}