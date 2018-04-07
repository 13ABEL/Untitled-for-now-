package com.hestia.datalayer.Deck;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.hestia.domainlayer.Card;
import com.hestia.domainlayer.Deck;
import com.hestia.domainlayer.DeckImpl;

import java.util.ArrayList;
import java.util.Date;

/**
 * Adapter for the Room library which maps to the deck in the domain layer
 */
@Entity
public class DeckAdapter{
  @PrimaryKey()
  @NonNull
  protected String deckID;

  @ColumnInfo(name = "deck_list")
  protected String deckList;

  @ColumnInfo(name = "deck_name")
  protected String deckName = "No name";
  @ColumnInfo(name = "deck_author")
  protected String username;
  @ColumnInfo(name = "deck_summary")
  protected String summary;

  @ColumnInfo(name = "created_time")
  protected String createdDateString;
}

