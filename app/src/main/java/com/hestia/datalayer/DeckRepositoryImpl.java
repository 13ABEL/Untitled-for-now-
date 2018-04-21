package com.hestia.datalayer;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.hestia.presentationlayer.DeckDecorator;
import com.hestia.presentationlayer.displaydecks.DisplayDecksContract;
import com.hestia.presentationlayer.displaydecks.DisplayDecksPresenter;
import com.hestia.presentationlayer.displaysaved.DisplaySavedContract;
import com.hestia.presentationlayer.singledeck.SingleDeckContract;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Set;

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
  private static String TAG = "DECK_REPOSITORY";
  private DisplayDecksContract.Presenter displayDecksPresenter;
  private SingleDeckContract.Presenter singleDeckPresenter;

  private FirebaseFirestore db;
  private String returnString = "";

  private DeckDecorator fetchedDeck;

  private CollectionReference deckCollection;
  private CollectionReference savedCollection;

  private Query queryDeckBatch = null;
  private Query querySavedDeckBatch = null;


  final int BATCH_SIZE = 15;


  public DeckRepositoryImpl () {
    // gets the user instance and initializes the instance of the Cloud Firestore db
    FirebaseUser currentUSer = FirebaseAuth.getInstance().getCurrentUser();
    this.db = FirebaseFirestore.getInstance();

    if (currentUSer != null) {
      // initializes the reference to the saved collection for the user
      String currentUserUid = currentUSer.getUid();
      savedCollection = db.collection( "savedDecks").document(currentUserUid)
          .collection("savedDecks");

      // initializes the savedDeck query specific to the user
      querySavedDeckBatch = savedCollection.limit(BATCH_SIZE);
    }

    // does not require instance of user
    deckCollection = db.collection( "decks");
  }

  public DeckRepositoryImpl (DisplayDecksPresenter displayDeckPresenter) {
    // initializes the instance of the Cloud Firestore db
    this.db = FirebaseFirestore.getInstance();
    //this.displayDeckPresenter = displayDeckPresenter;
  }

  public void getDeckBatch(DisplayDecksContract.Presenter presenter, final int numDecks) {
    this.displayDecksPresenter = presenter;

    // initialize the query for the deck batch
    if (queryDeckBatch == null) {
      queryDeckBatch = deckCollection.limit(BATCH_SIZE);
    }

    Task <QuerySnapshot> task = queryDeckBatch.get();
    task.addOnSuccessListener(new OnSuccessListener<QuerySnapshot> () {
      @Override
      public void onSuccess(QuerySnapshot documentSnapshots) {
        int currentBatchSize = documentSnapshots.size();
        // only gets more decks if there are any left to get
        if (currentBatchSize > 0) {
          // gets the last document loaded (used to construct next query)
          DocumentSnapshot lastVisible = documentSnapshots.getDocuments()
              .get(currentBatchSize - 1);

          // constructs a new query starting at the last document (for pagination)
          queryDeckBatch = deckCollection.orderBy("createdDate").startAfter(lastVisible).limit(BATCH_SIZE);

          //creates a new array for the decks
          ArrayList<DeckDecorator> newDecks = new ArrayList<>();
          String deckID;

          for (DocumentSnapshot document : documentSnapshots) {
            deckID = document.getId();
            //test += document.getId() + " = " + document.getData();
            Log.d("-----------------pixel", document.getId() + " = " + document.getData());
            returnString = document.getId();// + " = " + document.getData();

            // add the id to the card map before sending it to be parsed
            Map<String, Object> cardMap = document.getData();
            cardMap.put("id", deckID);

            DeckDecorator newDeck = parseFullDeck(cardMap);

            Log.d("NEW DECK", "newDeck = " + newDeck.getDeckName());
            newDecks.add(newDeck);
          }
          displayDecksPresenter.receiveDeckBatch(newDecks);
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
            // send the list of new decks to the presenter
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
    //String authorID = deckMap.get("author_ID").toString();
    String deckID = (String) deckMap.get("id");
    // TODO change author_id back to username once you have time to create a firestore function
    String userID = (String) deckMap.get("author_id");
    String deckName = (String) deckMap.get("deckName");
    String summary = (String) deckMap.get("summary");
    String deckList = (String) deckMap.get("deckString");
    Date createdDate = (Date) deckMap.get("createdDate");

    Log.d("REPOSITORY MAP TESTING",
        " deck_ID = " + deckID
        + " deck_name = " + deckName
        +" userID = " + userID
        + " summary = " + summary
        + " deckList = " + deckList
        + " createdDate = " + createdDate.toString()
    );

    return new DeckDecorator(deckID, deckName, userID, deckList, summary, createdDate);
  }


  /**
   * Always stores a deck in the current user's account
   * @param deck
   */
  public void saveDeck(DeckDecorator deck) {


  }



  public void getSavedBatch(final DisplaySavedContract.Presenter displayCardsPresenter) {
    // get the Document query object
    Task <QuerySnapshot> task = querySavedDeckBatch.get();
    task.addOnSuccessListener(new OnSuccessListener<QuerySnapshot> () {
      @Override
      public void onSuccess(QuerySnapshot documentSnapshots) {
        int currentBatchSize = documentSnapshots.size();

        // only gets more decks if there are any left to get
        if (currentBatchSize > 0) {
          // gets the last document loaded (used to construct next query)
          DocumentSnapshot lastVisible = documentSnapshots.getDocuments()
              .get(currentBatchSize - 1);

          //creates a new array for the decks
          ArrayList<DeckDecorator> newDecks = new ArrayList<>();

          // constructs a new query starting at the last document (for pagination)
          querySavedDeckBatch = savedCollection.orderBy("createdDate").startAfter(lastVisible).limit(BATCH_SIZE);

          for (DocumentSnapshot document : documentSnapshots) {
            //test += document.getId() + " = " + document.getData();
            Log.d(TAG, "getsavedbatch" + " = " + document.getData());

            // returnString = document.getId();// + " = " + document.getData();
            // formats the data into a deck object and adds it into the array of decks
            Map <String, Object> deckData = document.getData();
            deckData.put("id", document.getId());

            DeckDecorator newDeck = parseFullDeck(deckData);
//          Log.d("NEW DECK", "newDeck = " + newDeck.getDeckName());
            newDecks.add(newDeck);
          }
          displayCardsPresenter.receiveSavedBatch(newDecks);
        }
      }
    });
  }



}
