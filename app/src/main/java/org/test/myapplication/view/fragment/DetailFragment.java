package org.test.myapplication.view.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
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

import stream.customalert.CustomAlertDialogue;

public class DetailFragment extends Fragment {

    public static final String BUNDLE_ARG_IS_ITEM_DELETED = "isItemsDeleted";
    private static final String ARG_Contact = "contact_detail";
    public static final String FRAGMENT_TAG_EDIT = "Edit";
    public static final int REQUEST_CODE_EDIT = 0;
    private ContactModel mContact;
    private FragmentDetailBinding mDetailBinding;
    private ContactViewModel mViewModel;
    private Long mId;
    private boolean mItemsSelected = false;

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
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(BUNDLE_ARG_IS_ITEM_DELETED, mItemsSelected);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mId = getArguments().getLong(ARG_Contact);
        }
        if (savedInstanceState != null)
            if (savedInstanceState.getBoolean(BUNDLE_ARG_IS_ITEM_DELETED)) {
                deleteContact();
                mItemsSelected = true;
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

        int nightModeFlags = mDetailBinding.getRoot().getContext().getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        if (nightModeFlags != Configuration.UI_MODE_NIGHT_YES) {
            mDetailBinding.idIVContact.setBackgroundColor(mDetailBinding.getRoot().getResources().getColor(R.color.blue_500));
            mDetailBinding.idTVName.setBackgroundColor(mDetailBinding.getRoot().getResources().getColor(R.color.blue_500));
            mDetailBinding.idIVCall.setColorFilter(ContextCompat.getColor(requireActivity(), R.color.blue_500), android.graphics.PorterDuff.Mode.SRC_IN);
            mDetailBinding.idIVMessage.setColorFilter(ContextCompat.getColor(requireActivity(), R.color.blue_500), android.graphics.PorterDuff.Mode.SRC_IN);
            mDetailBinding.idIVEmail.setColorFilter(ContextCompat.getColor(requireActivity(), R.color.blue_500), android.graphics.PorterDuff.Mode.SRC_IN);
        }
        return mDetailBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ContactViewModel.class);
        mViewModel.setContactsUnSelected();
        initView();
        listener();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode != Activity.RESULT_OK || data == null)
            return;

        if (requestCode == REQUEST_CODE_EDIT) {
            initView();
        }
    }

    private void initView() {
        mContact = mViewModel.getContact((mId));
        mViewModel.setContext(getActivity());
        mDetailBinding.setContactViewModel(mViewModel);
        mDetailBinding.setContact(mContact);
    }

    private void listener() {
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
                deleteContact();
                mItemsSelected = true;
            }
            return true;
        });
    }

    private void deleteContact() {
        CustomAlertDialogue.Builder alert = new CustomAlertDialogue.Builder(getActivity())
                .setStyle(CustomAlertDialogue.Style.DIALOGUE)
                .setCancelable(false)
                .setTitle(getString(R.string.delete_contact))
                .setMessage(getString(R.string.are_you_sure))
                .setPositiveText(getString(R.string.yes))
                .setPositiveColor(R.color.negative)
                .setPositiveTypeface(Typeface.DEFAULT_BOLD)
                .setOnPositiveClicked(new CustomAlertDialogue.OnPositiveClicked() {
                    @Override
                    public void OnClick(View view, Dialog dialog) {
                        mViewModel.deleteContact(mContact);
                        dialog.dismiss();
                        requireActivity().finish();
                    }
                })
                .setNegativeText(getString(R.string.no))
                .setNegativeColor(R.color.positive)
                .setOnNegativeClicked(new CustomAlertDialogue.OnNegativeClicked() {
                    @Override
                    public void OnClick(View view, Dialog dialog) {
                        mItemsSelected = false;
                        dialog.dismiss();
                    }
                })
                .setDecorView(getActivity().getWindow().getDecorView())
                .build();
        alert.show();
    }

}