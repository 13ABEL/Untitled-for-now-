package com.hestia.datalayer;

import android.hardware.fingerprint.FingerprintManager;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Display;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.hestia.domainlayer.Deck;
import com.hestia.domainlayer.DeckImpl;
import com.hestia.presentationlayer.displaydecks.DisplayDecksPresenter;
import com.squareup.okhttp.HttpUrl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Richard on 3/10/2018.
 */

/**
 * Just some more notes because I'm pretty sure I'll forget
 * Think of a repository as a literal repository where you can access certain item(s) using
 * certain queries
 *
 */
public class DeckRepositoryImpl implements DeckRepository {
  private DisplayDecksPresenter presenter;
  private FirebaseFirestore db;
  private String returnString = "";

  public DeckRepositoryImpl (DisplayDecksPresenter displayDeckPresenter) {
    // initializes the instance of the Cloud Firestore db
    this.db = FirebaseFirestore.getInstance();
    this.presenter = displayDeckPresenter;
  }


  public void getDeckBatch(int numDecks) {
    Task <QuerySnapshot> task = db.collection( "decks").get();
    task.addOnCompleteListener(new OnCompleteListener<QuerySnapshot> (){
      @Override
      public void onComplete(@NonNull Task<QuerySnapshot> task) {
        if (task.isSuccessful()) {
          //creates a new array for the decks
          ArrayList <Deck> newDecks = new ArrayList<>();

          for (DocumentSnapshot document: task.getResult()) {
            //test += document.getId() + " = " + document.getData();
            Log.d("-----------------pixel", document.getId() + " = " + document.getData());
            returnString = document.getId() + " = " + document.getData();

            // formats the data into and object
            Deck newDeck = new DeckImpl(returnString);
            newDecks.add(newDeck);
          }

          presenter.addDecksListener(newDecks);
        }
        else {
          // displays error message if there is a problem retrieving the deck
          Log.w("----------------hi", "Error getting documents.", task.getException());
        }
      }
    });
  }



  @Override
  public void saveDeck(Deck deck) {

  }


}
