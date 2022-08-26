package org.test.myapplication.view.adapter;

import android.content.Context;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;
import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import org.test.myapplication.R;
import org.test.myapplication.databinding.ItemContactBinding;
import org.test.myapplication.model.ContactModel;
import org.test.myapplication.viewmodel.ContactViewModel;
import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainHolder>{
    private final LifecycleOwner mOwner;
    private final ContactViewModel mContactViewModel;
    private final List<ContactModel> mContactList;
    public MainAdapter(LifecycleOwner owner, Context context, ContactViewModel contactViewModel, List<ContactModel> contactList) {
        mContactList = contactList;
        mOwner = owner;
        mContactViewModel = contactViewModel;
        mContactViewModel.setContext(context);
    }

    @NonNull
    @Override
    public MainHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mContactViewModel.getApplication());
        ItemContactBinding itemContactBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.item_contact,
                parent,
                false);

        return new MainHolder(itemContactBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MainAdapter.MainHolder holder, int position) {

        ContactModel contact = mContactList.get(position);
        holder.bindContact(contact);
    }

    @Override
    public int getItemCount() {
        return mContactList.size();
    }
    class MainHolder extends RecyclerView.ViewHolder {

        ItemContactBinding mItemContactBinding;

        public MainHolder(ItemContactBinding itemContactBinding) {
            super(itemContactBinding.getRoot());
            mItemContactBinding = itemContactBinding;
            mItemContactBinding.setLifecycleOwner(mOwner);
            mItemContactBinding.setContactViewModel(mContactViewModel);
            int nightModeFlags =  mItemContactBinding.getRoot().getContext().getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
            if (nightModeFlags == Configuration.UI_MODE_NIGHT_YES)
                mItemContactBinding.contactNameRecycler.setTextColor(mItemContactBinding.getRoot().getResources().getColor(R.color.white));

        }

        public void bindContact(ContactModel contact) {
            mItemContactBinding.getRoot().setOnLongClickListener(view -> {
                mContactViewModel.onLongClickContactListItems(contact);
                return true;
            });
            mItemContactBinding.setContact(contact);

            ColorGenerator generator = ColorGenerator.MATERIAL;
            int color = generator.getRandomColor();
            TextDrawable drawable = null;
            String name = mContactViewModel.getContactFullName(contact);
            mItemContactBinding.checkbox.setChecked(contact.getCheck_Select() == 1);
            if (contact.getCheck_Select() == 0)
                mItemContactBinding.checkbox.setVisibility(View.GONE);
            if (!name.isEmpty()) {
                mItemContactBinding.contactNameRecycler.setText(name);
                drawable = getTextDrawable(name, color);
            }
            else if (!contact.getContactNumber().trim().isEmpty()){
                mItemContactBinding.contactNameRecycler.setText(contact.getContactNumber());
                drawable = getTextDrawable(contact.getContactNumber(), color);

            }else if (!contact.getContactEmail().trim().isEmpty()){
                mItemContactBinding.contactNameRecycler.setText(contact.getContactEmail());
                drawable = getTextDrawable(contact.getContactEmail(), color);

            }
            // setting image to our image view on below line.
            mItemContactBinding.contactImage.setImageDrawable(drawable);
        }

        private TextDrawable getTextDrawable(String title, int color) {
            TextDrawable drawable;
            // below text drawable is a circular.
            drawable = TextDrawable.builder().beginConfig()
                    .width(100)  // width in px
                    .height(100) // height in px
                    .endConfig()
                    // as we are building a circular drawable
                    // we are calling a build round method.
                    // in that method we are passing our text and color.
                    .buildRound(title.substring(0, 1), color);
            return drawable;
        }

    }
}
