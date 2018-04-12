package com.hestia.presentationlayer.displaycards;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.DataSource;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.content.Context;

import com.hestia.datalayer.Card.CardDao;
import com.hestia.datalayer.Card.CardDatabase;
import com.hestia.datalayer.Card.CardDecorator;
import com.hestia.datalayer.CardRepository;
import com.hestia.datalayer.CardRepositoryImpl;


public class DisplayCardsVM extends ViewModel {
  private LiveData<PagedList<CardDecorator>> cardList = null;

  public DisplayCardsVM() {}

  public LiveData<PagedList<CardDecorator>> getCards(Context currentContext, int cardCost) {

    // initializes the list if it is null
    if (cardList == null) {

      // creates an instance of the repository to interact with the database
      CardRepository cardRepo = new CardRepositoryImpl(currentContext);
      // generate the livedata paged list and attach it to this viewmodel
      cardList = cardRepo.generateOrdered("name", true);
    }
    return cardList;
  }

}