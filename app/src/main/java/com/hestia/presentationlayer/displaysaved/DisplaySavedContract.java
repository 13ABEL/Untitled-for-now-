package com.hestia.presentationlayer.displaysaved;

import com.hestia.datalayer.DeckRepository;
import com.hestia.presentationlayer.Base;
import com.hestia.presentationlayer.DeckDecorator;

import java.util.List;

/**
 * Created by Richard on 4/5/2018.
 */

public interface DisplaySavedContract {
  interface View extends Base.BaseView{
    void addDecks(List<DeckDecorator> decks);
  }

  interface Presenter extends Base.BasePresenter {
    void injectDependencies(DeckRepository deckRepo);
    void getDeckBatch();
    void receiveSavedBatch(List<DeckDecorator> decks);
  }


}
