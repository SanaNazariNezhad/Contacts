package org.test.myapplication.view.activity;

import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;

import org.test.myapplication.model.ContactModel;
import org.test.myapplication.view.fragment.DetailFragment;

public class DetailActivity extends SingleFragmentActivity {

    public static final String EXTRA_Contact = "extra_contact_id";

    public static Intent newIntent (Context context, ContactModel contact){
        Intent intent = new Intent(context,DetailActivity.class);
        intent.putExtra(EXTRA_Contact,contact.getPrimaryId());
        return intent;
    }

    @Override
    public Fragment createFragment() {
        return DetailFragment.newInstance(getIntent().getLongExtra(EXTRA_Contact,-1));

    }

}