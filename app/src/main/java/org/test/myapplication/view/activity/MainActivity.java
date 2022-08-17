package org.test.myapplication.view.activity;

import androidx.fragment.app.Fragment;

import org.test.myapplication.view.fragment.MainFragment;

public class MainActivity extends SingleFragmentActivity {

    @Override
    public Fragment createFragment() {
        return MainFragment.newInstance();
    }
}

