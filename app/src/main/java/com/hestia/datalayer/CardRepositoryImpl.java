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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.*;
import org.json.JSONObject;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Created by Richard on 3/31/2018.
 */

public class CardRepositoryImpl implements CardRepository {
  static final String TAG = "CARD REPOSITOR";
  static final String ENDPOINT = "https://omgvamp-hearthstone-v1.p.mashape.com/cards";

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
            Log.e("RESPONSE", response);
            Toast.makeText(viewContext, response.substring(0, 500), Toast.LENGTH_SHORT).show();
          }
        }, new Response.ErrorListener() {
          @Override
          public void onErrorResponse(VolleyError error) {
            Log.e("RESPONSE", "ERROR WITH REQUEST");
            Toast.makeText(viewContext, error.getMessage(), Toast.LENGTH_SHORT).show();
          }
        }) {

      // overrides the getheaders method to add custom headers
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


  public void initializeDatabase (String jsonReturn) {
    JSONParser parser = new JSONParser();

    try {
      Object object = parser.parse(jsonReturn);
      org.json.JSONObject jsonObject = (JSONObject) object;



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





}

