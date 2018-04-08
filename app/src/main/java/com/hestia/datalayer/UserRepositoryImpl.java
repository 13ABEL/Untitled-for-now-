package com.hestia.datalayer;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hestia.domainlayer.Deck;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class UserRepositoryImpl implements UserRepository {
  private FirebaseUser currentUser;
  private String TAG = "USER_REPOSITORY";
  private String COLLECTION_NAME = "users";

  private CollectionReference userCollection;
  private CollectionReference userSavedDeckCollection;
  private CollectionReference userFavedDeckCollection;

  public UserRepositoryImpl() {
    // initializes the firestore collection reference
    userCollection = FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
    userSavedDeckCollection = FirebaseFirestore.getInstance().collection("savedDecks");
    userFavedDeckCollection = FirebaseFirestore.getInstance().collection("favedDecks");

    // initialize the user
    currentUser = FirebaseAuth.getInstance().getCurrentUser();
  }

  @Override
  public void initializeAccount() {
    currentUser = FirebaseAuth.getInstance().getCurrentUser();
  }

  @Override
  public void getCurrentUser(String userId) {
    final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

    Log.d(TAG, firebaseUser.getUid());
    // checks if the account already exists in the database
    DocumentReference userRef = userCollection.document(firebaseUser.getUid());

    // retrieves the userData
    Task <DocumentSnapshot> future = userRef.get();
    future.addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
      @Override
      public void onComplete(@NonNull Task<DocumentSnapshot> task) {
        if (task.isSuccessful()) {
          DocumentSnapshot userAccount = task.getResult();
          // create the new user account if it doesn't exist already
          if (!userAccount.exists()) {
            createUserAccount(firebaseUser);
          }
          else {
            Log.d(TAG,(String) userAccount.getData().get("email"));
          }
        }
      }
    });
  }



  private void createUserAccount(FirebaseUser firebaseUser) {
    // create the map that will represent the user
    Map <String, Object> userMap = new HashMap<>();
    userMap.put("username", "TEST");
    userMap.put("email", firebaseUser.getEmail());
    userMap.put("created", new Date());

    // add the user to the collection using the auth uid as the document id
    userCollection.document(firebaseUser.getUid()).set(userMap)
        .addOnCompleteListener(new OnCompleteListener<Void>() {
          @Override
          public void onComplete(@NonNull Task<Void> task) {
            // calls method to create the local user account
          }
        });

    // add a document for saved, faved deckss for the user
    userSavedDeckCollection.document(firebaseUser.getUid()).set(new HashMap<String, Object>());
    userFavedDeckCollection.document(firebaseUser.getUid()).set(new HashMap<String, Object>());
  }

  @Override
  public void favDeck (Deck deck) {
    // create the map that will represent the deck to be saved
    Map <String, Object> userMap = new HashMap<>();
    userFavedDeckCollection.document(currentUser.getUid()).set(deck);

  }

  @Override
  public void saveDeck(Deck deck) {
    // generates the map rep of the deck to be used in firestore
    Map <String, Object> deckRep = deck.generateMap();
    userSavedDeckCollection.document(currentUser.getUid()).set(deckRep);
  }



}
