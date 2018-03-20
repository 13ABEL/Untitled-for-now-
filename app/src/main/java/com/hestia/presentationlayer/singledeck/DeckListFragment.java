package com.hestia.presentationlayer.singledeck;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hestia.R;
import com.hestia.domainlayer.Deck;

/**
 * Created by Richard on 3/17/2018.
 */

public class DeckListFragment extends Fragment{
  // stores the instance of the deck
  Deck displayDeck;


  // new instance constructor
  public static InfoFragment newInstance(int page, String title, Deck deck){
    InfoFragment infoFragment = new InfoFragment();
    infoFragment.addDeck(deck);

    return infoFragment;
  }

  public  void addDeck(Deck deck) {
    this.displayDeck = deck;
  }
  //
  public void onCreate (Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.single_deck_infotab, container, false);

    TextView deckSummary = view.findViewById(R.id.infotab_deck_summary);
    deckSummary.setText("HELLO");

    return view;
  }

}


