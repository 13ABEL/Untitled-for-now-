package com.hestia.datalayer;

import com.hestia.domainlayer.Card;
import com.hestia.presentationlayer.displaycards.DisplayCardsContract;

/**
 * Created by Richard on 3/31/2018.
 */

public interface CardRepository {

  Card getCardByID(String ID);

  void getCardBatch(int batchSize);
  void initializeDatabase();



}
