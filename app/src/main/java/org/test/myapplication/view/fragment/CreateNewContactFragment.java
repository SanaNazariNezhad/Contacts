package org.test.myapplication.view.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import org.test.myapplication.R;
import org.test.myapplication.databinding.FragmentCreateNewContactBinding;
import org.test.myapplication.model.ContactModel;
import org.test.myapplication.viewmodel.ContactViewModel;

public class CreateNewContactFragment extends Fragment {

    private FragmentCreateNewContactBinding mBinding;
    private ContactViewModel mViewModel;

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
                R.layout.fragment_create_new_contact,
                container,
                false);

        return mBinding.getRoot();
    }

    private void listener() {
        mBinding.btnSave.setOnClickListener(view -> {
            if (mBinding.contactEmail.getText().toString().trim().length() == 0
            && mBinding.contactName.getText().toString().trim().length() == 0
            && mBinding.contactNumber.getText().toString().trim().length() == 0){

                Toast.makeText(getActivity(), "Nothing to save. Contact discarded.", Toast.LENGTH_SHORT).show();

            } else{
                ContactModel contact = new ContactModel(mBinding.contactName.getText().toString(),
                        mBinding.contactNumber.getText().toString(),mBinding.contactEmail.getText().toString());
                mViewModel.insertContact(contact);
            }
            requireActivity().finish();
        });
        mBinding.btnCancel.setOnClickListener(view -> requireActivity().finish());
    }
}