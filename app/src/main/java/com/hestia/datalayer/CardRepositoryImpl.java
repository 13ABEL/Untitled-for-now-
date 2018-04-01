package com.hestia.datalayer;

import android.content.Context;
import android.os.AsyncTask;

import com.google.android.gms.tasks.Task;
import com.hestia.datalayer.Card.CardDecorator;
import com.hestia.domainlayer.Card;
import com.hestia.domainlayer.CardImpl;
import com.hestia.presentationlayer.displaycards.DisplayCardsContract;

import java.util.List;

/**
 * Created by Richard on 3/31/2018.
 */

public class CardRepositoryImpl implements CardRepository {
  @Override
  public Card getCardByID(String ID) {
    return null;
  }

  public List<CardDecorator> getCardBatch (DisplayCardsContract.Presenter presenter, int numCards) {
    // gets the context of the view
    Context viewContext = presenter.getView().getViewContext();

    CardDatabase cardDatabase = CardDatabase.getDatabase(viewContext);
    List <CardDecorator> returnedCards = cardDatabase.cardModel().getBatch(numCards);

    // routes the information back to the presenter
    presenter.receiveCardBatch(returnedCards);

    return returnedCards;
  }



}

