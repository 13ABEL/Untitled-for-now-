package com.hestia.datalayer.Card;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
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
import com.hestia.R;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Richard on 3/31/2018.
 */

@Database(entities = {CardDecorator.class}, version = 2, exportSchema = false)
public abstract class CardDatabase extends RoomDatabase {
  private static final String TAG = "CARD_DATABASE";
  private static final String ENDPOINT = "https://omgvamp-hearthstone-v1.p.mashape.com/cards?collectible=1";

  private static CardDatabase INSTANCE = null;

  public abstract CardDao cardModel();

  public static CardDatabase getDatabase (Context mContext) {
    // following singleton pattern - generates instance if it doesn't already exist
    if (INSTANCE == null) {
      // code to check if the database file exists
      File currentDB = mContext.getDatabasePath("CardDatabase");
      Boolean initializeDB = !currentDB.exists();

      // create a new instance of the database
      INSTANCE = Room.databaseBuilder(mContext, CardDatabase.class, "CardDatabase")
          .build();

      // initializes the database based on earlier check, after room instance has been created
      if (initializeDB) {
        INSTANCE.initializeDatabase(mContext);
      }
      else {
        Log.i(TAG, "Database exists - no need to reinitialize");
      }
    }
    return INSTANCE;
  }

  public static void destroyInstance() {
    INSTANCE = null;
  }

  // since we're only calling this once, no need for repository
  private void initializeDatabase(final Context mContext) {
    // gets the endpoint key from the secrets xml file
    final String API_KEY = mContext.getString(R.string.hearthstone_api_key);
    // create the request queue
    RequestQueue rQueue = Volley.newRequestQueue(mContext);

    // Request the response from the URL
    StringRequest stringRequest = new StringRequest(Request.Method.GET, ENDPOINT,
        new Response.Listener<String>() {
          @Override
          public void onResponse(String response) {
            // calls the method to parse the JSON results and insert them into the database
            insertJSONReturn(response, mContext);
          }
        }, new Response.ErrorListener() {
          @Override
          public void onErrorResponse(VolleyError error) {
          Log.e("RESPONSE", "ERROR WITH REQUEST");
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


  /**
   * Used to parse the JSON response from the hearthstone API (non official)
   * @param jsonReturn the response from the GET query to the api endpoint
   */
  private static void insertJSONReturn (String jsonReturn, Context mContext) {
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

        ArrayList<CardDecorator> newCards = new ArrayList<>();

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
        Toast.makeText(mContext, "Finished import cards from the " + expansionKey + " set", Toast.LENGTH_SHORT).show();
      }
    } catch (ParseException e) {
      Log.e (TAG, "There was an issue parsing the JSON return: " + e.getMessage());
    }
  }

  static class InsertTask extends AsyncTask<List<CardDecorator>, Integer, Integer> {
    @Override
    protected Integer doInBackground(List <CardDecorator> ... cards) {
      List<CardDecorator> cardList = cards[0];
      // insert all the cards async
      INSTANCE.cardModel().insertAll(cardList);

      return cards.length;
    }
  }



}
