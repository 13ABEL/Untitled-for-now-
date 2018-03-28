package com.hestia.domainlayer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Richard on 3/7/2018.
 */

public class DeckImpl implements Deck {
  private ArrayList <String> deckList = new ArrayList<>();
  int DECK_LENGTH = 30;
  // variables that with default values
  private String deckName = "No name";
  private String username;
  private String summary;

  //
  private String deckID;
  private Date createdDate;
  private String authorID;

  public DeckImpl (String name, String author, String list, String info, Date date) {
    this.deckName = name;
    this.username = author;
    this.summary = info;
    this.createdDate = date;

    // parse the deck list into an arraylist
    for (int i = 0; i < DECK_LENGTH; i ++) {
      this.deckList.add(list.substring(i*3, i*3 + 4));
    }
  }

  public DeckImpl () {
  }

  public DeckImpl (String author) {
    this.deckName = author;
  }

  public String getDeckName() {
    return deckName;
  }

  public String getAuthor() {
    return username;
  }

  public String toString() {
    return "not null";
  }



}
