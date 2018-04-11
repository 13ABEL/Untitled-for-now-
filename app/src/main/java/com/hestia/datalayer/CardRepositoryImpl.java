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

  private CardDao cardModel;

  // configuration for paged list
  PagedList.Config pagedListConfig;



  LiveData<PagedList<CardDecorator>> livePagedCards;


  public CardRepositoryImpl(DisplayCardsContract.Presenter newPresenter) {
    this.presenter = newPresenter;

    // gets the context of the view and updates the database instance
    this.viewContext = presenter.getView().getViewContext();
    this.cardDatabase = CardDatabase.getDatabase(viewContext);


    // page config to customize how our data is loaded
    pagedListConfig =(new PagedList.Config.Builder()).setEnablePlaceholders(true)
        .setPrefetchDistance(15)
        .setPageSize(30).build();
  }

  public CardRepositoryImpl(Context currentContext) {

    // gets the context of the view and updates the database instance
    this.viewContext = currentContext;
    this.cardDatabase = CardDatabase.getDatabase(viewContext);

    // page config to customize how our data is loaded
    pagedListConfig =(new PagedList.Config.Builder()).setEnablePlaceholders(true)
        .setPrefetchDistance(15)
        .setPageSize(30).build();
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

  //TODO create a page for generating ordered by
  public LiveData<PagedList<CardDecorator>> generateFiltered(String column, int value) {
    cardModel = cardDatabase.cardModel();

    // redirects the query based on the column name
    if (column == "expansion") {
      new LivePagedListBuilder<>(cardModel.getByCost(), pagedListConfig).build();
    }
    else if (column == "") {

    }
    return null;
  }

  public LiveData<PagedList<CardDecorator>> generateOrdered(String column, boolean desc) {
    cardModel = cardDatabase.cardModel();
    DataSource.Factory <Integer, CardDecorator> cardFactory;

    // redirects the query based on the column name
    if (column.equals("expansion")) {
      cardFactory = cardModel.getByCostOrder();
    }
    else if (column.equals("name")) {
      cardFactory = cardModel.getByNameOrder();
    }
    else {
      // returns default sort if the value is invalid
      cardFactory = cardModel.getByCostOrder();
    }

    this.livePagedCards = new LivePagedListBuilder<Integer, CardDecorator>(cardFactory, pagedListConfig).build();
    return livePagedCards;
  }



}

