package com.hestia.domainlayer;

import android.widget.CalendarView;

/**
 * Created by Richard on 3/29/2018.
 */

public class CardImpl implements Card{
  String cardID;
  String name;
  String cardClass;
  String text;
  int cost;

  public CardImpl() {

  }

  public CardImpl (String id) {
    this.cardID = id;
  }


}
