package com.hestia.presentationlayer.singledeck;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.hestia.R;
import com.hestia.domainlayer.Deck;
import com.hestia.presentationlayer.DeckDecorator;
import com.hestia.presentationlayer.displaydecks.DisplayDecksContract;

/**
 * Created by Richard on 3/17/2018.
 */

public class InfoFragment extends TabFragment {
  View rootView;

  private static InfoFragment INSTANCE;

  public static InfoFragment newInstance() {
    // creates a new instance the fragment if it doesn't already exist
    if (INSTANCE == null) {
      InfoFragment newFragment = new InfoFragment();
    }
    //Log.e("TEST TAG", newFragment.test + "");
    return INSTANCE;
  }

  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    // inflates the layout into a view
    this.rootView = inflater.inflate(R.layout.single_deck_infotab, container, false);
    return rootView;
  }

  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
  }

  public void setTitle(String deckSummary) {
    TextView textThing = rootView.findViewById(R.id.infotab_deck_summary);
    textThing.setText(deckSummary);
  }
}
