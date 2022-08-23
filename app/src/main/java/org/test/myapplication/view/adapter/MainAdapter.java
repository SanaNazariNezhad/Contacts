package org.test.myapplication.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
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

import java.util.ArrayList;
import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainHolder>{
    private final LifecycleOwner mOwner;
    private final ContactViewModel mContactViewModel;
    private List<ContactModel> mContactList;
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

        MainHolder mainHolder = new MainHolder(itemContactBinding);
        return mainHolder;
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

        }

        public void bindContact(ContactModel contact) {
            mItemContactBinding.setContact(contact);
            mItemContactBinding.contactName.setText(contact.getContactName());
            // on below line we are setting data to our text view.
            ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
            // generate random color
            int color = generator.getRandomColor();
            TextDrawable drawable = null;
            if (!contact.getContactName().toString().trim().isEmpty()) {

                drawable = getTextDrawable(contact.getContactName(), color);
            }
            else if (!contact.getContactNumber().toString().trim().isEmpty()){
                drawable = getTextDrawable(contact.getContactNumber(), color);

            }else if (!contact.getContactEmail().toString().trim().isEmpty()){
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
