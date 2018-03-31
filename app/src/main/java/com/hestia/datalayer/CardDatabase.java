package com.hestia.datalayer;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.hestia.datalayer.Card.CardDao;
import com.hestia.datalayer.Card.CardDecorator;

/**
 * Created by Richard on 3/31/2018.
 */

@Database(entities = {CardDecorator.class}, version = 1)
public abstract class CardDatabase extends RoomDatabase{

  public abstract CardDao cardDao();

  //TODO implement singleton pattern for this class


}
