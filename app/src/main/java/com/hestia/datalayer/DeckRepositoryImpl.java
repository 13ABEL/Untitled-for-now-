package com.hestia.datalayer;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.hestia.domainlayer.Deck;
import com.hestia.domainlayer.DeckImpl;
import com.hestia.presentationlayer.displaydecks.DisplayDecksContract;
import com.hestia.presentationlayer.displaydecks.DisplayDecksPresenter;
import com.hestia.presentationlayer.singledeck.SingleDeckContract;

import org.w3c.dom.Document;

import java.util.ArrayList;

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
  private DisplayDecksContract.Presenter displayDecksPresenter;
  private SingleDeckContract.Presenter singleDeckPresenter;

  private FirebaseFirestore db;
  private String returnString = "";


  public DeckRepositoryImpl () {
    // initializes the instance of the Cloud Firestore db
    this.db = FirebaseFirestore.getInstance();
  }

  public DeckRepositoryImpl (DisplayDecksPresenter displayDeckPresenter) {
    // initializes the instance of the Cloud Firestore db
    this.db = FirebaseFirestore.getInstance();
    //this.displayDeckPresenter = displayDeckPresenter;
  }

  public void getDeckBatch(DisplayDecksContract.Presenter presenter, int numDecks) {
    this.displayDecksPresenter = presenter;

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
            returnString = document.getId();// + " = " + document.getData();

            // formats the data into a deck object and adds it into the array of decks
            Deck newDeck = parseFirebaseReturn(returnString);
            newDecks.add(newDeck);
          }
          displayDecksPresenter.receiveDeckBatch(newDecks);
        }
        else {
          // displays error message if there is a problem retrieving the deck
          Log.w("----------------hi", "Error getting documents.", task.getException());
        }
      }
    });
  }


  public void getFullDeck(SingleDeckContract.Presenter presenter, String deckID) {
    this.singleDeckPresenter = presenter;
    Deck fetchedDeck  = new DeckImpl();

    // get the document reference for the deck with id deckID
    DocumentReference docRef = db.collection("decks").document(deckID);
    docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
      @Override
      public void onComplete(@NonNull Task<DocumentSnapshot> task) {
        // always check if the task is successful
        if (task.isSuccessful()) {
          DocumentSnapshot document = task.getResult();
          if (document != null && document.exists()) {
            // create a new deck by parsing the firebase data
            Log.d("TESTERTESTER", "DocumentSnapshot data: " + document.getData());
            //Deck newDeck = parseFirebaseReturn(document.getData());
            // sends the new deck back to the presenter to handle
            //singleDeckPresenter.receiveFullDeck(newDeck);
          }
        }
      }
    });

    singleDeckPresenter.receiveFullDeck(fetchedDeck);
  }


  private Deck parseFirebaseReturn (String returnString) {
    String parsedString = returnString;

    Deck newDeck = new DeckImpl(parsedString);
    return newDeck;
  }


  // TODO: 3/17/2018 create an object for getting a preview of the deck





  @Override
  public void saveDeck(Deck deck) {

  }



}
