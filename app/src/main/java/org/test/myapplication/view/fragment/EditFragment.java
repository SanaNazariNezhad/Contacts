package org.test.myapplication.view.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import org.test.myapplication.R;
import org.test.myapplication.databinding.FragmentEditBinding;
import org.test.myapplication.model.ContactModel;
import org.test.myapplication.viewmodel.ContactViewModel;

public class EditFragment extends DialogFragment {
    public static final String KEY_VALUE_CONTACT_ID = "key_value_ContactId";
    private FragmentEditBinding mBinding;
    private ContactViewModel mViewModel;
    private long mId;
    private ContactModel mContact;

    public EditFragment() {
        // Required empty public constructor
    }

    public static EditFragment newInstance(long contactId) {
        EditFragment fragment = new EditFragment();
        Bundle args = new Bundle();
        args.putLong(KEY_VALUE_CONTACT_ID,contactId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mId = getArguments().getLong(KEY_VALUE_CONTACT_ID);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ContactViewModel.class);
        mContact = mViewModel.getContact(mId);
        listeners();
        initView();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_edit,
                container,
                false);

        return mBinding.getRoot();
    }

    private void initView() {
        mBinding.nameEdit.setText(mContact.getContactName());
        mBinding.mobileEdit.setText(mContact.getContactNumber());
        mBinding.emailEdit.setText(mContact.getContactEmail());
    }

    private void listeners() {
        mBinding.btnCancelEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        mBinding.btnSaveEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateInput()) {
                    sendResult();
                    dismiss();
                } else {
                    checkInput();
                }
            }
        });
    }

    private void checkInput() {
        mBinding.nameFormEdit.setErrorEnabled(false);
        mBinding.mobileFormEdit.setErrorEnabled(false);
        mBinding.emailFormEdit.setErrorEnabled(false);
        if (mBinding.nameEdit.getText().toString().trim().isEmpty()) {
            mBinding.nameFormEdit.setErrorEnabled(true);
            mBinding.nameFormEdit.setError("Field cannot be empty!");
        }
        if (mBinding.mobileEdit.getText().toString().trim().isEmpty()) {
            mBinding.mobileFormEdit.setErrorEnabled(true);
            mBinding.mobileFormEdit.setError("Field cannot be empty!");
        }
        if (mBinding.emailEdit.getText().toString().trim().isEmpty()) {
            mBinding.emailFormEdit.setErrorEnabled(true);
            mBinding.emailFormEdit.setError("Field cannot be empty!");
        }
    }

    private boolean validateInput() {
        return !(mBinding.nameEdit.getText().toString().trim().isEmpty() &&
                mBinding.mobileEdit.getText().toString().trim().isEmpty() &&
                mBinding.emailEdit.getText().toString().trim().isEmpty());
    }

    private void sendResult() {
        Fragment fragment = getTargetFragment();
        int requestCode = getTargetRequestCode();
        int resultCode = Activity.RESULT_OK;
        Intent intent = new Intent();
        mContact.setContactName(mBinding.nameEdit.getText().toString());
        mContact.setContactNumber(mBinding.mobileEdit.getText().toString());
        mContact.setContactEmail(mBinding.emailEdit.getText().toString());

        mViewModel.updateContact(mContact);

        fragment.onActivityResult(requestCode, resultCode, intent);
    }
}