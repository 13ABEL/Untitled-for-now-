package com.hestia.presentationlayer.displaydecks;

import com.hestia.domainlayer.Deck;
import com.hestia.presentationlayer.Base;
import com.hestia.presentationlayer.DeckDecorator;

import java.util.List;


/**
 * Created by Richard on 3/6/2018.
 *
 * This is a contract for the Presenters and Views
 *  > The interface will ensure we can easily swap implementations out
 *  > Also ensure out implementations implement "x" method(s)
 */
public interface DisplayDecksContract {

  interface Presenter extends Base.BasePresenter {
    void getNextDecks(int numDecks);

    void receiveDeckBatch(List<DeckDecorator> deck);
  }

  interface View extends Base.BaseView {
    void addDecks(List <DeckDecorator> decks);
  }
}
