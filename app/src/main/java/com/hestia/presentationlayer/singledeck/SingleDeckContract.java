package com.hestia.presentationlayer.singledeck;

import android.support.v4.app.Fragment;

import com.hestia.domainlayer.Deck;
import com.hestia.presentationlayer.displaydecks.DisplayDecksContract;

/**
 * Created by Richard on 3/14/2018.
 */

public interface SingleDeckContract {

  interface View {

  }

  interface Presenter {
    void receiveFullDeck(Deck deck);
    void addTabFragment(TabFragment tabFragment);
  }
}
