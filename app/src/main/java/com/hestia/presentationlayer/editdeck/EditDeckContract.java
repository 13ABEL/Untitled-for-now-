package com.hestia.presentationlayer.editdeck;

import com.hestia.datalayer.Card.CardDecorator;
import com.hestia.presentationlayer.Base;

public interface EditDeckContract {
  interface View extends Base.BaseView {
    void showCardAdded(boolean cardAdded);
  }

  interface Presenter extends Base.BasePresenter {
    // event to add/remove cards from the deck
    void addToNewDeck(CardDecorator card);
    // saves the deck to firestore
    void saveChanges();
  }

}