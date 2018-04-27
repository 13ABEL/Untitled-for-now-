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
import com.hestia.domainlayer.Card;
import com.hestia.domainlayer.Deck;
import com.hestia.presentationlayer.DeckDecorator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Richard on 3/14/2018.
 */

public class SingleDeckPresenter implements SingleDeckContract.Presenter{
  private final String TAG = "SINGLE_DECK_PRESENTER";

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

  /**
   * inject the card repo for to be used for retrieving cards
   * @param cardRepo implementation of card repo
   */
  @Override
  public void injectDependencies(CardRepository cardRepo) {
    this.cardRepo = cardRepo;
  }


  @Override
  public void getDeckList() {
    if (cardRepo != null) {
      // gets the list of cards in the deck for viewing
      cardRepo.getCardsFromString(this, currentDeck.getDeckString());
    }
  }


  /**
   * Callback method for displaying cards from the card repository
   * @param deckList the list of cards to be displayed
   */
  public void receiveDeckList(List <Card> deckList) {
    // tells the view what to display
    singleDeckView.displayDecklist(deckList);
  }


  /**
   * Callback method to receive the deck object from repository after async call and to
   * tell the view what to display
   * @param deck received deck
   */
  @Override
  public void receiveFullDeck(DeckDecorator deck) {
    this.currentDeck = deck;
    // tells the view what to display
    singleDeckView.displayInfo(deck.getDeckName());
  }

  /**
   * Saves the deck to the list of decks saved by the user
   */
  @Override
  public void saveDeck() {
    UserRepository userRepository = new UserRepositoryImpl();
    userRepository.saveDeck(currentDeck);
  }

}
