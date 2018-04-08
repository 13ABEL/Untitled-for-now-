package com.hestia.domainlayer;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by Richard on 3/7/2018.
 */

public interface Deck {
  String getDeckName();
  String getAuthor();
  Map <String, Object> generateMap();
}
