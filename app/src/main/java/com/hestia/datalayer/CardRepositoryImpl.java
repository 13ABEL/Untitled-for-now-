package com.hestia.datalayer;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.Task;
import com.hestia.R;
import com.hestia.datalayer.Card.CardDecorator;
import com.hestia.domainlayer.Card;
import com.hestia.domainlayer.CardImpl;
import com.hestia.presentationlayer.DeckDecorator;
import com.hestia.presentationlayer.displaycards.DisplayCardsContract;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static java.lang.Integer.parseInt;


/**
 * Created by Richard on 3/31/2018.
 */

public class CardRepositoryImpl implements CardRepository {
  static final String TAG = "CARD_REPOSITORY";
  static final String ENDPOINT = "https://omgvamp-hearthstone-v1.p.mashape.com/cards?collectible=1";

  private CardDatabase cardDatabase;

  private DisplayCardsContract.Presenter presenter;
  private Context viewContext;


  public CardRepositoryImpl(DisplayCardsContract.Presenter newPresenter) {

    this.presenter = newPresenter;

    // gets the context of the view and updates the database instance
    this.viewContext = presenter.getView().getViewContext();
    this.cardDatabase = CardDatabase.getDatabase(viewContext);
  }

  @Override
  public Card getCardByID(String ID) {
    return null;
  }


  public void getCardBatch (int numCards) {
    NewTask fetchBatchTask = new NewTask();
    fetchBatchTask.execute(presenter, numCards);
  }




  public ArrayList<DeckDecorator> parseReturn(String requestReturn) {
    return null;
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

