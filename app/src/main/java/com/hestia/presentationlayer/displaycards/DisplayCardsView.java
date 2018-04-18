package com.hestia.presentationlayer.displaycards;

import android.app.ActionBar;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.content.Context;
import android.os.Bundle;
import android.os.TestLooperManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.hestia.R;
import com.hestia.datalayer.Card.CardDecorator;
import com.hestia.datalayer.CardRepository;
import com.hestia.datalayer.CardRepositoryImpl;
import com.hestia.domainlayer.Card;
import com.hestia.presentationlayer.customadapter.DisplayCardAdapter;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Richard on 3/30/2018.
 */

public class DisplayCardsView extends Fragment implements DisplayCardsContract.View{
  private final String FRAGMENT_TAG = "DISPLAY_CARDS_FRAGMENT";
  private final String TAG = "DISPLAY_CARDS_FRAGMENT";

  private View rootView;
  private RecyclerView mRecyclerView;
  private RecyclerView.LayoutManager mLayoutManager;

  private DisplayCardAdapter mLayoutAdapter;

  DisplayCardsVM viewModel;
  String created = "NEW";


  /**
   * This method is called only when the fragment is created
   * @param savedInstanceState
   */
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    getActivity().setTitle(R.string.app_name);

    // gets the ViewModel instance associated with the fragment
    viewModel = ViewModelProviders.of(this).get(DisplayCardsVM.class);
  }


  public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
    if (rootView == null) {
      // inflates the view and enables the options menu
      rootView = inflater.inflate(R.layout.display_cards, container, false);
      setHasOptionsMenu(true);

      // sets tag to allow manager to recognise this fragment
      rootView.setTag(FRAGMENT_TAG);

      created = "NOT NEW";
    }
    // initializes the instance of the recycler view using the newly inflated view
    mRecyclerView = rootView.findViewById(R.id.display_cards_recyclerview);
    mRecyclerView.setHasFixedSize(true);

    // initialize and sets the layout manager for the recycler view
    if (mLayoutManager == null) {
      mLayoutManager = new GridLayoutManager(rootView.getContext(), 2);
      mRecyclerView.setLayoutManager(mLayoutManager);
    }

    // initializes and sets the adapter for the recycler view (not the root)
    if (mLayoutAdapter == null) {
      mLayoutAdapter = new DisplayCardAdapter();
      mRecyclerView.setAdapter(mLayoutAdapter);
      // calls the method to set the default page
      resetData();
    }

    return rootView;
  }

  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    // clear the previous menu
    // inflate the menu layout into the Menu object
    inflater.inflate(R.menu.display_cards_menu, menu);
    super.onCreateOptionsMenu(menu, inflater);

    // set the query listener for the search bar
    SearchView searchView = (SearchView) menu.findItem(R.id.display_cards_search).getActionView();
    searchView.setOnQueryTextListener(new searchListener(this));
    // resets the list when the search is closed
    searchView.setOnCloseListener(new SearchView.OnCloseListener() {
      @Override
      public boolean onClose() {
        Toast.makeText(getActivity().getApplicationContext(), "closed search", Toast.LENGTH_SHORT).show();
        resetData();
        return false;
      }
    });
  }


  // resets the data that the adapter observes
  public void resetData () {
    viewModel.getCards(this.getContext(), 4).observe(this, liveCardList ->
        mLayoutAdapter.submitList(liveCardList));
  }


  class searchListener implements SearchView.OnQueryTextListener {
    Fragment fragRef;

    searchListener(Fragment fragment) {
      super();
      this.fragRef = fragment;
    }
    @Override
    public boolean onQueryTextChange(String newText) {
      // returns the default list if the search text is empty
      if (newText.length() == 0) {
        resetData();
      }
      // true because the action is handled by the listener
      return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
      // returns the default list if the search text is empty
      viewModel.getSearchResult(fragRef.getContext(), query).observe(
          fragRef, liveSearchList -> mLayoutAdapter.submitList(liveSearchList)
      );
      // true because the action is handled by the listener
      return true;
    }
  }


  /**
   * implemented this method to allow the repository to access this context
   * Room needs the context to generate an instance
   * @return the context for this view
   */
  public Context getViewContext () {
    return this.getContext();
  }

  // TODO initialize a new actionbar with custom menus (filtering, sorting)
  // TODO allow custom OnClicks to be applied to this view (it'll be reused in a variety of scenarios later on



}
