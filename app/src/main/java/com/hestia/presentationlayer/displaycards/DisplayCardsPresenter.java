package com.hestia.presentationlayer.displaycards;


import android.arch.lifecycle.LiveData;
import android.arch.paging.PagedList;
import android.widget.Toast;

import com.hestia.datalayer.Card.CardDecorator;
import com.hestia.datalayer.CardRepository;
import com.hestia.datalayer.CardRepositoryImpl;
import com.hestia.domainlayer.Card;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Richard on 3/30/2018.
 */

public class DisplayCardsPresenter implements DisplayCardsContract.Presenter {
  private DisplayCardsContract.View displayCardsView;
  private CardRepository cardRepo;

  // holds the items that the adapter displays, want to keep it T H I N
  private ArrayList<Card> cardSet;

  public DisplayCardsPresenter (DisplayCardsContract.View view) {
    this.displayCardsView = view;

    // initialize the instance of the repository and the item set used by the adapter
    cardSet = new ArrayList<>();
  }

  @Override
  public void injectDependencies(CardRepository cardRepo) {
    this.cardRepo = cardRepo;
  }

  public DisplayCardsContract.View getView () {
    return displayCardsView;
  }


  public void retrieveCards(DisplayCardsContract.View view, int classID , int cost) {
    cardRepo.generateFiltered(this, view, classID, cost);
  }


  /**
   * Callback to receive the set of cards when they've been loaded by the repository
   * @param cardBatch
   */
  public void receiveCardBatch (DisplayCardsContract.View tab, List <Card> cardBatch) {
    if (cardBatch != null) {
      //Toast.makeText(displayCardsView.getViewContext(), cardBatch.size(), Toast.LENGTH_SHORT).show();
      // gets position of last element in items array
      int position = cardSet.size() - 1;
      // adds all the new cards to the current list of cards
      cardSet.addAll(cardBatch);
      // notifies the adapter that data has been added
      //displayCardsView.notifyAdapter(position);
      // passes the cards to the view to display
      //displayCardsView.displayCardBatch(cardSet);
      tab.displayCards(cardBatch);
    }
  }

  public List<Card> getCardSet(){
    return this.cardSet;
  }

}
