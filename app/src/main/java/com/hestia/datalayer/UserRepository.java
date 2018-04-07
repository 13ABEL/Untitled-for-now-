package com.hestia.datalayer;

import com.firebase.ui.auth.data.model.User;
import com.google.firebase.auth.FirebaseUser;

public interface UserRepository {
  //initializes account for user in firebase
  void initializeAccount();

  // gets the user based on the current Firebase User
  void getCurrentUser(String userID);


}
