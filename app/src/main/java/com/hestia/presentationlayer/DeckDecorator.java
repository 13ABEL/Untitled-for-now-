package com.hestia.presentationlayer;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import com.hestia.domainlayer.Deck;
import com.hestia.domainlayer.DeckImpl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Created by Richard on 3/28/2018.
 *
 * Use this to maintain the separation between our models and the Android framework.
 * We need a parcelable object to pass between fragments
 *
 */

@Entity
public class DeckDecorator extends DeckImpl implements Parcelable{

  public DeckDecorator () {
    super();
  }

  public DeckDecorator (String deckID, String author, String deckClass) {
    super(deckID, author, deckClass);
  }

  public DeckDecorator (String deckID, String name, String author, String list, String info, Date date) {
    super(deckID, name, author, list, info, date);
  }

  public DeckDecorator (Parcel parcelIn) {
    // retrieve the map from the parcel in order
    this.deckName = parcelIn.readString();
    this.username = parcelIn.readString();
    String summary = parcelIn.readString();
    this.deckID =parcelIn.readString();
    this.authorID = parcelIn.readString();
    this.createdDateString = parcelIn.readString();

    setSummary(summary);
  }

  public static final Parcelable.Creator<DeckDecorator> CREATOR =
      new Parcelable.Creator<DeckDecorator>() {

    /**
     * Generates the parcelable object from the parcel
     * @param parcel
     * @return
     */
    @Override
    public DeckDecorator createFromParcel(Parcel parcel) {
      return new DeckDecorator(parcel);
    }

    @Override
    public DeckDecorator[] newArray(int size) {
          return new DeckDecorator[size];
        }
  };

  @Override
  public void writeToParcel(Parcel parcelOut, int flags) {
    // puts the object info into the map
    parcelOut.writeString(this.getDeckName());
    parcelOut.writeString(this.getAuthor());
    parcelOut.writeString(this.getSummary());
    parcelOut.writeString(this.getDeckID());
    parcelOut.writeString(this.authorID);
    parcelOut.writeString(this.createdDateString);
  }

  @Override
  public int describeContents() {
    return 0;
  }


}