package com.hestia.presentationlayer.singledeck;

import com.hestia.datalayer.DeckRepositoryImpl;
import com.hestia.domainlayer.Deck;

/**
 * Created by Richard on 3/14/2018.
 */

public class SingleDeckPresenter implements SingleDeckContract.Presenter{
  private SingleDeckContract.View singleDeckView;
  Deck currentDeck;


  public SingleDeckPresenter(SingleDeckContract.View view, String deckID) {
    singleDeckView = view;

    // initialize the repository object to get data
    //deckRepository = new DeckRepositoryImpl(this);
    //deckRepository.ge



  }



}
