package com.hestia.datalayer.Card;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class CardLink {
  @PrimaryKey(autoGenerate = true)
  @NonNull
  int cardID;

  @ColumnInfo
  String cardURL;
}
