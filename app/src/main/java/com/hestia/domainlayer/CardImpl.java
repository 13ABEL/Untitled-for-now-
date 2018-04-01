package com.hestia.domainlayer;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.widget.CalendarView;

/**
 * Created by Richard on 3/29/2018.
 */

@Entity
public class CardImpl implements Card {
  @PrimaryKey
  @NonNull
  public String cardID;

  @ColumnInfo(name = "card_name")
  public String name;

  @ColumnInfo(name = "card_class")
  public String cardClass;

  @ColumnInfo(name = "card_text")
  public String text;

  @ColumnInfo(name = "card_cost")
  public int cost;


  public CardImpl() {

  }

  public CardImpl (String id) {
    this.cardID = id;
    // temporary
    this.name = id;
    this.cost = 0;
  }

  public String getID () {
    return this.cardID;
  }

  public String getName () {
    return this.name;
  }
  public int getCost () {
    return this.cost;
  }


}
