package com.hestia.presentationlayer.displaycards;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.hestia.datalayer.Card.CardDao;
import com.hestia.datalayer.Card.CardDatabase;
import com.hestia.datalayer.Card.CardDecorator;
import com.hestia.datalayer.CardRepositoryImpl;

import java.util.concurrent.Executor;


public class DisplayCardsVM extends ViewModel {
  public LiveData<PagedList<CardDecorator>> cardList = null;

  public DisplayCardsVM() {
    super();
  }

  public LiveData<PagedList<CardDecorator>> getCards(Context currentContext) {

    CardDatabase cardDatabase = CardDatabase.getDatabase(currentContext);

    Log.d("PAGEDLIST", cardDatabase.toString());
    Log.d("PAGEDLIST", cardDatabase.cardModel().toString());
    Log.d("PAGEDLIST", cardDatabase.cardModel().getByCost().toString());

    CardDao cardDao = cardDatabase.cardModel();

    //if (cardList == null) {
      this.cardList = new LivePagedListBuilder<>(cardDao.getByCost(), 12).build();

    //}

    if (cardList == null) {
      Log.d("CARDLISTNULL", "YES");
    } else {
      Log.d("CARDLISTNULL", "NO");
    }

    return cardList;
  }
}
