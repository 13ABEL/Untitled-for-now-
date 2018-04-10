package com.hestia.datalayer;

import android.arch.lifecycle.LiveData;
import android.arch.paging.DataSource;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;

import com.hestia.datalayer.Card.CardDao;
import com.hestia.datalayer.Card.CardDatabase;
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
  private final String TAG = "CARD_REPOSITORY";
  private CardDatabase cardDatabase;

  private DisplayCardsContract.Presenter presenter;
  private Context viewContext;


  private Cursor cardCursor;

  public CardRepositoryImpl(DisplayCardsContract.Presenter newPresenter) {

    this.presenter = newPresenter;

    // gets the context of the view and updates the database instance
    this.viewContext = presenter.getView().getViewContext();
    this.cardDatabase = CardDatabase.getDatabase(viewContext);
  }

  public CardRepositoryImpl(Context currentContext) {

    // gets the context of the view and updates the database instance
    this.viewContext = currentContext;
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


  class NewTask extends AsyncTask <Object, Integer, List<CardDecorator>>{
    private  DisplayCardsContract.Presenter presenter;

    @Override
    protected List<CardDecorator> doInBackground(Object... objects) {
      this.presenter = (DisplayCardsContract.Presenter) objects[0];
      int numCards = (int) objects[1];

      List<CardDecorator> cardList = cardDatabase.cardModel().getBatch(numCards);

      // gets the batch of cards
      return cardList;
    }

    protected void onPostExecute(List<CardDecorator> returnedCards) {
      // routes the information back to the presenter
      presenter.receiveCardBatch(returnedCards);
    }
  }


  public void getByCost(int cardCost) {
    //new RetrieveByCost().execute(cardCost);
  }


//  class RetrieveByCost extends AsyncTask <Integer, Integer, Object> {
//    @Override
//    protected Object doInBackground(Integer... integers) {
//      Cursor cardsByCost = cardDatabase.cardModel().getByCost(integers[0]);
//      cardsByCost.moveToFirst();
//      Log.d(TAG, cardCursor.toString());
//
//      return null;
//    }
//  }



}

