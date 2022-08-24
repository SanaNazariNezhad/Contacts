package org.test.myapplication.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import org.test.myapplication.R;
import org.test.myapplication.databinding.FragmentNewContactBinding;
import org.test.myapplication.model.ContactModel;
import org.test.myapplication.model.Name;
import org.test.myapplication.viewmodel.ContactViewModel;

public class NewContactFragment extends Fragment {

    private FragmentNewContactBinding mBinding;
    private ContactViewModel mViewModel;
    private String[] mFullName = new String[5];
    private String mContactName;

    public NewContactFragment() {
        // Required empty public constructor
    }

    public static NewContactFragment newInstance() {

        return  new NewContactFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ContactViewModel.class);
        listener();
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

    private void listener() {
        mBinding.imageViewInsertName.setOnClickListener(view -> extractedName());
        mBinding.btnSave.setOnClickListener(view -> {
            Name name = new Name();
            if (mBinding.editTextEmail.getText().toString().trim().length() == 0
                    && mBinding.editTextPhone.getText().toString().trim().length() == 0
                    && mBinding.addName.getText().toString().trim().length() == 0
                    && mBinding.editTextNamePrefix.getText().toString().trim().length() == 0
                    && mBinding.editTextFirstName.getText().toString().trim().length() == 0
                    && mBinding.editTextMiddleName.getText().toString().trim().length() == 0
                    && mBinding.editTextLastName.getText().toString().trim().length() == 0
                    && mBinding.editTextNameSuffix.getText().toString().trim().length() == 0) {

                Toast.makeText(getActivity(), "Nothing to save. Contact discarded.", Toast.LENGTH_SHORT).show();

            } else if (mBinding.editTextNamePrefix.getText().toString().trim().length() == 0
                    && mBinding.editTextFirstName.getText().toString().trim().length() == 0
                    && mBinding.editTextMiddleName.getText().toString().trim().length() == 0
                    && mBinding.editTextLastName.getText().toString().trim().length() == 0
                    && mBinding.editTextNameSuffix.getText().toString().trim().length() == 0) {

                name.setFirst(mBinding.addName.getText().toString());
                name.setPrefix("");
                name.setMiddle("");
                name.setLast("");
                name.setSuffix("");
                ContactModel contact = new ContactModel(name, mBinding.editTextPhone.getText().toString(),
                        mBinding.editTextEmail.getText().toString());
                mViewModel.insertContact(contact);
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

                ContactModel contact = new ContactModel(name, mBinding.editTextPhone.getText().toString(),
                        mBinding.editTextEmail.getText().toString());
                mViewModel.insertContact(contact);
            }
            requireActivity().finish();
        });
        mBinding.btnCancel.setOnClickListener(view -> requireActivity().finish());
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
}