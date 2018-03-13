package com.hestia.datalayer;

import com.hestia.domainlayer.Deck;

/**
 * Created by Richard on 3/10/2018.
 */

public interface DeckRepository {
  void getDeck (int deckID);

  void saveDeck (Deck deck);
  // getCreatedBetween();
  // getCreatedBy();
  // getClass();
  // getRating();
}
