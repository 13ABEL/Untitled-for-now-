package com.hestia.presentationlayer.displaycards;


import com.hestia.datalayer.Card.CardDecorator;
import com.hestia.datalayer.CardRepository;
import com.hestia.datalayer.CardRepositoryImpl;
import com.hestia.domainlayer.Card;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Richard on 3/30/2018.
 */

public class DisplayCardsPresenter implements DisplayCardsContract.Presenter {
  private DisplayCardsContract.View displayCardsView;
  private CardRepository cardRepo;

  private static final int BATCH_SIZE = 100;
  int currentBatchEnd = 0;

  public DisplayCardsPresenter (DisplayCardsContract.View view) {
    this.displayCardsView = view;

    // initialize the instance of the repository
    cardRepo = new CardRepositoryImpl();
  }

  public DisplayCardsContract.View getView () {
    return displayCardsView;
  }


  public void fetchCardBatch() {
    cardRepo.getCardBatch(this, BATCH_SIZE);
    //TODO update the currentBatchEnd value
  }

  public void receiveCardBatch (List <CardDecorator> cardBatch) {
    // passes the cards to the view to display
    displayCardsView.displayCardBatch(cardBatch);
  }

}
