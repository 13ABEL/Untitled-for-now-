package com.hestia.presentationlayer.displaycards;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.content.Context;

import com.hestia.datalayer.Card.CardDao;
import com.hestia.datalayer.Card.CardDatabase;
import com.hestia.datalayer.Card.CardDecorator;


public class DisplayCardsVM extends ViewModel {
  public LiveData<PagedList<CardDecorator>> cardList = null;

  public DisplayCardsVM() {}

  public LiveData<PagedList<CardDecorator>> getCards(Context currentContext, int cardCost) {

    // gets the current DAO model to interact with the database
    CardDatabase cardDatabase = CardDatabase.getDatabase(currentContext);
    CardDao cardDao = cardDatabase.cardModel();

    // initializes the list if it is null
    if (cardList == null) {
      // page config to customize how our data is loaded
      PagedList.Config pagedListConfig=(new PagedList.Config.Builder()).setEnablePlaceholders(true)
          .setPrefetchDistance(15)
          .setPageSize(30).build();
      this.cardList = new LivePagedListBuilder<>(cardDao.getByCost(cardCost), pagedListConfig).build();
    }
    return cardList;
  }
}
