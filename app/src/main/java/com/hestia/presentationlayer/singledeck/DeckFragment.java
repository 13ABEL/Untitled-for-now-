package com.hestia.presentationlayer.singledeck;

import android.os.Bundle;
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

public class DeckFragment extends TabFragment {
  public int test = 0;

  // new instance constructor
  public static DeckFragment newInstance(int page, String title, SingleDeckContract.Presenter presenter){
    // creates a new instance of the fragment and passes it the parent presenter
    DeckFragment newFragment = new DeckFragment();
    newFragment.setPresenter(presenter);

    Log.e("TEST TAG", newFragment.test + "");
    return newFragment;
  }

  public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    // inflates the layout into a view
    View view = inflater.inflate(R.layout.single_deck_listtab, container, false);
    return view;
  }

  public void updateUI(Deck deck) {
    TextView textThing = getActivity().findViewById(R.id.listtab_test);
    textThing.setText(R.string.test_paragraph_long);
  }
}


