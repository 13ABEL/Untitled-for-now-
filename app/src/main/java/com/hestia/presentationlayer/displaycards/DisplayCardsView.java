package com.hestia.presentationlayer.displaycards;

import android.app.ActionBar;
import android.app.Dialog;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.content.Context;
import android.os.Bundle;
import android.os.TestLooperManager;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hestia.R;
import com.hestia.datalayer.Card.CardDecorator;
import com.hestia.datalayer.CardRepository;
import com.hestia.datalayer.CardRepositoryImpl;
import com.hestia.domainlayer.Card;
import com.hestia.presentationlayer.CardImageDialog;
import com.hestia.presentationlayer.customadapter.DisplayCardAdapter;
import com.squareup.picasso.Picasso;


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
  private FragmentStatePagerAdapter filterTabAdapter;


  private int currentClassID;

  ViewPager viewPager;
  TabLayout tabLayout;

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
    viewModel.initializeRepo(this.getContext());
  }


  public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
    if (rootView == null) {
      // inflates the view and enables the options menu
      rootView = inflater.inflate(R.layout.display_cards, container, false);

      // enables the menu for this activity and clears the previous menu items
      setHasOptionsMenu(true);
      getActivity().invalidateOptionsMenu();

      // sets tag to allow manager to recognise this fragment
      rootView.setTag(FRAGMENT_TAG);

      created = "NOT NEW";
    }
//    // initializes the instance of the recycler view using the newly inflated view
//    mRecyclerView = rootView.findViewById(R.id.display_cards_recyclerview);
//    mRecyclerView.setHasFixedSize(true);
//
//    // initialize and sets the layout manager for the recycler view
//    if (mLayoutManager == null) {
//      mLayoutManager = new GridLayoutManager(rootView.getContext(), 2);
//      mRecyclerView.setLayoutManager(mLayoutManager);
//    }
//
//    // initializes and sets the adapter for the recycler view (not the root)
//      if (mLayoutAdapter == null) {
//        mLayoutAdapter = new DisplayCardAdapter();
//        mRecyclerView.setAdapter(mLayoutAdapter);
//        // calls the method to set the default page
//        resetData();
//    }

    // Retrieve the fragment manager to create a new adapter for the tabs
    // (note) we use fragment child manager we have nested fragments
    FragmentManager childFragManager = getChildFragmentManager();
    filterTabAdapter = new FilterTabAdapter(childFragManager);

    // inflates the pager view and attaches the tab adapter to it
    viewPager = rootView.findViewById(R.id.display_cards_viewpager);
    viewPager.setAdapter(filterTabAdapter);

    // inflates the tab layout and attaches the viewpager to it
    tabLayout = rootView.findViewById(R.id.display_cards_tablayout);
    tabLayout.setupWithViewPager(viewPager);

    return rootView;
  }


  public void onResume () {
    super.onResume();
    // Necessary for re-attaching the adapter to to the viewpager
    FragmentManager childFragManager = getChildFragmentManager();
    viewPager.setAdapter(new FilterTabAdapter(childFragManager));
  }

  public void onPause() {
    super.onPause();
    // reset the pager's adapter to prevent it being
    viewPager.setAdapter(null);
  }

  /**
   * Inflates the menu and add listeners for each of its items
   * @param menu
   * @param inflater
   */
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


  public boolean onOptionsItemSelected(MenuItem item) {
    int newClassID = currentClassID;
    if (item.getTitle() != "class") {
      // routes the selection to the presenter to handle logic
      switch (item.getTitle().toString()) {
        case "druid": currentClassID = 1; break;
        case "hunter": currentClassID = 2; break;
        case "mage": currentClassID = 3; break;
        case "paladin": currentClassID = 4; break;
        case "priest": currentClassID = 5; break;
        case "rogue": currentClassID = 6; break;
        case "shaman": currentClassID = 7; break;
        case "warlock": currentClassID = 8; break;
        case "warrior": currentClassID = 9; break;
      }
    }

    // reset the view only if a new class is selected
    if (currentClassID != newClassID) {
      // updates the class id used by viewmodel to generate list
      viewModel.changeClass(currentClassID);
      int position = tabLayout.getSelectedTabPosition();

      // get the child fragment manger to create a new tab layout adapter to allow us to reset
      FragmentManager childFragManager = getChildFragmentManager();
      viewPager.setAdapter(new FilterTabAdapter(childFragManager));

      viewPager.setCurrentItem(position);
    }

    return super.onOptionsItemSelected(item);
  }


  // resets the data that the adapter observes
  public void resetData () {
    viewModel.getCards(4).observe(this, liveCardList ->
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
      viewModel.getSearchResult(query).observe(
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


  class FilterTabAdapter extends FragmentStatePagerAdapter {
    FilterTabAdapter (FragmentManager fragmentManager) {
      super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
      DisplayCardsTab cardTab = new DisplayCardsTab();

      // gets the cards associated with this tab and attach them to it
      cardTab.attachCards(viewModel.getCards(position));
      return cardTab;
    }

    @Override
    public int getCount() {
      return 11;
    }

    public CharSequence getPageTitle(int position) {
      return position + "";
    }
  }

}
