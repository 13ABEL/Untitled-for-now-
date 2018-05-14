package com.hestia.datalayer.Card;

import android.arch.paging.DataSource;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;


import com.hestia.domainlayer.Card;

import java.util.List;

/**
 * Created by Richard on 3/31/2018.
 */

@Dao
public interface CardDao {
  // selects all cards
  @Query("SELECT * FROM CardDecorator LIMIT :batchSize")
  List<CardDecorator> getBatch(int batchSize);

  // selects all cards in a class
  @Query("SELECT * FROM CardDecorator")
  List<CardDecorator> getAllInClass();

  //TODO make column
  // selects all cards in an expansion
  @Query("SELECT * FROM CardDecorator")
  List<CardDecorator> getAllInExpansion();

  // selects all cards with a certain cost
  @Query("SELECT * FROM CardDecorator WHERE card_cost LIKE :cost")
  List<CardDecorator> getAllWithCost(int cost);

  // inserts all card into the database
  @Insert
  void insertAll(List <CardDecorator> cards);
  // inserts all card into the database

  @Insert
  void insert(CardDecorator cardDecorator);

  // selects all cards, ordering by cost
  @Query("SELECT * FROM CardDecorator WHERE card_cost")
  DataSource.Factory<Integer, CardDecorator> getByCost();

  // selects all cards, ordering by cost
  @Query("SELECT * FROM CardDecorator ORDER BY card_cost ASC")
  DataSource.Factory<Integer, CardDecorator> getByCostOrder();

  // selects all cards, ordering by name
  @Query("SELECT * FROM CardDecorator ORDER BY card_name DESC")
  DataSource.Factory<Integer, CardDecorator> getByNameOrder();


  // TODO add the expansion column to entity
  // selects all cards, ordering by expansion
  @Query("SELECT * FROM CardDecorator WHERE card_name LIKE :expansion ")
  DataSource.Factory<Integer, CardDecorator> getByExpansion(String expansion);

  // selects all cards with the selected cost
  @Query("SELECT * FROM CardDecorator WHERE card_cost = :cardCost")
  DataSource.Factory<Integer, CardDecorator> getByCost(String cardCost);

  // selects all cards from the selected class
  @Query("SELECT * FROM CardDecorator WHERE card_class LIKE :className ORDER BY card_cost ASC")
  DataSource.Factory<Integer, CardDecorator> getByClass(int className);

  // selects all cards that match the search terms
  @Query("SELECT * FROM CardDecorator WHERE card_name LIKE :search")
  DataSource.Factory<Integer, CardDecorator> getBySearch(String search);

  @Query("SELECT * FROM CardDecorator WHERE card_name LIKE :search AND card_class = :className")
  DataSource.Factory<Integer, CardDecorator> getBySearchClass(String search, String className);

  // selects all cards from the selected class
  @Query("SELECT * FROM CardDecorator WHERE card_class IN (:className, 0) " +
      "AND card_cost >= 0 " +
      "ORDER BY card_class DESC, card_cost ASC")
  List<CardDecorator> getEditable(int className);


  @Query("SELECT * FROM CardDecorator WHERE cardID IN(:queryArray) ORDER BY card_cost ASC")
  List <CardDecorator> getCardList (String [] queryArray);


  @Query("SELECT * FROM CardDecorator WHERE card_cost = :cost AND card_class = :classID " +
      "ORDER BY card_cost ASC")
  DataSource.Factory<Integer, CardDecorator> getClassCardsByCost(int classID, int cost);
}
