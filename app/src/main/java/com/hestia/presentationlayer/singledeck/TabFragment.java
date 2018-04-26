package com.hestia.presentationlayer.singledeck;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Toast;

import com.hestia.domainlayer.Deck;
import com.hestia.presentationlayer.DeckDecorator;

/**
 * Created by Richard on 3/19/2018.
 */

public abstract class TabFragment extends Fragment {
  int position;

  // public abstract void updateUI(DeckDecorator deck);


  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
  }

  public void setPosition(int tab) {
    this.position = tab;
  }
}
