package com.hestia.domainlayer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Richard on 3/7/2018.
 */

public class DeckImpl implements Deck {
  protected ArrayList <String> deckList = new ArrayList<>();
  int DECK_LENGTH = 30;
  // variables that with default values
  protected String deckName = "No name";
  protected String username;
  protected String summary;

  //
  protected String deckID;
  protected Date createdDate;
  protected String createdDateString;
  protected String authorID;

  public DeckImpl (String name, String author, String list, String info, Date date) {
    this.deckName = name;
    this.username = author;
    this.summary = info;
    this.createdDate = date;

    // format the Date as a string
    DateFormat df = new SimpleDateFormat("dd/MM/yyyy:HH:mm:ss");
   this.createdDateString = df.format(this.createdDate);

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
    return this.deckName;
  }

  public String getAuthor() {
    return this.username;
  }

  public String toString() {
    return "not null";
  }



  public Map<String, Object> generateMap() {
    Map <String, Object> mapRep = new HashMap();

    // puts the object info into the map
    mapRep.put("name", this.deckName);
    mapRep.put("author", this.username);
    mapRep.put("summary", this.summary);
    mapRep.put("deckID", this.deckID);
    mapRep.put("createdDate", this.createdDate);
    mapRep.put("author", this.authorID);

    return mapRep;
  }


}
