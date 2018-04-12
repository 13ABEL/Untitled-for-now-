package com.hestia.datalayer;

import android.arch.lifecycle.LiveData;
import android.arch.paging.PagedList;

import com.hestia.datalayer.Card.CardDecorator;
import com.hestia.domainlayer.Card;
import com.hestia.presentationlayer.displaycards.DisplayCardsContract;

/**
 * Created by Richard on 3/31/2018.
 */

public interface CardRepository {

  Card getCardByID(String ID);

  void getCardBatch(int batchSize);

  LiveData<PagedList<CardDecorator>> generateOrdered(String column, boolean desc);
  LiveData<PagedList<CardDecorator>> generateFiltered(String column, String value);

}
