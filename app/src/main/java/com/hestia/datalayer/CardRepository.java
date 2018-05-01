package com.hestia.datalayer;

import android.arch.lifecycle.LiveData;
import android.arch.paging.PagedList;

import com.hestia.datalayer.Card.CardDecorator;
import com.hestia.domainlayer.Card;
import com.hestia.presentationlayer.displaycards.DisplayCardsContract;
import com.hestia.presentationlayer.singledeck.SingleDeckContract;

import java.util.List;

/**
 * Created by Richard on 3/31/2018.
 */

public interface CardRepository {

  Card getCardByID(String ID);

  void getCardBatch(int batchSize);

  LiveData<PagedList<CardDecorator>> generateOrdered(String column, boolean desc);
  LiveData<PagedList<CardDecorator>> generateFiltered(int cost, int classID);
  LiveData<PagedList<CardDecorator>> generateSearchResults(String search, boolean desc);
  LiveData<PagedList<CardDecorator>> generateDeckCards(int classID, boolean isStandard);

  void getCardsFromString(SingleDeckContract.Presenter cPresenter, String deckString);

}
