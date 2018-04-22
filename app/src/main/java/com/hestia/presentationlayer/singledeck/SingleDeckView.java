package com.hestia.presentationlayer.singledeck;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.hestia.R;
import com.hestia.presentationlayer.DeckDecorator;
import com.hestia.presentationlayer.customadapter.SingleDeckTabAdapter;

/**
 * Created by Richard on 3/14/2018.
 */

public class SingleDeckView extends Fragment implements SingleDeckContract.View {
  private SingleDeckContract.Presenter singleDeckPresenter;
  private String [] tabNames = {"INFO", "LIST", "COMMENTS"};
  private SingleDeckTabAdapter mSingleDeckTabAdapter;
  private ViewPager mPager;
  private DeckDecorator currentDeck;

  FloatingActionButton editButton;

  /**
   * Inflates the xml file into a view
   *
   * @param inflater
   * @param container
   * @param savedInstanceState
   * @return the single deck view
   */
  public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    // inflate the layout for this view
    View rootView = inflater.inflate(R.layout.single_deck, container, false);

    // enables the menu for this activity and clears the previous menu items
    setHasOptionsMenu(true);
    getActivity().invalidateOptionsMenu();

    // gets the deckDecorator object from the bundle
    currentDeck = this.getArguments().getParcelable("deck");
    //Toast.makeText(getContext(), "Single Deck Screen " + currentDeck.getDeckName(), Toast.LENGTH_SHORT).show();

    // create instance of the presenter
    singleDeckPresenter = new SingleDeckPresenter(this, currentDeck);

    // gets supportFragmentManager bc ViewPage uses support library fragments
    FragmentManager fragManager = getActivity().getSupportFragmentManager();
    mSingleDeckTabAdapter = new SingleDeckTabAdapter(fragManager, singleDeckPresenter);

    // sets the collection adapter with tabs as the pager adapter
    mPager = rootView.findViewById(R.id.single_deck_pager);
    mPager.setAdapter(mSingleDeckTabAdapter);

    TabLayout tabLayout = rootView.findViewById(R.id.sliding_tabs);
    tabLayout.setupWithViewPager(mPager);

    return rootView;
  }

  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    // gets the current Deck ID and uses it to get the deck from the repository
    //String deckName = this.getArguments().getString("deck_id");

    // sets the title of the navbar as the current deck name
    //getActivity().setTitle(currentDeck.getDeckName());
    getActivity().setTitle(currentDeck.getDeckID());


    // checks if the deck has already been fav-ed by the user
  }

  public void onPause() {
    super.onPause();
    // hide the floating action button when exiting this view
    if (editButton != null) {
      editButton.setVisibility(View.INVISIBLE);
    }
  }

  public void onCreateOptionsMenu(Menu mMenu, MenuInflater mInflater) {
    // inflate the menu layout into the Menu object
    mInflater.inflate(R.menu.display_single_menu, mMenu);
    super.onCreateOptionsMenu(mMenu, mInflater);
  }

  public boolean onOptionsItemSelected (MenuItem selectedItem) {
    switch (selectedItem.getItemId()) {
      case R.id.item_fav_deck:
        // tells the presenter to fav the deck
        singleDeckPresenter.saveDeck();
    }
    return false;
  }



  public void displayInfo(String title) {
    // TODO more lines when more info is used for this fragment
  }

  //TODO implement a pull downwards refresh listener

  public void displayEditOption() {
    // show the floating action button with the appropriate image
    editButton = getActivity().findViewById(R.id.fab);

    editButton.setImageDrawable(getActivity().getDrawable(R.drawable.ic_edit_24dp));
    editButton.setVisibility(View.VISIBLE);
  }

}
