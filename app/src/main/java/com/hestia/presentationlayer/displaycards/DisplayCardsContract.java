package com.hestia.presentationlayer.displaycards;

import android.arch.lifecycle.LiveData;
import android.arch.paging.PagedList;
import android.content.Context;

import com.hestia.datalayer.Card.CardDecorator;
import com.hestia.datalayer.CardRepository;
import com.hestia.domainlayer.Card;
import com.hestia.presentationlayer.Base;

import java.util.List;

/**
 * Created by Richard on 3/30/2018.
 */

public interface DisplayCardsContract {

  interface View extends Base.BaseView {
    void displayCards(List <Card> cardList);
  }


  interface Presenter extends Base.BasePresenter {
    View getView();

    void injectDependencies(CardRepository cardRepo);
    void retrieveCards(DisplayCardsContract.View view, int classID, int cost);
    void receiveCardBatch(DisplayCardsContract.View tab, List <Card> cardBatch);
    List<Card> getCardSet();
  }



}
