package com.hestia.datalayer;

import android.content.Context;
import android.os.AsyncTask;

import com.hestia.datalayer.Card.CardDecorator;
import com.hestia.domainlayer.Card;
import com.hestia.presentationlayer.DeckDecorator;
import com.hestia.presentationlayer.displaycards.DisplayCardsContract;


import java.util.ArrayList;

import java.util.List;


/**
 * Created by Richard on 3/31/2018.
 */

public class CardRepositoryImpl implements CardRepository {
  private CardDatabase cardDatabase;

  private DisplayCardsContract.Presenter presenter;
  private Context viewContext;


  public CardRepositoryImpl(DisplayCardsContract.Presenter newPresenter) {

    this.presenter = newPresenter;

    // gets the context of the view and updates the database instance
    this.viewContext = presenter.getView().getViewContext();
    this.cardDatabase = CardDatabase.getDatabase(viewContext);
  }

  @Override
  public Card getCardByID(String ID) {
    return null;
  }


  public void getCardBatch (int numCards) {
    NewTask fetchBatchTask = new NewTask();
    fetchBatchTask.execute(presenter, numCards);
  }




  public ArrayList<DeckDecorator> parseReturn(String requestReturn) {
    return null;
  }



  class NewTask extends AsyncTask <Object, Integer, List<CardDecorator>>{
    private  DisplayCardsContract.Presenter presenter;

    @Override
    protected List<CardDecorator> doInBackground(Object... objects) {
      this.presenter = (DisplayCardsContract.Presenter) objects[0];
      int numCards = (int) objects[1];
      // gets the batch of cards
      return cardDatabase.cardModel().getBatch(numCards);
    }

    protected void onPostExecute(List<CardDecorator> returnedCards) {
      // routes the information back to the presenter
      presenter.receiveCardBatch(returnedCards);
    }
  }






}

