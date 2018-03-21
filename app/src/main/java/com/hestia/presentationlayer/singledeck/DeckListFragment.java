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
  public static DeckListFragment newInstance(int page, String title, SingleDeckContract.Presenter presenter){
    // creates a new instance of the fragment and sets passes it the parent presenter
    DeckListFragment newFragment = new DeckListFragment();
    newFragment.setPresenter(presenter);
    Log.e("TEST TAG", newFragment.test + "");

    return newFragment;
  }

  //
  public void onCreate (Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.single_deck_infotab, container, false);
    
    // adds the new fragment to the parent presenter to be managed
    if (parentPresenter != null) {
      parentPresenter.addTabFragment(this);
    }

    return view;
  }

  public void updateUI(Deck deck) {
    TextView textThing = getActivity().findViewById(R.id.infotab_deck_summary);
    textThing.setText(R.string.test_paragraph_long);
  }

}


