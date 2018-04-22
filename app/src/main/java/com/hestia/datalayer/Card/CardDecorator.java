package com.hestia.datalayer.Card;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.hestia.domainlayer.CardImpl;

/**
 * Created by Richard on 3/31/2018.
 *
 * Entity for the database
 * Separate the Room framework (annotations) from the domain layer
 *
 *
 */

@Entity
public class CardDecorator extends CardImpl {
  // Room uses this to access the class
  public CardDecorator() {
    super();
  }

  public CardDecorator(String cardID, String name, int cardClass, String type, String rarity,
                       String text, String tribe, int cost ) {
    //this.cardID = cardID;
    this.name = name;
    this.cardClass = cardClass;
    this.type = type;
    this.rarity = rarity;
    this.text = text;
    this.tribe = tribe;
    this.cost = cost;
  }

}
