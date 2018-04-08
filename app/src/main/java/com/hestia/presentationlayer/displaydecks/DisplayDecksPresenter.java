package com.hestia.presentationlayer.displaydecks;

import com.hestia.datalayer.DeckRepository;
import com.hestia.datalayer.DeckRepositoryImpl;
import com.hestia.domainlayer.Deck;
import com.hestia.presentationlayer.DeckDecorator;

import java.util.List;

/**
 * Created by Richard on 3/6/2018.
 *
 * Presenter is used to handle the logic associated with fragment
 * > The presenter's methods are called from the view's instance of the presenter
 * > Since the presenter retains a reference to the view, we can tell it to
 * update the UI when needed
 *
 */

public class DisplayDecksPresenter implements DisplayDecksContract.Presenter{
  private DisplayDecksContract.View displayDeckView;
  private DeckRepository deckRepository;

  private List<Deck> deckList;

  /**
   * Constructor for the Presenter:
   * Associates the instance of the presenter to the view
   * @param view
   */
  public DisplayDecksPresenter (DisplayDecksContract.View view) {
    this.displayDeckView = view;

    // initialize the instance of the deck repository and retrieve the first deck batch
    deckRepository = new DeckRepositoryImpl();
    deckRepository.getDeckBatch(this, 100);
  }

  public void getDeckBatch() {
    deckRepository.getDeckBatch(this, 10);
  }


  // TODO
  private void getUser() {
  }

  /**
   * Receives method call after async task resolves to update the UI
   * @param decks
   */
  public void receiveDeckBatch(List<DeckDecorator> decks) {
    // adds to the list of decks
    displayDeckView.addDecks(decks);
  }



}
