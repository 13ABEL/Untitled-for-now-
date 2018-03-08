package com.hestia.datalayer;

import android.arch.persistence.room.RoomDatabase;

/**
 * Created by Richard on 3/7/2018.
 */


public abstract class AppDatabase extends RoomDatabase {
  public abstract DeckDao deckDao();



}
