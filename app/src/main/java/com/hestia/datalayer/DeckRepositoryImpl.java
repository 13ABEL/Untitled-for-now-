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
import com.hestia.presentationlayer.DeckDecorator;
import com.hestia.presentationlayer.displaydecks.DisplayDecksContract;
import com.hestia.presentationlayer.displaydecks.DisplayDecksPresenter;
import com.hestia.presentationlayer.singledeck.SingleDeckContract;

import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

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

  private DeckDecorator fetchedDeck;


  // testing purposes
  

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
          ArrayList <DeckDecorator> newDecks = new ArrayList<>();

          for (DocumentSnapshot document: task.getResult()) {
            //test += document.getId() + " = " + document.getData();
            Log.d("-----------------pixel", document.getId() + " = " + document.getData());
            returnString = document.getId();// + " = " + document.getData();

            // formats the data into a deck object and adds it into the array of decks
            // DeckDecorator newDeck = parseFirebaseReturn(returnString);
            DeckDecorator newDeck = parseFullDeck(document.getData());
            Log.d("NEW DECK", "newDeck = " + newDeck.getDeckName() );
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


  private DeckDecorator parseFirebaseReturn (String returnString) {
    String parsedString = returnString;

    DeckDecorator newDeck = new DeckDecorator();
    return newDeck;
  }

  /**
   * Used for getting a single deck from the firebase firestore
   * @param presenter the presenter tied to the view displaying the deck
   * @param deckID the id of the deck we want to get
   */
  public void getFullDeck(SingleDeckContract.Presenter presenter, String deckID) {
    this.singleDeckPresenter = presenter;

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
            Log.d("REPOSITORY", "DocumentSnapshot data: " + document.getData());
            // parses the firebase return and sends it to the presenter to handle
            fetchedDeck = parseFullDeck(document.getData());
            if (fetchedDeck == null) {
              Log.d("TEST OBJECT", "the deck is null");
            }
            Log.d("TEST OBJECT", fetchedDeck.toString());
            singleDeckPresenter.receiveFullDeck(fetchedDeck);

          }
        }
      }
    });

  }


  /**
   * This parses a firestore return map to create a new deck object
   * @param deckMap map representation of data from firestore
   * @return the Deck object representation of data from firestore
   */
  private DeckDecorator parseFullDeck (Map <String, Object> deckMap) {
    // parse the data from the firestore map (implementation dependent)
    //TODO currently using author id -> need to change to username
    String  authorID = deckMap.get("author_id").toString();
    String deckName = (String) deckMap.get("deck_name");
    String summary = (String) deckMap.get("summary");
    String deckList = (String) deckMap.get("list");
    Date createdDate = (Date) deckMap.get("created");

    Log.d("REPOSITORY MAP TESTING",
        " deck_name = " + deckName
        +" authorID = " + authorID
        + " summary = " + summary
        + " deckList = " + deckList
        + " createdDate = " + createdDate.toString()
    );

    return new DeckDecorator(deckName, authorID, deckList, summary, createdDate);
  }


  // TODO: 3/17/2018 create an object for getting a preview of the deck



  public void saveDeck(DeckDecorator deck) {

  }






}
