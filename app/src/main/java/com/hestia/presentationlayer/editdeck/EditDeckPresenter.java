package com.hestia.presentationlayer.editdeck;

import com.hestia.datalayer.Card.CardDecorator;
import com.hestia.datalayer.UserRepository;
import com.hestia.datalayer.UserRepositoryImpl;
import com.hestia.domainlayer.Card;
import com.hestia.domainlayer.Deck;

import java.util.ArrayList;
import java.util.Collection;

public class EditDeckPresenter implements EditDeckContract.Presenter {
  final private String LEGENDARY = "Legendary";

  // reference to the view the presenter manages
  EditDeckContract.View cView;

  private Deck editDeck;

  private Collection <Card> cardList;
  //List <CardDecorator> currentDeckList;

  EditDeckPresenter(EditDeckContract.View ownerView, Deck newDeck) {
    this.cView = ownerView;
    this.editDeck = newDeck;

    // initialize the arraylist to hold the cards
    cardList = new ArrayList<>();

    // extract the current cardlist from the deck

  }

  @Override
  public void addToNewDeck(CardDecorator currCard) {
    // attempts to add the card to the deck, storing result
    int cardsAdded = editDeck.addToDeck(currCard);

    // tells view to update based on the result
    if (cardsAdded == 1) {
      cView.showCardAdded(true);
    }
    else if (cardsAdded == -1 ) {
      cView.showCardAdded(false);
    }
  }

  /**
   * Saves the deck and uploads it to the firestore database
   */
  @Override
  public void saveChanges() {
    // create a reference to the repository (move instantiation to exterior class after)
    UserRepository userRepository = new UserRepositoryImpl();
    userRepository.saveNewDeck(editDeck);
  }


}
