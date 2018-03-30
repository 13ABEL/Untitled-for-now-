package com.hestia.presentationlayer.singledeck;

import android.support.v4.app.Fragment;

import com.hestia.domainlayer.Deck;
import com.hestia.presentationlayer.DeckDecorator;
import com.hestia.presentationlayer.displaydecks.DisplayDecksContract;

/**
 * Created by Richard on 3/14/2018.
 */

public interface SingleDeckContract {

  interface View {
    void displayInfo(String title);
  }

  interface Presenter {
    void receiveFullDeck(DeckDecorator deck);
    void addTabFragment(TabFragment tabFragment, int position);
    void addInfoTabFragment(InfoFragment newInfoFrag);
    void addDeckTabFragment(DeckFragment newInfoFrag);
  }


}
