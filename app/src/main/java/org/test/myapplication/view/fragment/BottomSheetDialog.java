package org.test.myapplication.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
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
    private ContactViewModel mViewModel;
    private ContactModel mContact;
    private BottomSheetShareBinding mShareBinding;
    public static final String AUTHORITY = "org.test.myapplication.fileProvider";

    public static BottomSheetDialog newInstance(long contactId) {
        BottomSheetDialog fragment = new BottomSheetDialog();
        Bundle args = new Bundle();
        args.putLong(CONTACT_ID,contactId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable
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
        mId = getArguments().getLong(CONTACT_ID);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ContactViewModel.class);
        mContact = mViewModel.getContact(mId);
        listeners();
    }

    private void listeners() {
        mShareBinding.tvFileShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                //TODO share vcf file
                    //exportVCF();

                    Toast.makeText(getActivity(),
                                    "File Share has error!", Toast.LENGTH_SHORT)
                            .show();


            }
        });

        mShareBinding.tvTextShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                shareContactIntent();
                dismiss();
            }
        });
    }

    //TODO share vcf file

    /*private void exportVCF() throws IOException {
        VCard vCard = new VCard();

        vCard.setFormattedName(mContact.getContactName());
        Email email = new Email(mContact.getContactEmail());
        vCard.addEmail(email);
        Telephone telephone = new Telephone(mContact.getContactNumber());
        vCard.addTelephoneNumber(telephone);
        File file = new File(requireActivity().getFilesDir(),"vcard.vcf");
        Ezvcard.write(vCard).go(file);
        Uri vcfUri = generateUriForFile(file);
        sendVcfFile(file,vcfUri);
    }

    private Uri generateUriForFile(File file) {
        return FileProvider.getUriForFile(
                getContext(),
                AUTHORITY,
                file);
    }

    private void grantWriteUriToAllResolvedActivities(Intent emailIntent, Uri emailUri) {
        List<ResolveInfo> activities = getActivity().getPackageManager()
                .queryIntentActivities(
                        emailIntent,
                        PackageManager.MATCH_DEFAULT_ONLY);

        for (ResolveInfo activity: activities) {
            getActivity().grantUriPermission(
                    activity.activityInfo.packageName,
                    emailUri,
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        }
    }

    public void sendVcfFile(File file,Uri uri) {

        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.putExtra(Intent.EXTRA_EMAIL, mContact.getContactEmail());
        emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
        grantWriteUriToAllResolvedActivities(emailIntent,uri);
        getActivity().startActivity(Intent.createChooser(emailIntent,"Choose an Email client :"));

    }*/

    private void shareContactIntent() {

        ShareCompat.IntentBuilder intentBuilder = ShareCompat.IntentBuilder.from(getActivity());
        Intent intent = intentBuilder
                .setType("text/plain")
                .setText(shareWord(mContact.getContactName(),mContact.getContactNumber(),
                        mContact.getContactEmail()))
                .setChooserTitle(getString(R.string.contact_sharing_massage))
                .createChooserIntent();

        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public String shareWord(String name, String number, String email) {

        String shareMassage = getString(
                R.string.share_contact,
                name,
                number,
                email);

        return shareMassage;
    }
}

