package com.hestia.datalayer;

import com.hestia.domainlayer.Deck;
import com.hestia.presentationlayer.DeckDecorator;
import com.hestia.presentationlayer.displaydecks.DisplayDecksContract;
import com.hestia.presentationlayer.singledeck.SingleDeckContract;

/**
 * Created by Richard on 3/10/2018.
 */

public interface DeckRepository {
  void getDeckBatch (DisplayDecksContract.Presenter presenter, int numDecks);

  void getFullDeck(SingleDeckContract.Presenter presenter, String deckID);

  void saveDeck (DeckDecorator deck);
  // getCreatedBetween();
  // getCreatedBy();
  // getClass();
  // getRating();
}
