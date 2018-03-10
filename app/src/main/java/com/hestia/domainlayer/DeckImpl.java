package com.hestia.domainlayer;

/**
 * Created by Richard on 3/7/2018.
 */

public class DeckImpl implements Deck {
  int deck_id;
  int author_id;

  String temp1 ="Discolock";
  String temp2 = "Richard Wei";
  String temp3 = "March 9th";

  public DeckImpl () {

  }

  public DeckImpl (int wow) {

  }

  public String toString () {
    return "haha yeet";
  }

  public String getDeckName() {
    return temp1;
  }

  public String getAuthor() {
    return temp2;
  }

}
