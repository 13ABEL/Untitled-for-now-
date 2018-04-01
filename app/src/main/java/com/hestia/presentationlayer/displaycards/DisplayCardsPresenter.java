package com.hestia.presentationlayer.displaycards;


import com.hestia.datalayer.Card.CardDecorator;
import com.hestia.domainlayer.Card;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Richard on 3/30/2018.
 */

public class DisplayCardsPresenter implements DisplayCardsContract.Presenter {
  private DisplayCardsContract.View displayCardsView;

  public DisplayCardsPresenter (DisplayCardsContract.View view) {
    this.displayCardsView = view;
  }

  public DisplayCardsContract.View getView () {
    return displayCardsView;
  }

  public void receiveCardBatch (List <CardDecorator> cardBatch) {
    // passes the cards to the view to display
    displayCardsView.displayCardBatch(cardBatch);
  }

}
