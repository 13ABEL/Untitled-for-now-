package com.hestia.presentationlayer.displaycards;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hestia.R;

/**
 * Created by Richard on 3/30/2018.
 */

public class DisplayCardsView extends Fragment implements DisplayCardsContract.View{
  DisplayCardsContract.Presenter displayCardsPresenter;

  public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
    View rootView = inflater.inflate(R.layout.display_cards, container, false);

    // check if presenter doesn't already exist for this view
    if (displayCardsPresenter == null) {
      // creates a new instance of the presenter
      displayCardsPresenter = new DisplayCardsPresenter(this);
    }

    return rootView;
  }





}
