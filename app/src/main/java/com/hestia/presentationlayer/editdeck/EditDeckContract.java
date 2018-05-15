package com.hestia.presentationlayer.editdeck;

import android.arch.lifecycle.LiveData;
import android.arch.paging.PagedList;

import com.hestia.datalayer.Card.CardDecorator;
import com.hestia.datalayer.CardRepository;
import com.hestia.domainlayer.Card;
import com.hestia.presentationlayer.Base;

import java.util.List;

public interface EditDeckContract {
  interface View extends Base.BaseView {
    void showCardAdded(boolean cardAdded);
    void displayCards(List <Card> cardList);
  }

  interface Presenter extends Base.BasePresenter {
    // event to add/remove cards from the deck
    void addToNewDeck(Card card);
    // saves the deck to firestore
    void saveChanges();

    void injectDependencies(CardRepository cardRepo);
    void retrieveCards(int classID);
  }

}
