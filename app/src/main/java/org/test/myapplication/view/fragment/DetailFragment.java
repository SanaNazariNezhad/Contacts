package org.test.myapplication.view.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
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
    public static final String FRAGMENT_TAG_EDIT = "Edit";
    public static final int REQUEST_CODE_EDIT = 0;
    public static final String FRAGMENT_TAG_DELETE = "Delete";
    public static final int REQUEST_CODE_DELETE = 1;

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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
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
        listener();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode != Activity.RESULT_OK || data == null)
            return;

        if (requestCode == REQUEST_CODE_EDIT) {
            initView();
        }else if (requestCode == REQUEST_CODE_DELETE){
            requireActivity().finish();
        }
    }

    private void initView() {
        mContact = mViewModel.getContact((mId));
        mViewModel.setContext(getActivity());
        mDetailBinding.setContactViewModel(mViewModel);
        mDetailBinding.idTVName.setText(mViewModel.getContactFullName(mContact));
        mDetailBinding.idTVPhoneNumber.setText(mContact.getContactNumber());
        mDetailBinding.idTVEmailAddress.setText(mContact.getContactEmail());
        mDetailBinding.setContact(mContact);
    }

    private void listener(){
        mDetailBinding.bottomNavigation.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.share_menu) {
                BottomSheetDialog bottomSheet = BottomSheetDialog.newInstance(mContact.getPrimaryId());
                bottomSheet.show(requireActivity().getSupportFragmentManager(),
                        "BottomSheetShare");
            } else if (itemId == R.id.edit_menu) {
                EditFragment editFragment = EditFragment.newInstance(mContact.getPrimaryId());
                editFragment.setTargetFragment(
                        DetailFragment.this,
                        REQUEST_CODE_EDIT);
                editFragment.show(
                        requireActivity().getSupportFragmentManager(),
                        FRAGMENT_TAG_EDIT);
            } else if (itemId == R.id.delete_menu) {
                DeleteFragment deleteFragment = DeleteFragment.newInstance(mContact.getPrimaryId());
                deleteFragment.setTargetFragment(
                        DetailFragment.this,
                        REQUEST_CODE_DELETE);
                deleteFragment.show(
                        requireActivity().getSupportFragmentManager(),
                        FRAGMENT_TAG_DELETE);
            }
            return true;
        });
    }

}