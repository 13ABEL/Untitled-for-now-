package com.hestia.presentationlayer.displaysaved;

import com.hestia.presentationlayer.DeckDecorator;

import java.util.List;

/**
 * Created by Richard on 4/5/2018.
 */

public interface Contract {
  interface View {
    void addDecks(List<DeckDecorator> decks);
  }

  interface Presenter {
    void getDeckBatch();
    void receiveSavedBatch(List<DeckDecorator> decks);
  }


}
