package com.hestia.domainlayer;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by Richard on 3/29/2018.
 */


public interface Card {
  int getID();
  String getName();
  int getCost();
  
  String getRarity();

}
