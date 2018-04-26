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
import com.hestia.domainlayer.CardImpl;
import com.hestia.presentationlayer.DeckDecorator;
import com.hestia.presentationlayer.displaycards.DisplayCardsContract;
import com.hestia.presentationlayer.singledeck.SingleDeckContract;
import com.hestia.presentationlayer.singledeck.SingleDeckPresenter;


import java.util.ArrayList;

import java.util.List;


/**
 * Created by Richard on 3/31/2018.
 */

public class CardRepositoryImpl implements CardRepository {
  private static final String TAG = "CARD_REPOSITORY";
  private static CardDatabase cardDatabase;

  private DisplayCardsContract.Presenter presenter;
  private Context viewContext;

  private CardDao cardModel;

  // configuration for paged list
  private PagedList.Config pagedListConfig;

  private LiveData<PagedList<CardDecorator>> livePagedCards;


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
    // gets the context of the view and gets the database instance based on it
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






  /**
   * Generates a filtered live paged-list based on filter value
   * @param column column to filter
   * @param value value of column to display
   * @return the live page data associated with the filter applied
   */
  public LiveData<PagedList<CardDecorator>> generateFiltered(String column, String value) {
    cardModel = cardDatabase.cardModel();
    DataSource.Factory <Integer, CardDecorator> cardFactory;

    // redirects the query based on the column name
    if (column.equals("expansion")) {
      cardFactory = cardModel.getByExpansion(value);
    }
    else if (column.equals("cost")) {
      cardFactory = cardModel.getByCost(value);
    }
    else {
      cardFactory = cardModel.getByExpansion(value);
    }

    // build the new Live paged list based on the factory produced
    this.livePagedCards = new LivePagedListBuilder<>(cardFactory, this.pagedListConfig).build();
    return livePagedCards;
  }


  /**
   * Generates an ordered live paged lists based on a column
   * @param column the general name of the column to be rerouted
   * @param desc orders the results descending if true
   * @return the live page data associated with the method call
   */
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


  /**
   * Generates a live paged list for search results
   * @param search
   * @param desc
   * @return
   */
  public LiveData<PagedList<CardDecorator>> generateSearchResults(String search, boolean desc) {
    cardModel = cardDatabase.cardModel();
    DataSource.Factory <Integer, CardDecorator> cardFactory;

    // add the appropriate characters to make use of the "like" selector
    cardFactory = cardModel.getBySearch("%" + search + "%");

    this.livePagedCards = new LivePagedListBuilder<Integer, CardDecorator>(cardFactory, pagedListConfig).build();
    return livePagedCards;
  }


  public LiveData<PagedList<CardDecorator>> generateDeckCards(int classID, boolean isStandard) {
    cardModel = cardDatabase.cardModel();
    DataSource.Factory <Integer, CardDecorator> cardFactory;

    // add the appropriate characters to make use of the "like" selector
    cardFactory = cardModel.getEditable(classID);

    this.livePagedCards = new LivePagedListBuilder<Integer, CardDecorator>(cardFactory, pagedListConfig).build();
    return livePagedCards;
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




  @Override
  public void getCardsFromString(SingleDeckContract.Presenter presenter, String deckString) {
    new ParseCardString().execute(presenter, deckString);
  }

  static class ParseCardString extends AsyncTask <Object, Integer, List<Card>> {
    private SingleDeckPresenter singleDeckPresenter;
    @Override
    protected List<Card> doInBackground(Object... objects) {
      this.singleDeckPresenter = (SingleDeckPresenter) objects[0];
      String deckString = (String) objects[1];

      String[] deckArray = new String[30];

      // checks the deck for cards
      if (deckString != null) {
        // parse the deck to generate a query string
        for (int i = 0; i < deckString.length() / 4; i++) {
          deckArray[i] = deckString.substring(i * 3, i * 3 + 4);
          Log.d(TAG, "CARD ID " + deckArray[i]);
        }
      }

      return new ArrayList<>(cardDatabase.cardModel().getCardList(deckArray));
    }
    protected void onPostExecute(List<Card> newDeckList) {
      Log.d(TAG, newDeckList.toString());
      // sends the new deck of cards to the presenter
      singleDeckPresenter.receiveDeckList(newDeckList);
    }
  }



}

