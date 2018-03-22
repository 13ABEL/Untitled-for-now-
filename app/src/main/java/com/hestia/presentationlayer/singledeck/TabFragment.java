package com.hestia.presentationlayer.singledeck;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.hestia.domainlayer.Deck;

/**
 * Created by Richard on 3/19/2018.
 */

public class TabFragment extends Fragment {
  SingleDeckContract.Presenter parentPresenter;

  public int test = 20;


  public void updateUI (Deck deck) {

  }

  public void setPresenter(SingleDeckContract.Presenter presenter) {
    // uses the parent presenter to update information
    parentPresenter = presenter;
  }

  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    // adds the current fragment to the parent presenter once everything is loaded
    parentPresenter.addTabFragment(this);
  }
}
