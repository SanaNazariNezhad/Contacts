package org.test.myapplication.view.fragment;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.test.myapplication.model.ContactModel;
import org.test.myapplication.view.adapter.MainAdapter;
import org.test.myapplication.R;
import org.test.myapplication.databinding.FragmentMainBinding;
import org.test.myapplication.viewmodel.ContactViewModel;

import java.util.Objects;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class MainFragment extends Fragment {

    private ContactViewModel mViewModel;
    private FragmentMainBinding mBinding;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_main,
                container,
                false);

        initView();
        return mBinding.getRoot();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ContactViewModel.class);
        setAdapter();
        swipeRecycler();
        FAB_listener();
    }

    @Override
    public void onResume() {
        super.onResume();
        setAdapter();
    }

    @Override
    public void onPause() {
        super.onPause();
        setAdapter();
    }

    private void initView() {
        mBinding.recyclerMainFragment.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void setAdapter() {
        MainAdapter mainAdapter = new MainAdapter(this, getActivity(), mViewModel);
        mBinding.recyclerMainFragment.setAdapter(mainAdapter);
    }

    private void swipeRecycler() {
        ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                final int position = viewHolder.getAdapterPosition();
                ContactModel contact = mViewModel.getContactList().get(position);
                if (direction == ItemTouchHelper.RIGHT){
                    mViewModel.makeCall(contact);
                    Toast.makeText(getActivity(), "Call: " + contact, Toast.LENGTH_SHORT).show();
                }else {
                    mViewModel.sendMessage(contact);
                    Toast.makeText(getActivity(), "Message: " + contact, Toast.LENGTH_SHORT).show();
                }
                setAdapter();

            }

            @Override
            public void onChildDraw (Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                     float dX, float dY, int actionState, boolean isCurrentlyActive){
                new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                        .addSwipeLeftBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.blue1))
                        .addSwipeLeftActionIcon(R.drawable.ic_sms)
                        .addSwipeRightBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.light_green))
                        .addSwipeRightActionIcon(R.drawable.ic_call)
                        .addSwipeRightLabel(getString(R.string.call))
                        .setSwipeRightLabelColor(Color.WHITE)
                        .addSwipeLeftLabel(getString(R.string.message))
                        .setSwipeLeftLabelColor(Color.WHITE)
                        //.addCornerRadius(TypedValue.COMPLEX_UNIT_DIP, 16)
                        //.addPadding(TypedValue.COMPLEX_UNIT_DIP, 8, 16, 8)
                        .create()
                        .decorate();
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(mBinding.recyclerMainFragment);
    }

    private void FAB_listener() {
        mViewModel.setContext(getActivity());
        mBinding.setContactViewModel(mViewModel);
    }

}