package com.hestia.presentationlayer.createdeck;

import com.hestia.datalayer.Card.CardDecorator;
import com.hestia.presentationlayer.DeckDecorator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class CreateDeckPresenter implements CreateDeckContract.Presenter {
  CreateDeckContract.View cView;


  //
  private Collection cardList;

  List <CardDecorator> currentDeckList;


  CreateDeckPresenter(CreateDeckContract.View newView) {
    this.cView = newView;
    // initialize the arraylist to hold the cards
    currentDeckList = new ArrayList<>();
    cardList = new ArrayList();
  }


  @Override
  public void addToNewDeck(CardDecorator currCard) {
    // checks if a card with the same ID already exists within the deck
    int numCard = Collections.frequency(cardList, currCard);

    // checks if the deck already contains the max number of the card
    String rarity = currCard.getRarity();
    if (rarity.equals("legendary") && numCard == 0 || rarity.equals("legendary") && numCard < 2){
      // add the card to the array list and update the number of that card
    }

    // update the view to reflect the changes in the card
    //

  }
}
