package com.hestia.presentationlayer;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.hestia.domainlayer.CardImpl;

/**
 * Created by Richard on 3/31/2018.
 *
 * Separate the Room framework (annotations) from the domain layer
 *
 *
 */

public class CardDecorator extends CardImpl {
  @PrimaryKey
  String cardID;

  @ColumnInfo(name = "card_name")
  String name;

  @ColumnInfo(name = "card_class")
  String cardClass;

  @ColumnInfo(name = "card_text")
  String text;

  @ColumnInfo(name = "card_cost")
  int cost = 0;


}
