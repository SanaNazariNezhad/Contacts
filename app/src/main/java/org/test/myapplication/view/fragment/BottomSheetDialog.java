package org.test.myapplication.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ShareCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import org.test.myapplication.R;
import org.test.myapplication.databinding.BottomSheetShareBinding;
import org.test.myapplication.model.ContactModel;
import org.test.myapplication.viewmodel.ContactViewModel;

public class BottomSheetDialog extends BottomSheetDialogFragment {
    public static final String CONTACT_ID = "ContactId";
    private long mId;
    private ContactModel mContact;
    private ContactViewModel mViewModel;
    private BottomSheetShareBinding mShareBinding;

    public static BottomSheetDialog newInstance(long contactId) {
        BottomSheetDialog fragment = new BottomSheetDialog();
        Bundle args = new Bundle();
        args.putLong(CONTACT_ID,contactId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable
            ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        mShareBinding = DataBindingUtil.inflate(inflater,
                R.layout.bottom_sheet_share,
                container,
                false);
        return mShareBinding.getRoot();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mId = getArguments().getLong(CONTACT_ID);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ContactViewModel.class);
        mContact = mViewModel.getContact(mId);
        listeners();
    }

    private void listeners() {
        mShareBinding.tvFileShare.setOnClickListener(v -> {
            //TODO share vcf file
                //exportVCF();
            Toast.makeText(getActivity(),
                                "File Share has error!", Toast.LENGTH_SHORT)
                        .show();
        });

        mShareBinding.tvTextShare.setOnClickListener(v -> {
            shareContactIntent();
            dismiss();
        });
    }

    private void shareContactIntent() {

        ShareCompat.IntentBuilder intentBuilder = new ShareCompat.IntentBuilder(requireActivity());
        Intent intent = intentBuilder
                .setType("text/plain")
                .setText(shareWord(mViewModel.getContactFullName(mContact), mContact.getContactNumber(),
                        mContact.getContactEmail()))
                .setChooserTitle(getString(R.string.contact_sharing_massage))
                .createChooserIntent();

        if (intent.resolveActivity(requireActivity().getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public String shareWord(String name, String number, String email) {

        return getString(
                R.string.share_contact,
                name,
                number,
                email);
    }
}

