package com.hestia.presentationlayer.singledeck;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.hestia.R;
import com.hestia.datalayer.CardRepository;
import com.hestia.datalayer.CardRepositoryImpl;
import com.hestia.datalayer.UserRepository;
import com.hestia.datalayer.UserRepositoryImpl;
import com.hestia.domainlayer.Card;
import com.hestia.presentationlayer.DeckDecorator;

import java.util.List;

/**
 * Created by Richard on 3/14/2018.
 */

public class SingleDeckView extends Fragment implements SingleDeckContract.View {
  private final String TAG = "SINGLE_DECK_VIEW";
  private SingleDeckContract.Presenter singleDeckPresenter;

  //
  private DeckFragment deckFrag;
  private InfoFragment infoFrag;

  private ViewPager viewPager;
  private DeckDecorator currentDeck;

  FloatingActionButton editButton;


  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    // gets the deckDecorator object from the bundle
    if (this.getArguments() != null) {
      currentDeck = this.getArguments().getParcelable("deck");
      singleDeckPresenter = new SingleDeckPresenter(this, currentDeck);

      // creates a new instance of the repo to inject into the presenter
      CardRepository cardRepo = new CardRepositoryImpl(this.getContext());
      singleDeckPresenter.injectDependencies(cardRepo);
    }
  }


  /**
   * Inflates the xml layouts into a view
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

    // retrieve the frag manager and create a new adapter for the tabs
    FragmentManager fragManager = getActivity().getSupportFragmentManager();
    FragmentStatePagerAdapter tabsAdapter = new TabsAdapter(fragManager);

    // attaches the tabs adapter to the view pager
    viewPager = rootView.findViewById(R.id.single_deck_pager);
    viewPager.setAdapter(tabsAdapter);

    // attaches the view pager to the sliding tabs layout
    TabLayout tabLayout = rootView.findViewById(R.id.sliding_tabs);
    tabLayout.setupWithViewPager(viewPager);

    return rootView;
  }

  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    // sets the title of the navbar as the current deck name
    getActivity().setTitle(currentDeck.getDeckName());

    // TODO check if the deck has already been fav-ed by the user
    UserRepository userRepo = new UserRepositoryImpl();
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

  @Override
  public void displaySummary(String summary) {

  }

  @Override
  public void displayDecklist(List<Card> cardList) {
    deckFrag.displayDecklist(cardList);
  }


  /**
   * Note that we use a FragmentPagerAdapter instead of a FragmentStatePagerAdapter
   * It's better for a small and fixed number of screens
   */
  class TabsAdapter extends FragmentStatePagerAdapter {
    TabsAdapter(FragmentManager manager) {
      super(manager);
    }

    @Override
    public Fragment getItem(int position) {
      Fragment newFragment;

      if (position == 1) {
        deckFrag = new DeckFragment();
        newFragment = deckFrag;
        // tells the presenter to retrieve the decklist for this deck
        singleDeckPresenter.getDeckList();
      }
      else {
        infoFrag = new InfoFragment();
        newFragment = infoFrag;
      }
      return newFragment;
    }

    @Override
    public int getCount() {
      return 2;
    }
  }

}
