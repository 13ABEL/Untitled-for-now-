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

  // new instance constructor
  public static InfoFragment newInstance(int page, String title, SingleDeckContract.Presenter presenter) {
    // creates a new instance of the fragment and passes it to the parent presenter
    InfoFragment newFragment = new InfoFragment();
    newFragment.setPresenter(presenter);
    newFragment.setPosition(page);

    //Log.e("TEST TAG", newFragment.test + "");
    return newFragment;
  }

  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    // inflates the layout into a view
    View view = inflater.inflate(R.layout.single_deck_infotab, container, false);
    return view;
  }

  public void updateUI(DeckDecorator deck) {
    TextView textThing = getActivity().findViewById(R.id.infotab_deck_summary);
    textThing.setText(deck.getSummary());
  }


}
