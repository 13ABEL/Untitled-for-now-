package com.hestia.presentationlayer.displaysaved;

import com.hestia.datalayer.DeckRepository;
import com.hestia.datalayer.DeckRepositoryImpl;
import com.hestia.domainlayer.Deck;
import com.hestia.presentationlayer.DeckDecorator;

import java.util.List;

public class DisplaySavedPresenter implements Contract.Presenter {
  Contract.View displaySavedView;

  private DeckRepository savedDeckRepository;

  private List<Deck> savedDeckList;

  public DisplaySavedPresenter(Contract.View view){
    this.displaySavedView = view;

    // initialize the instance of the deck repository and retrieve the first deck batch
    savedDeckRepository = new DeckRepositoryImpl();
    savedDeckRepository.getSavedBatch(this);
  }


  @Override
  public void getDeckBatch() {
    savedDeckRepository.getSavedBatch(this);
  }

  /**
   * Receives method call after async task resolves to update the UI
   * @param decks
   */
  public void receiveSavedBatch(List<DeckDecorator> decks) {
    // adds to the list of decks
    displaySavedView.addDecks(decks);
  }

}
