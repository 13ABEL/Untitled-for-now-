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


  public void initializeDatabase () {
    // gets the endpoint key from the secrets xml file
    final String API_KEY = viewContext.getString(R.string.hearthstone_api_key);
    // create the request queue
    RequestQueue rQueue = Volley.newRequestQueue(viewContext);

    // Request the response from the URL
    StringRequest stringRequest = new StringRequest(Request.Method.GET, ENDPOINT,
        new Response.Listener<String>() {
          @Override
          public void onResponse(String response) {
            // calls the method to parse the JSON results and insert them into the database
            insertJSONReturn(response);
          }
        }, new Response.ErrorListener() {
          @Override
          public void onErrorResponse(VolleyError error) {
            Log.e("RESPONSE", "ERROR WITH REQUEST");
            Toast.makeText(viewContext, error.getMessage(), Toast.LENGTH_SHORT).show();
          }
        }) {

      // overrides the getheaders method to add custom headers (for auth)
      @Override
      public Map<String, String> getHeaders() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("X-Mashape-Key", API_KEY);
        return params;
      }
    };
    // Add the request to the RequestQueue.
    rQueue.add(stringRequest);
  }


  public ArrayList<DeckDecorator> parseReturn(String requestReturn) {
    return null;
  }


  /**
   * Used to parse the JSON response from the hearthstone API (non official)
   * @param jsonReturn the response from the GET query to the api endpoint
   */
  private void insertJSONReturn (String jsonReturn) {
    // create the new instance of the parser to iteract with the data
    JSONParser parser = new JSONParser();

    try {
      JSONObject jsonObject = (JSONObject) parser.parse(jsonReturn);
      JSONArray expansionSet;
      JSONObject card;

      Object testNull;

      // create the iterator for each expansion set using its keyset
      Iterator expansionIterator = jsonObject.keySet().iterator();
      while (expansionIterator.hasNext()) {
        String expansionKey = (String) expansionIterator.next();
        expansionSet = (JSONArray) jsonObject.get(expansionKey);

        ArrayList <CardDecorator> newCards = new ArrayList<>();

        // create the iterator for this expansion directly because it's a JSON Array
        Iterator expansionCardIterator = expansionSet.iterator();
        // iterate through the cards in each expansion
        while (expansionCardIterator.hasNext()) {
          // get the next key for the loop
          card = (JSONObject) expansionCardIterator.next();

          int cardCost = -1;
          testNull = card.get("cost");
          if (testNull != null) {
            Long longCost = (Long) testNull;
            cardCost = longCost.intValue();
          }

          Log.i(TAG, (String) card.get("name"));
          // inserts the card into the database
          newCards.add( new CardDecorator(
              (String) card.get("dbfId"),
              (String) card.get("name"),
              (String) card.get("faction"),
              (String) card.get("type"),
              (String) card.get("rarity"),
              (String) card.get("text"),
              "",
              cardCost
              )
          );
        }

        InsertTask insertCardsTask = new InsertTask();
        insertCardsTask.execute(newCards);

        Toast.makeText(viewContext, "Finished import cards from the " + expansionKey + " set", Toast.LENGTH_SHORT).show();
      }
    } catch (ParseException e) {
      Log.e (TAG, "There was an issue parsing the JSON return: " + e.getMessage());
    }
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


  class InsertTask extends AsyncTask <List <CardDecorator>, Integer, Integer>{
    @Override
    protected Integer doInBackground(List <CardDecorator> ... cards) {
      List<CardDecorator> cardList = cards[0];
      // insert all the cards async
      cardDatabase.cardModel().insertAll(cardList);

      return cards.length;
    }
  }



}

