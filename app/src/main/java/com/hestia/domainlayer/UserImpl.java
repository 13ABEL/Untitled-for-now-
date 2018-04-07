package com.hestia.domainlayer;

import java.util.Date;
import java.util.List;

public class UserImpl implements User {
  String userID;
  String username;
  List<String> userDecks;
  List<String> savedDecks;


  public UserImpl (String id, String username, Date date, List createdDecks, List savedDecks) {
    this.userID = id;
    this.username = username;
    this.userDecks = createdDecks;
    this.savedDecks = savedDecks;
  }

}
