package com.hestia.domainlayer;

import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Richard on 3/7/2018.
 */
public class DeckImpl implements Deck {
  final int DRUID = 1;
  final int HUNTER = 2;
  final int MAGE = 3;
  final int PALADIN = 4;
  final int PRIEST = 5;
  final int ROGUE = 6;
  final int SHAMAN = 7;
  final int WARLOCK = 8;
  final int WARRIOR = 9;
  
  protected ArrayList <Card> deckList = new ArrayList<>();
  // properties set on object creation
  protected String deckID;
  protected String deckName;
  protected String authorID;
  protected String username;
  protected int classID;
  protected Date createdDate;
  protected String createdDateString;


  // editable properties should only be changed by setters
  private String summary;
  private String deckString;
  private Date editedDate;
  protected boolean isStandard;


  /**
   * Constructor for a new deck (requires attributes that are not editable)
   */
  public DeckImpl (String deckID, String authorID, int classID) {
    this.deckID = deckID;
    this.authorID = authorID;
    this.classID = classID;
    this.createdDate = new Date();

    // temporarily use author id as the username
    this.username = authorID;

    // initializes the empty array
    Collection <Card> deckList = new ArrayList<>();
  }


  /**
   * Constructor for a full deck
   * usually used when pulling from external sources
   * @param deckID
   * @param deckName
   * @param authorID
   * @param info
   * @param date
   */
  public DeckImpl (String deckID, String deckName, String authorID, String deckList, String info, Date date) {
    this.deckID = deckID;
    this.deckName = deckName;
    this.authorID = authorID;
    this.username = authorID;
    this.summary = info;
    this.createdDate = date;
    this.deckString = deckList;

    // format the Date as a string
    DateFormat df = new SimpleDateFormat("dd/MM/yyyy:HH:mm:ss");
    this.createdDateString = df.format(this.createdDate);

    // checks the deck for cards
    if (deckList != null) {
      // parse the deck list into an arraylist
      for (int i = 0; i < deckList.length()/4 ; i++) {
        // gets the card id from the string
        String cardID = deckList.substring(i * 3, i * 3 + 4);
        this.deckList.add(new CardImpl(cardID));
      }
    }
  }

  /**
   * Adds or removes a card to the decklist based on the number of copies of it already exist
   * in this deck
   *
   * @param currCard card that needs to be added/removed
   * @return whether the card was added or removed
   */
  public int addToDeck(Card currCard){
    int cardsAdded = 0;
    // gets rarity of card and number of occurrences in the deck
    String rarity = currCard.getRarity();
    int numCard = Collections.frequency(deckList, currCard);

    // removes the card if the deck already has max copies of it
    if (rarity.equals("Legendary") && numCard == 1 || numCard == 2) {
      deckList.remove(currCard);
      cardsAdded = -1;
    }
    // add the current card if it's not being removed and the deck still has room
    else if (deckList.size() < 30) {
      deckList.add(currCard);
      // tells the view to show cards being added
      cardsAdded = 1;
    }
    return cardsAdded;
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
    return this.authorID;
  }

  @Override
  public int getDeckClass() {
    return classID;
  }

  public String toString() {
    return "not null";
  }

  public String getSummary() { return this.summary;}

  public ArrayList<Card> getDeckList () {
    return this.deckList;
  }

  public Map<String, Object> generateMap () {
    // generates the deck string for easier uploading
    StringBuilder deckString = new StringBuilder();
    for (Card collectionCard : deckList) {
      deckString.append(collectionCard.getID());
    }

    Map<String, Object> deckRep = new HashMap<String, Object>();
    deckRep.put("deckName", this.deckName);
    deckRep.put("author_id", this.authorID);
    deckRep.put("username", this.username);
    deckRep.put("summary", this.summary);
    deckRep.put("createdDate", this.createdDate);

    deckRep.put("classID", this.classID);
    deckRep.put("deckString", deckString.toString());

    return deckRep;
  }

}
