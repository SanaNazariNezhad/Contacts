package org.test.myapplication.view.activity;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;
import org.test.myapplication.view.fragment.CreateNewContactFragment;

public class CreateNewContactActivity extends SingleFragmentActivity {

    public static Intent newIntent (Context context){

        return new Intent(context,CreateNewContactActivity.class);
    }

    @Override
    public Fragment createFragment() {
        return CreateNewContactFragment.newInstance();
    }

}