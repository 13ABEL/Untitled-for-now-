package com.hestia.presentationlayer;

import android.os.Parcel;
import android.os.Parcelable;

import com.hestia.domainlayer.DeckImpl;

/**
 * Created by Richard on 3/28/2018.
 *
 * Use this to maintain the separation between our models and the Android framework.
 * We need a parcelable object to pass between fragments
 *
 */

public class DeckDecorator extends DeckImpl implements Parcelable {

  private DeckDecorator (Parcel parcelIn) {
    // reads all the data from parcel and stores it in this object
  }

  public static final Parcelable.Creator<DeckDecorator> CREATOR =
      new Parcelable.Creator<DeckDecorator>() {

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
    // writes all the data in the current parcelable object to the outgoing parcel
    parcelOut.writeString("");
  }

  @Override
  public int describeContents() {
    return 0;
  }


}