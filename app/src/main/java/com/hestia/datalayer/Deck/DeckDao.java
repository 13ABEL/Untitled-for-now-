package com.hestia.datalayer.Deck;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.hestia.domainlayer.Deck;

import java.util.List;

@Dao
public interface DeckDao {
  // selects all decks
  @Query("SELECT * FROM DeckAdapter LIMIT :batchSize")
  List <DeckAdapter> getBatch(int batchSize);

  @Insert()
  void insertDeckBatch(List<Deck> decks);
}
