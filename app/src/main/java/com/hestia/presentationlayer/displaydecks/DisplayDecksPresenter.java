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
  }

  public void getAllUsers () {
    // gets all the objects from the room database
    List decks = new ArrayList <Deck> ();

    // temporarily testing please dont hurt me
    Deck deck1 = new DeckImpl("1");


    decks.add(deck1);
    decks.add(deck1);
    decks.add(deck1);
    decks.add(deck1);
    decks.add(deck1);
    decks.add(deck1);
    decks.add(deck1);
    decks.add(deck1);
    decks.add(deck1);
    decks.add(deck1);
    decks.add(deck1);
    decks.add(deck1);
    decks.add(deck1);
    decks.add(deck1);
    decks.add(deck1);
    decks.add(deck1);
    decks.add(deck1);
    decks.add(deck1);
    decks.add(deck1);
    decks.add(deck1);

    Deck deckssss = deckRepository.getDeck( 0);
    decks.add(deckssss);
    // tells the view to display all the users
    displayDeckView.displayMultiUsers(decks);
  }

  private void getUser() {

  }


  public void addDecksListener(ArrayList<Deck> decks) {
    displayDeckView.displayMultiUsers(decks);

  }





}
