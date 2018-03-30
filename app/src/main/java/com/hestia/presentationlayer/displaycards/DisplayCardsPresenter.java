package com.hestia.presentationlayer.displaycards;


import com.hestia.presentationlayer.displaydecks.DisplayDecksPresenter;

/**
 * Created by Richard on 3/30/2018.
 */

public class DisplayCardsPresenter implements DisplayCardsContract.Presenter {
  private DisplayCardsContract.View displayCardsView;

  public DisplayCardsPresenter (DisplayCardsContract.View view) {
    this.displayCardsView = view;
  }




}
