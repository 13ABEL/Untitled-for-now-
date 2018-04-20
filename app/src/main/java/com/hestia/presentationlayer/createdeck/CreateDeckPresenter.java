package com.hestia.presentationlayer.createdeck;

import com.hestia.datalayer.Card.CardDecorator;
import com.hestia.presentationlayer.DeckDecorator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class CreateDeckPresenter implements CreateDeckContract.Presenter {
  final private String LEGENDARY = "Legendary";


  CreateDeckContract.View cView;


  //
  private Collection <CardDecorator> cardList;

  List <CardDecorator> currentDeckList;


  CreateDeckPresenter(CreateDeckContract.View newView) {
    this.cView = newView;
    // initialize the arraylist to hold the cards
    currentDeckList = new ArrayList<>();
    cardList = new ArrayList<>();
  }



  @Override
  public void addToNewDeck(CardDecorator currCard) {
    // gets rarity of card and number of occurrences in the deck
    String rarity = currCard.getRarity();
    int numCard = Collections.frequency(cardList, currCard);

    // removes the card if the deck already has max copies of it
    if (rarity.equals(LEGENDARY) && numCard == 1 || numCard == 2) {
      cardList.remove(currCard);
      // tells the view to display cards being removed
      cView.showCardAdded(false);
    }
    // add the current card if it's not being removed and the deck still has room
    else if (cardList.size() < 30) {
      cardList.add(currCard);
      // tells the view to show cards being added
      cView.showCardAdded(true);
    }
  }

  public int getDeckSize() {
    return cardList.size();
  }

}
