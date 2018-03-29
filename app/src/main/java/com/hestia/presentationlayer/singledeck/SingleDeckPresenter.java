package com.hestia.presentationlayer.singledeck;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.hestia.datalayer.DeckRepository;
import com.hestia.datalayer.DeckRepositoryImpl;
import com.hestia.domainlayer.Deck;
import com.hestia.presentationlayer.DeckDecorator;

import java.util.ArrayList;

/**
 * Created by Richard on 3/14/2018.
 */

public class SingleDeckPresenter implements SingleDeckContract.Presenter{
  private ArrayList <TabFragment> tabFragments = new ArrayList<>();
  private SingleDeckContract.View singleDeckView;
  private DeckRepository deckRepository;
  private Deck currentDeck;


  public SingleDeckPresenter(SingleDeckContract.View view, DeckDecorator deck) {
    this.singleDeckView = view;
    this.currentDeck = deck;
  }

  /**
   * Callback method to receive the deck object from repository after async call
   * @param deck
   */
  @Override
  public void receiveFullDeck(DeckDecorator deck) {
    this.currentDeck = deck;

    // tells the view what to display
    singleDeckView.displayInfo(deck.getDeckName());
  }

  @Override
  public void addTabFragment(TabFragment tabFragment, int position) {
    if (tabFragments.size() >= position) {
      // adds the fragment into the current array of tab fragments
      this.tabFragments.add(position, tabFragment);
      tabFragment.updateUI(currentDeck);
    } else {
      this.tabFragments.add(tabFragment);
      tabFragment.updateUI(currentDeck);
    }
  }


}
