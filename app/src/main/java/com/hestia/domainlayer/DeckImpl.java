package com.hestia.domainlayer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Richard on 3/7/2018.
 */

public class DeckImpl implements Deck {
  private final int DECK_LENGTH = 30;

  protected ArrayList <Card> deckList = new ArrayList<>();
  // properties set on object creation
  protected String deckID;
  protected String deckName;
  protected String username;
  protected String className;
  protected Date createdDate;
  protected String createdDateString;


  // editable properties should only be changed by setters
  private String summary;
  private String deckString;
  private Date editedDate;
  protected boolean isStandard;


  // TBD
  protected String authorID;

  /**
   * Constructor for a new deck (requires attributes that are not editable)
   */
  public DeckImpl (String deckID, String authorID, String className) {
    this.authorID = authorID;
    this.className = className;
    this.deckID = deckID;
    this.createdDate = new Date();

    // initializes the empty array
    Collection <Card> deckList = new ArrayList<>();
  }


  /**
   * Constructor for a full deck
   * usually used when pulling from external sources
   * @param ID
   * @param name
   * @param author
   * @param list
   * @param info
   * @param date
   */
  public DeckImpl (String ID, String name, String author, String list, String info, Date date) {
    this.deckID = ID;
    this.deckName = name;
    this.username = author;
    this.summary = info;
    this.createdDate = date;
    this.deckString = list;

    // format the Date as a string
    DateFormat df = new SimpleDateFormat("dd/MM/yyyy:HH:mm:ss");
   this.createdDateString = df.format(this.createdDate);

    // parse the deck list into an arraylist
    for (int i = 0; i < DECK_LENGTH; i ++) {
      // gets the card id from the string
      String cardID = list.substring(i*3, i*3 + 4);
      this.deckList.add(new CardImpl(cardID));
    }
  }

  public DeckImpl () {
  }

  public String getDeckID () {
    return this.deckID;
  }

  public String getDeckName() {
    return this.deckName;
  }

  @Override
  public void setDeckName(String newName) {
    this.deckName = newName;
  }

  @Override
  public void setEditedDate(Date editedDate) {

  }

  @Override
  public void setSummary(String summary) {

  }

  @Override
  public void setDeck(Collection<Card> cards) {

  }

  @Override
  public void setFormat(boolean isStandard) {

  }

  public String getAuthor() {
    return this.username;
  }

  @Override
  public String getDeckClass() {
    return className;
  }

  public String toString() {
    return "not null";
  }

  public String getSummary() { return this.summary;}

  public ArrayList<Card> getDeckList () {
    return this.deckList;
  }

  public Map<String, Object> generateMap () {
    Map<String, Object> deckRep = new HashMap<String, Object>();
    deckRep.put("deckName", this.deckName);
    deckRep.put("username", this.username);
    deckRep.put("summary", this.summary);
    deckRep.put("createdDate", this.createdDate);
    deckRep.put("deckString", this.deckString);

    return deckRep;
  }

}
