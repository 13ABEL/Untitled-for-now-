package com.hestia.datalayer.Card;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.hestia.presentationlayer.DeckDecorator;

import java.util.List;

/**
 * Created by Richard on 3/31/2018.
 */

@Dao
public interface CardDao {
  // selects all cards
  @Query("SELECT * FROM carddecorator")
  List<CardDecorator> getAll();

  // selects all cards in a class
  @Query("SELECT * FROM carddecorator")
  List<CardDecorator> getAllInClass();

  //TODO make column
  // selects all cards in an expansion
  @Query("SELECT * FROM carddecorator")
  List<CardDecorator> getAllInExpansion();

  // selects all cards with a certain cost
  @Query("SELECT * FROM carddecorator WHERE card_cost LIKE :cost")
  List<CardDecorator> getAllWithCost(int cost);
}
