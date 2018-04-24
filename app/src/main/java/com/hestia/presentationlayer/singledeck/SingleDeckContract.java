package com.hestia.presentationlayer.singledeck;

import android.arch.lifecycle.LiveData;
import android.arch.paging.PagedList;
import android.support.v4.app.Fragment;

import com.hestia.datalayer.Card.CardDecorator;
import com.hestia.datalayer.CardRepository;
import com.hestia.datalayer.UserRepository;
import com.hestia.domainlayer.Card;
import com.hestia.domainlayer.Deck;
import com.hestia.presentationlayer.DeckDecorator;
import com.hestia.presentationlayer.displaydecks.DisplayDecksContract;

import java.util.List;

/**
 * Created by Richard on 3/14/2018.
 */

public interface SingleDeckContract {

  interface View {
    void displayInfo(String title);
    void displayEditOption();
  }

  interface Presenter {
    /**
     * Inject the repository for retrieving card objects
     * @param cardRepo implementation of card repo
     */
    void injectDependencies(CardRepository cardRepo);

    void getDeckList();
    void receiveDeckList(List <Card> deckList);



    void receiveFullDeck(DeckDecorator deck);
    void addTabFragment(TabFragment tabFragment, int position);
    void addInfoTabFragment(InfoFragment newInfoFrag);
    void addDeckTabFragment(DeckFragment newInfoFrag);
    void saveDeck();
  }


}
