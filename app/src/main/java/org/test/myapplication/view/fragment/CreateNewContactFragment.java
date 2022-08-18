package org.test.myapplication.view.fragment;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.test.myapplication.R;
import org.test.myapplication.databinding.FragmentCreateNewContactBinding;

public class CreateNewContactFragment extends Fragment {

    private FragmentCreateNewContactBinding mNewContactBinding;

    public CreateNewContactFragment() {
        // Required empty public constructor
    }

    public static CreateNewContactFragment newInstance() {

        return  new CreateNewContactFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        mNewContactBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_create_new_contact,
                container,
                false);

        return mNewContactBinding.getRoot();
    }
}