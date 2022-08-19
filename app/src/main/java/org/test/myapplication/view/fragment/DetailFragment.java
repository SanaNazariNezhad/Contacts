package org.test.myapplication.view.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.test.myapplication.R;
import org.test.myapplication.databinding.FragmentDetailBinding;
import org.test.myapplication.model.ContactModel;
import org.test.myapplication.viewmodel.ContactViewModel;

public class DetailFragment extends Fragment {

    private static final String ARG_Contact = "contact_detail";

    private ContactModel mContact;
    private FragmentDetailBinding mDetailBinding;
    private ContactViewModel mViewModel;
    private Long mId;

    public DetailFragment() {
        // Required empty public constructor
    }

    public static DetailFragment newInstance(long contact) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_Contact, contact);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mId = getArguments().getLong(ARG_Contact);
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

        return mDetailBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ContactViewModel.class);
        initView();
    }

    private void initView() {
        mContact = mViewModel.getContact((mId));
        mViewModel.setContext(getActivity());
        mDetailBinding.setContactViewModel(mViewModel);
        mDetailBinding.idTVName.setText(mContact.getContactName());
        mDetailBinding.idTVPhoneNumber.setText(mContact.getContactNumber());
        mDetailBinding.idTVEmailAddress.setText(mContact.getContactEmail());
        mDetailBinding.setContact(mContact);
    }
}