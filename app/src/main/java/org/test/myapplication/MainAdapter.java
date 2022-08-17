package org.test.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import org.test.myapplication.databinding.ItemContactBinding;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainHolder>{
    private final LifecycleOwner mOwner;
    private final ContactViewModel mContactViewModel;

    public MainAdapter(LifecycleOwner owner, Context context, ContactViewModel contactViewModel) {
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

        String contacts = mContactViewModel.getContactList().get(position);
        holder.bindContact(contacts);
    }

    @Override
    public int getItemCount() {
        return mContactViewModel.getContactList().size();
    }
    class MainHolder extends RecyclerView.ViewHolder {

        ItemContactBinding mItemContactBinding;

        public MainHolder(ItemContactBinding itemContactBinding) {
            super(itemContactBinding.getRoot());
            mItemContactBinding = itemContactBinding;
            mItemContactBinding.setLifecycleOwner(mOwner);

        }

        public void bindContact(String contact) {
            mItemContactBinding.textContact.setText(contact);
        }
    }
}
