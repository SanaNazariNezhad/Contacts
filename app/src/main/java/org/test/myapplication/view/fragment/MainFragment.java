package org.test.myapplication.view.fragment;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import android.app.Dialog;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import org.test.myapplication.model.ContactModel;
import org.test.myapplication.view.adapter.MainAdapter;
import org.test.myapplication.R;
import org.test.myapplication.databinding.FragmentMainBinding;
import org.test.myapplication.viewmodel.ContactViewModel;

import java.util.List;
import java.util.Objects;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;
import stream.customalert.CustomAlertDialogue;

public class MainFragment extends Fragment {

    public static final String BUNDLE_ARG_IS_ITEM_SELECTED = "isItemsSelected";
    public static final String BUNDLE_IS_CLICK_DELETE = "isClickDelete";
    private ContactViewModel mViewModel;
    private FragmentMainBinding mBinding;
    private SearchView mSearchView;
    private LiveData<Boolean> mSelectedItemsLiveData;
    private boolean mClick = false;

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
        if (savedInstanceState != null) {
            if (savedInstanceState.getInt(BUNDLE_ARG_IS_ITEM_SELECTED) != 0) {
                if (savedInstanceState.getBoolean(BUNDLE_IS_CLICK_DELETE))
                    deleteContact(savedInstanceState.getInt(BUNDLE_ARG_IS_ITEM_SELECTED));
            }
        }

        setHasOptionsMenu(true);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(BUNDLE_ARG_IS_ITEM_SELECTED, mViewModel.getNumberOfSelectedContacts());
        outState.putBoolean(BUNDLE_IS_CLICK_DELETE, mClick);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ContactViewModel.class);
        mSelectedItemsLiveData = mViewModel.getSelectedItemsLiveData();
        setAdapter(mViewModel.getContactList());
        swipeRecycler();
        FAB_listener();
        setObserver();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_search, menu);

        MenuItem searchMenuItem = menu.findItem(R.id.menu_item_search);
        SearchView searchView = (SearchView) searchMenuItem.getActionView();
        setSearchViewListeners(searchView);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.menu_delete) {
            if (mViewModel.getNumberOfSelectedContacts() != 0)
                deleteContact(mViewModel.getNumberOfSelectedContacts());
            return true;
        } else if (itemId == R.id.menu_select_all) {
            mViewModel.setContactsSelected();
            setAdapter(mViewModel.getContactList());
            return true;
        } else if (itemId == R.id.menu_unSelectAll) {
            mViewModel.setContactsUnSelected();
            setAdapter(mViewModel.getContactList());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void deleteContact(int deletedItem) {
        mClick = true;
        CustomAlertDialogue.Builder alert = new CustomAlertDialogue.Builder(getActivity())
                .setStyle(CustomAlertDialogue.Style.DIALOGUE)
                .setCancelable(false)
                .setTitle(getTitle(deletedItem))
                .setMessage(getString(R.string.are_you_sure))
                .setPositiveText(getString(R.string.yes))
                .setPositiveColor(R.color.negative)
                .setPositiveTypeface(Typeface.DEFAULT_BOLD)
                .setOnPositiveClicked((view, dialog) -> {
                    mViewModel.deleteSelectedContact();
                    setAdapter(mViewModel.getContactList());
                    dialog.dismiss();
                })
                .setNegativeText(getString(R.string.no))
                .setNegativeColor(R.color.positive)
                .setOnNegativeClicked((view, dialog) -> {
                    mViewModel.setContactsUnSelected();
                    setAdapter(mViewModel.getContactList());
                    dialog.dismiss();
                })
                .setDecorView(requireActivity().getWindow().getDecorView())
                .build();
        alert.show();
    }

    private String getTitle(int deletedItem) {
        String title;

        if (deletedItem == 1)
            title = getString(
                    R.string.delete_contact,
                    deletedItem + " contact");
        else {
            title = getString(
                    R.string.delete_contact,
                    deletedItem + " contacts");
        }

        return title;
    }

    private void setSearchViewListeners(SearchView searchView) {

        mSearchView = searchView;
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //searchView.clearFocus();
                searchMethod(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchMethod(newText);
                return true;
            }
        });
        searchView.setOnSearchClickListener(view -> {
            mViewModel.setContactsUnSelected();
            setAdapter(mViewModel.getContactList());
            String query = mViewModel.getQueryFromPreferences();
            if (query != null)
                searchView.setQuery(query, false);
        });
        searchView.setOnCloseListener(() -> {
            setAdapter(mViewModel.getContactList());
            searchView.onActionViewCollapsed();
            return true;
        });
    }

    private void searchMethod(String query) {
        List<ContactModel> searchList = mViewModel.searchContact(query);
        mViewModel.setQueryInPreferences(query);
        if (searchList.isEmpty()) {
            Toast.makeText(getActivity(), "No Contact Found.", Toast.LENGTH_SHORT).show();
        }
        // passing this filtered list to our adapter.
        setAdapter(searchList);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mViewModel.getQueryFromPreferences() == null)
            setAdapter(mViewModel.getContactList());
        else {
            if (mSearchView != null)
                if (!mSearchView.isFocusable())
                    setAdapter(mViewModel.getContactList());
                else if (mViewModel.getQueryFromPreferences().trim().isEmpty())
                    setAdapter(mViewModel.getContactList());
        }
    }

    private void initView() {
        mBinding.recyclerMainFragment.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void setAdapter(List<ContactModel> contactList) {
        MainAdapter mainAdapter = new MainAdapter(this, getActivity(), mViewModel, contactList);
        mBinding.recyclerMainFragment.setAdapter(mainAdapter);
    }

    private void swipeRecycler() {
        ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                final int position = viewHolder.getAdapterPosition();
                ContactModel contact = mViewModel.getContactList().get(position);
                if (direction == ItemTouchHelper.RIGHT) {
                    mViewModel.makeCall(contact);
                    Toast.makeText(getActivity(), "Call: " + contact, Toast.LENGTH_SHORT).show();
                } else {
                    mViewModel.sendMessage(contact);
                    Toast.makeText(getActivity(), "Message: " + contact, Toast.LENGTH_SHORT).show();
                }
                setAdapter(mViewModel.getContactList());

            }

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder,
                                    float dX, float dY, int actionState, boolean isCurrentlyActive) {
                new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                        .addSwipeLeftBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.blue))
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

    private void setObserver() {
        mSelectedItemsLiveData.observe(getViewLifecycleOwner(), aBoolean -> setAdapter(mViewModel.getContactList()));
    }

}