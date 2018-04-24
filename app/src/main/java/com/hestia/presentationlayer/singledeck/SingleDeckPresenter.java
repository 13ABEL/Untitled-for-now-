package com.hestia.presentationlayer.singledeck;

import android.arch.lifecycle.LiveData;
import android.arch.paging.PagedList;
import android.icu.text.IDNA;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.hestia.datalayer.Card.CardDecorator;
import com.hestia.datalayer.CardRepository;
import com.hestia.datalayer.CardRepositoryImpl;
import com.hestia.datalayer.DeckRepository;
import com.hestia.datalayer.DeckRepositoryImpl;
import com.hestia.datalayer.UserRepository;
import com.hestia.datalayer.UserRepositoryImpl;
import com.hestia.domainlayer.Deck;
import com.hestia.presentationlayer.DeckDecorator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Richard on 3/14/2018.
 */

public class SingleDeckPresenter implements SingleDeckContract.Presenter{
  private final String TAG = "SINGLE_DECK_PRESENTER";
  private final int INFO_TAB = 1;
  private final int DECK_TAB = 2;
  private final int EX_TAB = 3;

  private InfoFragment infoFragment;
  private DeckFragment deckFragment;

  private ArrayList <TabFragment> tabFragments = new ArrayList<>();
  private SingleDeckContract.View singleDeckView;
  private DeckRepository deckRepository;
  private DeckDecorator currentDeck;
  private CardRepository cardRepo;

  public SingleDeckPresenter(SingleDeckContract.View view, DeckDecorator deck) {
    this.singleDeckView = view;
    this.currentDeck = deck;

    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

    // adds the edit option if the author if the current user
    if (currentUser != null && currentDeck.getAuthor().equals(currentUser.getUid())) {
      Log.d(TAG, currentDeck.getAuthor() + " " + currentUser.getUid());
      // display the edit option for this deck
      singleDeckView.displayEditOption();
    }
  }

  @Override
  public void injectDependencies(CardRepository cardRepo) {
    this.cardRepo = cardRepo;
  }

  @Override
  public LiveData<PagedList<CardDecorator>> getAddableCards() {
    LiveData<PagedList<CardDecorator>> buildCards = null;
    // checks if the repo has been initialized
    if (cardRepo != null) {
      // use the current deck info to generate available cards
      buildCards = cardRepo.generateDeckCards(currentDeck.getDeckClass(), true);
    }
    return  buildCards;
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
  public void addTabFragment(TabFragment tabFragment, int tabPosition) {
    // adds the fragment into the current array of tab fragments
    this.tabFragments.add(tabPosition, tabFragment);

    // passes the correct method to update the UI based on tab position
    if (tabPosition == INFO_TAB) {
      InfoFragment specificFrag = (InfoFragment) tabFragment;
      specificFrag.updateUI(currentDeck);
    }
    else if (tabPosition == DECK_TAB) {
      DeckFragment specificFrag = (DeckFragment) tabFragment;
      specificFrag.updateUI(currentDeck.getDeckList());
    }
  }

  public void addInfoTabFragment(InfoFragment newInfoFrag) {
    this.infoFragment = newInfoFrag;
    // updates the UI
    newInfoFrag.updateUI(currentDeck);
  }

  public void addDeckTabFragment(DeckFragment newDeckFrag) {
    this.deckFragment = newDeckFrag;
    // updates the UI
    newDeckFrag.updateUI(currentDeck.getDeckList());
  }

  @Override
  public void saveDeck() {
    UserRepository userRepository = new UserRepositoryImpl();
    userRepository.saveDeck(currentDeck);
  }

}
