package com.hestia.datalayer;

import com.firebase.ui.auth.data.model.User;
import com.google.firebase.auth.FirebaseUser;
import com.hestia.domainlayer.Deck;

public interface UserRepository {
  //initializes account for user in firebase
  void initializeAccount();

  // gets the user based on the current Firebase User
  void getCurrentUser(String userID);

  void favDeck(Deck deck);

  void saveDeck(Deck deck);
  void saveNewDeck(Deck deck);
}
