package com.hestia.presentationlayer.createdeck;

import com.hestia.datalayer.Card.CardDecorator;
import com.hestia.presentationlayer.Base;

public interface CreateDeckContract {
  interface View extends Base.BaseView {

  }
  interface Presenter extends Base.BasePresenter {
    public void addToNewDeck(CardDecorator card);
  }


}
