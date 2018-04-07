package com.hestia.datalayer.Deck;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;


@Database(entities = {DeckAdapter.class}, version = 2, exportSchema = false)
public abstract class DeckDatabase extends RoomDatabase{
  private static final String TAG = "DECK_DATABASE";

  private static DeckDatabase INSTANCE = null;

  public static DeckDatabase getDeckDatabase(Context mContext) {
    // creates a new instance of the database only if it doesn't already exist
    if (INSTANCE == null ) {
      INSTANCE = Room.databaseBuilder(mContext, DeckDatabase.class, "DeckDatabase")
          .build();
    }
    return INSTANCE;
  }


}
