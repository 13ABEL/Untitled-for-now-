package com.hestia.datalayer.Card;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

@Dao
public interface CardLinkDao {
  @Insert
  void insert(CardLink link);

  @Query("SELECT 1 FROM CardLink WHERE cardID = :selectID ")
  String getCardUrl(String selectID);



}
