package com.hestia.datalayer;

import android.arch.lifecycle.LiveData;
import android.arch.paging.PagedList;
import android.graphics.Bitmap;

import com.hestia.datalayer.Card.CardDecorator;
import com.hestia.domainlayer.Card;
import com.hestia.presentationlayer.displaycards.DisplayCardsContract;
import com.hestia.presentationlayer.editdeck.EditDeckContract;
import com.hestia.presentationlayer.editdeck.EditDeckPresenter;
import com.hestia.presentationlayer.singledeck.SingleDeckContract;

import java.util.List;

/**
 * Created by Richard on 3/31/2018.
 */

public interface CardRepository {

  Card getCardByID(String ID);

  void getCardBatch(int batchSize);

  LiveData<PagedList<CardDecorator>> generateOrdered(String column, boolean desc);
  LiveData<PagedList<CardDecorator>> generateSearchResults(String search, boolean desc);

  void generateFiltered(DisplayCardsContract.Presenter presenter, DisplayCardsContract.View view,
                        int cost, int classID);
  void getEditableCards(EditDeckContract.Presenter presenter, int classID);
  void getCardsFromString(SingleDeckContract.Presenter cPresenter, String deckString);
}
