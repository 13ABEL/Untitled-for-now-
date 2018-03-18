package com.hestia.presentationlayer.singledeck;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hestia.R;

/**
 * Created by Richard on 3/17/2018.
 */

public class DeckListFragment extends Fragment{

  // new instance constructor
  public static InfoFragment newInstance(int page, String title){
    InfoFragment infoFragment = new InfoFragment();
    return infoFragment;
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


