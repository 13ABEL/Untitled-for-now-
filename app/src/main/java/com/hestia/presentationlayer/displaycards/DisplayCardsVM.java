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
  private CardRepository cardRepo;

  public DisplayCardsVM() {
    // TODO initialize the repository here and have the fragment context passed here
  }

  public LiveData<PagedList<CardDecorator>> getCards(Context currentContext, int cardCost) {
    // initializes the list if it is null
    if (cardList == null) {
      // creates an instance of the repository to interact with the database
      cardRepo = new CardRepositoryImpl(currentContext);
      // generate the live paged list and attach it to this viewmodel
      cardList = cardRepo.generateOrdered("name", true);
    }
    return cardList;
  }

  public LiveData<PagedList<CardDecorator>> getSearchResult(Context cContext, String searchText) {
    if (cardRepo == null) {
      cardRepo = new CardRepositoryImpl(cContext);
    }
    // generate the live paged list and attach it to this viewmodel
    cardList = cardRepo.generateSearchResults(searchText, true);

    return cardList;
  }



}
