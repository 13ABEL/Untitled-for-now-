package com.hestia.domainlayer;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

/**
 * Created by Richard on 3/7/2018.
 */

public interface Deck {
  // Note there should be a few things that should be set at initialization (ie. these attributes
  // should never change after being created)
  // Attributes generated on creation: author, deckID, deck

  // getters for un-editable attributes
  String getDeckID();
  String getAuthor();
  String getDeckClass();

  // getters for editable attributes
  String getDeckName();

  // setters for editable attributes
  void setDeckName(String newName);
  void setEditedDate(Date editedDate);
  void setSummary(String summary);
  void setDeck(Collection <Card> cards);
  void setFormat(boolean isStandard);

  int addToDeck(Card newCard);

  // TODO still not sure about this one because it requires implementation details
  // I don't might replace it in the future
  Map <String, Object> generateMap();
}
