package com.hestia.presentationlayer.displaydecks;

import com.hestia.domainlayer.Deck;

import java.util.List;

/**
 * Created by Richard on 3/6/2018.
 *
 * Presenter is used to handle the logic associated with fragment
 * > The presenter's methods are called from the view's instance of the presenter
 * > Since the presenter retains a reference to the view, we can tell it to
 * update the UI when needed
 *
 */

public class DisplayDecksPresenter implements DisplayDecksContract.Presenter{
  private DisplayDecksContract.View displayDeckView;

  private List<Deck> deckList;

  /**
   * Constructor for the Presenter:
   * Associates the instance of the presenter to the view
   * @param view
   */
  public DisplayDecksPresenter (DisplayDecksContract.View view) {
    this.displayDeckView = view;
  }







}
