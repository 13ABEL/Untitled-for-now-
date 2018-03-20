package com.hestia.presentationlayer.singledeck;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hestia.R;
import com.hestia.domainlayer.Deck;

/**
 * Created by Richard on 3/17/2018.
 */

public class DeckListFragment extends TabFragment {
  public int test = 0;

  // new instance constructor
  public static DeckListFragment newInstance(int page, String title){
    DeckListFragment newFragment = new DeckListFragment();
    return newFragment;
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

  public void updateUI(Deck deck) {

  }

}


