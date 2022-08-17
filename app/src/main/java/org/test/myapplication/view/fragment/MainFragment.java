package org.test.myapplication.view.fragment;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.test.myapplication.view.adapter.MainAdapter;
import org.test.myapplication.R;
import org.test.myapplication.databinding.FragmentMainBinding;
import org.test.myapplication.viewmodel.ContactViewModel;

public class MainFragment extends Fragment {

    private ContactViewModel mViewModel;
    private FragmentMainBinding mBinding;
    private MainAdapter mMainAdapter;

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
        // TODO: Use the ViewModel
        setAdapter();
    }

    private void setAdapter() {
        mMainAdapter = new MainAdapter(this,getActivity(),mViewModel);
        mBinding.recyclerMainFragment.setAdapter(mMainAdapter);
    }

    private void initView() {
        mBinding.recyclerMainFragment.setLayoutManager(new LinearLayoutManager(getContext()));
    }

}