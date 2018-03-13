package com.hestia.presentationlayer.displaydecks;

import com.hestia.datalayer.DeckRepository;
import com.hestia.datalayer.DeckRepositoryImpl;
import com.hestia.domainlayer.Deck;
import com.hestia.domainlayer.DeckImpl;

import java.lang.reflect.Array;
import java.util.ArrayList;
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

    // initialize the repository object to get data
    deckRepository = new DeckRepositoryImpl(this);

    // gets all the objects from the room database
    deckList = new ArrayList <Deck> ();
    getAllUsers();
  }

  public void getAllUsers () {
    deckRepository.getDeck( 0);
  }

  private void getUser() {

  }

  public void addDecksListener(ArrayList<Deck> decks) {
    // adds to the list of decks

    Deck deck1 = new DeckImpl();

    decks.add(deck1);
    decks.add(deck1);
    decks.add(deck1);
    decks.add(deck1);
    decks.add(deck1);
    decks.add(deck1);
    displayDeckView.addDecks(decks);
  }



}
