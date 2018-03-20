package com.hestia.presentationlayer.singledeck;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hestia.R;
import com.hestia.domainlayer.Deck;
import com.hestia.presentationlayer.displaydecks.DisplayDecksContract;

/**
 * Created by Richard on 3/17/2018.
 */

public class InfoFragment extends Fragment {
  // stores the instance of the deck
  Deck displayDeck;

  // new instance constructor
  public static InfoFragment newInstance(int page, String title, Deck cDeck){
    InfoFragment infoFragment = new InfoFragment();
    infoFragment.addDeck(cDeck);
    return infoFragment;
  }

  public  void addDeck(Deck deck) {
    this.displayDeck = deck;
  }

  public void onCreate (Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    // get the deck object from repository
    displayDeck = null;
  }

  public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.single_deck_infotab, container, false);

    TextView deckSummary = view.findViewById(R.id.infotab_deck_summary);
    deckSummary.setText("HELLO");

    return view;
  }



}
