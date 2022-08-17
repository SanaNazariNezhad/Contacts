package org.test.myapplication;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class MainActivity extends SingleFragmentActivity {

    @Override
    public Fragment createFragment() {
        return MainFragment.newInstance();
    }
}

