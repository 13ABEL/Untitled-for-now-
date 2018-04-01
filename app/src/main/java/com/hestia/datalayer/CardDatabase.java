package com.hestia.datalayer;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.hestia.datalayer.Card.CardDao;
import com.hestia.datalayer.Card.CardDecorator;
import com.hestia.domainlayer.Card;
import com.hestia.domainlayer.CardImpl;

/**
 * Created by Richard on 3/31/2018.
 */

@Database(entities = {CardDecorator.class}, version = 1, exportSchema = false)
public abstract class CardDatabase extends RoomDatabase {
  private static CardDatabase INSTANCE = null;

  public abstract CardDao cardModel();

  public static CardDatabase getDatabase (Context context) {
    // following singleton pattern - generates instance if it doesn't already exist
    if (INSTANCE == null) {
      // create a new instance of the database
      INSTANCE = Room.databaseBuilder(context, CardDatabase.class, "CardDatabase")
          .build();
    }

    return INSTANCE;
  }

  public static void destroyInstance() {
    INSTANCE = null;
  }

}
