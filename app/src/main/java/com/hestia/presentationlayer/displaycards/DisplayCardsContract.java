package com.hestia.presentationlayer.displaycards;

import android.content.Context;

import com.hestia.datalayer.Card.CardDecorator;
import com.hestia.domainlayer.Card;
import com.hestia.presentationlayer.Base;

import java.util.List;

/**
 * Created by Richard on 3/30/2018.
 */

public interface DisplayCardsContract {

  interface View extends Base.BaseView {
    Context getViewContext();
    void displayCardBatch(List <CardDecorator> cardBatch);
  }


  interface Presenter extends Base.BasePresenter {
    View getView();
    void fetchCardBatch();
    void receiveCardBatch(List <CardDecorator> cardBatch);
  }



}
