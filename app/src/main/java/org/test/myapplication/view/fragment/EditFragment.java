package org.test.myapplication.view.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import org.test.myapplication.R;
import org.test.myapplication.databinding.FragmentNewContactBinding;
import org.test.myapplication.model.ContactModel;
import org.test.myapplication.model.Name;
import org.test.myapplication.viewmodel.ContactViewModel;
import java.util.Objects;

public class EditFragment extends DialogFragment {
    public static final String KEY_VALUE_CONTACT_ID = "key_value_ContactId";
    private FragmentNewContactBinding mBinding;
    private ContactViewModel mViewModel;
    private String[] mFullName = new String[5];
    private long mId;
    private ContactModel mContact;
    private String mContactName;

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
        if (getArguments() != null) {
            mId = getArguments().getLong(KEY_VALUE_CONTACT_ID);
        }
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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_new_contact,
                container,
                false);

        return mBinding.getRoot();
    }

    @Override
    public void onStart()
    {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null)
        {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }

    private void initView() {
        mBinding.addName.setText(mViewModel.getContactFullName(mContact));
        mBinding.editTextNamePrefix.setText(mContact.getContactName().getPrefix());
        mBinding.editTextFirstName.setText(mContact.getContactName().getFirst());
        mBinding.editTextMiddleName.setText(mContact.getContactName().getMiddle());
        mBinding.editTextLastName.setText(mContact.getContactName().getLast());
        mBinding.editTextNameSuffix.setText(mContact.getContactName().getSuffix());
        mBinding.editTextPhone.setText(mContact.getContactNumber());
        mBinding.editTextEmail.setText(mContact.getContactEmail());
    }

    private void listeners() {
        mBinding.imageViewInsertName.setOnClickListener(view -> extractedName());
        mBinding.btnCancel.setOnClickListener(view -> dismiss());
        mBinding.btnSave.setOnClickListener(view -> {
            if (!validateInput()) {
                sendResult();
                dismiss();
            } else {
                Toast.makeText(getActivity(), "There is no information to save.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void extractedName() {
        if (mBinding.layoutAddFullName.getVisibility() == View.VISIBLE){
            mBinding.layoutAddFullName.setVisibility(View.GONE);
            mBinding.addName.setVisibility(View.VISIBLE);
            mContactName = checkName();
            mBinding.addName.setText(mContactName);
            mBinding.imageViewInsertName.setImageResource(R.drawable.ic_arrow_drop_down);
        } else{
            if (!checkName().equals(mBinding.addName.getText().toString())){
                mBinding.editTextFirstName.setText(mBinding.addName.getText().toString());
                mBinding.editTextMiddleName.setText("");
                mBinding.editTextLastName.setText("");
                mBinding.editTextNamePrefix.setText("");
                mBinding.editTextNameSuffix.setText("");
                mContactName = mBinding.addName.getText().toString();
            }
            mBinding.addName.setVisibility(View.GONE);
            mBinding.layoutAddFullName.setVisibility(View.VISIBLE);
            mBinding.imageViewInsertName.setImageResource(R.drawable.ic_arrow_drop_up);
        }
    }

    private String checkName() {
        String name;

        if (mBinding.editTextNamePrefix.getText().toString().trim().isEmpty() &&
                mBinding.editTextFirstName.getText().toString().trim().isEmpty() &&
                mBinding.editTextMiddleName.getText().toString().trim().isEmpty() &&
                mBinding.editTextLastName.getText().toString().trim().isEmpty() &&
                mBinding.editTextNameSuffix.getText().toString().trim().isEmpty()) {
            name = "";
        }else if (mBinding.editTextNameSuffix.getText().toString().trim().isEmpty()){
            String[] extract = extractNameFromEditText();
            name = extract[0] + " " + extract[1] + " " + extract[2] + " " + extract[3];
        }else {
            String[] extract = extractNameFromEditText();
            name = extract[0] + " " + extract[1] + " " + extract[2] + " " + extract[3] + ", " + extract[4];
        }

        return name;
    }

    private String[] extractNameFromEditText(){
        mFullName[0] = mBinding.editTextNamePrefix.getText().toString();
        mFullName[1] = mBinding.editTextFirstName.getText().toString();
        mFullName[2] = mBinding.editTextMiddleName.getText().toString();
        mFullName[3] = mBinding.editTextLastName.getText().toString();
        mFullName[4] = mBinding.editTextNameSuffix.getText().toString();

        return mFullName;
    }

    private boolean validateInput() {
        return mBinding.editTextEmail.getText().toString().trim().length() != 0
                && mBinding.editTextPhone.getText().toString().trim().length() != 0
                && mBinding.addName.getText().toString().trim().length() != 0
                && mBinding.editTextNamePrefix.getText().toString().trim().length() != 0
                && mBinding.editTextFirstName.getText().toString().trim().length() != 0
                && mBinding.editTextMiddleName.getText().toString().trim().length() != 0
                && mBinding.editTextLastName.getText().toString().trim().length() != 0
                && mBinding.editTextNameSuffix.getText().toString().trim().length() != 0;
    }

    private void sendResult() {
        Fragment fragment = getTargetFragment();
        int requestCode = getTargetRequestCode();
        int resultCode = Activity.RESULT_OK;
        Intent intent = new Intent();
        Name name = new Name();
        if (mBinding.editTextEmail.getText().toString().trim().length() == 0
                && mBinding.editTextPhone.getText().toString().trim().length() == 0
                && mBinding.addName.getText().toString().trim().length() == 0) {

            Toast.makeText(getActivity(), "There is no information to save.", Toast.LENGTH_SHORT).show();

        }else if (mBinding.editTextEmail.getText().toString().trim().length() == 0
                && mBinding.editTextPhone.getText().toString().trim().length() == 0
                && mBinding.editTextNamePrefix.getText().toString().trim().length() == 0
                && mBinding.editTextFirstName.getText().toString().trim().length() == 0
                && mBinding.editTextMiddleName.getText().toString().trim().length() == 0
                && mBinding.editTextLastName.getText().toString().trim().length() == 0
                && mBinding.editTextNameSuffix.getText().toString().trim().length() == 0){

            Toast.makeText(getActivity(), "There is no information to save.", Toast.LENGTH_SHORT).show();

        }else if (!checkName().equals(mBinding.addName.getText().toString())) {

            name.setFirst(mBinding.addName.getText().toString());
            name.setPrefix("");
            name.setMiddle("");
            name.setLast("");
            name.setSuffix("");
            mContact.setContactName(name);
            mContact.setContactNumber(Objects.requireNonNull(mBinding.editTextPhone.getText()).toString());
            mContact.setContactEmail(Objects.requireNonNull(mBinding.editTextEmail.getText()).toString());
        } else {
            if (mBinding.editTextNamePrefix.getText().toString().trim().length() == 0)
                name.setPrefix("");
            else
                name.setPrefix(mBinding.editTextNamePrefix.getText().toString());

            if (mBinding.editTextFirstName.getText().toString().trim().length() == 0)
                name.setFirst("");
            else
                name.setFirst(mBinding.editTextFirstName.getText().toString());
            if (mBinding.editTextMiddleName.getText().toString().trim().length() == 0)
                name.setMiddle("");
            else
                name.setMiddle(mBinding.editTextMiddleName.getText().toString());
            if (mBinding.editTextLastName.getText().toString().trim().length() == 0)
                name.setLast("");
            else
                name.setLast(mBinding.editTextLastName.getText().toString());
            if (mBinding.editTextNameSuffix.getText().toString().trim().length() == 0)
                name.setSuffix("");
            else
                name.setSuffix(mBinding.editTextNameSuffix.getText().toString());

            mContact.setContactName(name);
            mContact.setContactNumber(Objects.requireNonNull(mBinding.editTextPhone.getText()).toString());
            mContact.setContactEmail(Objects.requireNonNull(mBinding.editTextEmail.getText()).toString());

        }
        mViewModel.updateContact(mContact);

        if (fragment != null) {
            fragment.onActivityResult(requestCode, resultCode, intent);
        }
    }
}