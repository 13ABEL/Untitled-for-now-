package com.hestia.datalayer;

import android.content.Context;
import android.os.AsyncTask;

import com.google.android.gms.tasks.Task;
import com.hestia.datalayer.Card.CardDecorator;
import com.hestia.domainlayer.Card;
import com.hestia.domainlayer.CardImpl;
import com.hestia.presentationlayer.displaycards.DisplayCardsContract;

import java.util.List;

/**
 * Created by Richard on 3/31/2018.
 */

public class CardRepositoryImpl implements CardRepository {
  private CardDatabase cardDatabase;

  @Override
  public Card getCardByID(String ID) {
    return null;
  }


  public void getCardBatch (DisplayCardsContract.Presenter presenter, int numCards) {
    // gets the context of the view and updates the database instance
    Context viewContext = presenter.getView().getViewContext();
    cardDatabase = CardDatabase.getDatabase(viewContext);

    NewTask fetchBatchTask = new NewTask();
    fetchBatchTask.execute(presenter, numCards);
  }



  class NewTask extends AsyncTask <Object, Integer, List<CardDecorator>>{
    private  DisplayCardsContract.Presenter presenter;

    @Override
    protected List<CardDecorator> doInBackground(Object... objects) {
      this.presenter = (DisplayCardsContract.Presenter) objects[0];
      int numCards = (int) objects[1];
      // gets the batch of cards
      return cardDatabase.cardModel().getBatch(numCards);
    }

    protected void onPostExecute(List<CardDecorator> returnedCards) {
      // routes the information back to the presenter
      presenter.receiveCardBatch(returnedCards);
    }
  }


}

