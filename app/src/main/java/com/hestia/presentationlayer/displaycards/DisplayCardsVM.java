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

  public void initializeRepo (Context newContext) {
    cardRepo = new CardRepositoryImpl(newContext);
  }

  public LiveData<PagedList<CardDecorator>> getCards(int cardCost) {
    // generate the live paged list and attach it to this viewmodel
    cardList = cardRepo.generateOrdered("name", true);

    return cardList;
  }

  public LiveData<PagedList<CardDecorator>> getSearchResult(String searchText) {
    // generate the live paged list and attach it to this viewmodel
    cardList = cardRepo.generateSearchResults(searchText, true);

    return cardList;
  }

  public LiveData<PagedList<CardDecorator>> getCreateDecks(String className, boolean isStandard) {
    return null;
  }

}
