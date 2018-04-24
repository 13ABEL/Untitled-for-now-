package com.hestia.presentationlayer.displaysaved;

import com.hestia.datalayer.DeckRepository;
import com.hestia.datalayer.DeckRepositoryImpl;
import com.hestia.domainlayer.Deck;
import com.hestia.presentationlayer.DeckDecorator;

import java.util.List;

public class DisplaySavedPresenter implements DisplaySavedContract.Presenter {
  DisplaySavedContract.View displaySavedView;

  private DeckRepository deckRepo;

  private List<Deck> savedDeckList;

  public DisplaySavedPresenter(DisplaySavedContract.View view){
    this.displaySavedView = view;
  }

  @Override
  public void injectDependencies(DeckRepository deckRepo) {
    this.deckRepo = deckRepo;
  }

  @Override
  public void getDeckBatch() {
    deckRepo.getSavedBatch(this);
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
