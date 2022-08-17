package org.test.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

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
            mItemContactBinding.contactName.setText(contact);
            // on below line we are setting data to our text view.
            mItemContactBinding.contactName.setText(contact);
            ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
            // generate random color
            int color = generator.getRandomColor();

            // below text drawable is a circular.
            TextDrawable drawable2 = TextDrawable.builder().beginConfig()
                    .width(100)  // width in px
                    .height(100) // height in px
                    .endConfig()
                    // as we are building a circular drawable
                    // we are calling a build round method.
                    // in that method we are passing our text and color.
                    .buildRound(contact.substring(0, 1), color);
            // setting image to our image view on below line.
            mItemContactBinding.contactImage.setImageDrawable(drawable2);
        }
    }
}
