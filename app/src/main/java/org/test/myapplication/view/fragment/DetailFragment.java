package org.test.myapplication.view.fragment;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.test.myapplication.R;
import org.test.myapplication.databinding.FragmentDetailBinding;

public class DetailFragment extends Fragment {

    private static final String ARG_Contact = "contact_detail";

    private String mContact;
    private FragmentDetailBinding mDetailBinding;

    public DetailFragment() {
        // Required empty public constructor
    }

    public static DetailFragment newInstance(String contact) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_Contact, contact);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mContact = getArguments().getString(ARG_Contact);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mDetailBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_detail,
                container,
                false);

        initView();
        return mDetailBinding.getRoot();
    }

    private void initView() {
        mDetailBinding.idTVName.setText(mContact);
    }
}