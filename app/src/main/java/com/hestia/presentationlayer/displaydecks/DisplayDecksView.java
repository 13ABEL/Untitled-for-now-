package com.hestia.presentationlayer.displaydecks;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hestia.R;

/**
 * Created by Richard on 3/6/2018.
 */

public class DisplayDecksFragment extends Fragment {
  private DisplayDecksContract.presenter displayDeckPresenter;

  @Override
  public View onCreateView (LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState){
    // inflate the layout for the view
    return inflater.inflate(R.layout.display_decks, container, false);


    // create an instance of the presenter
    displayDeckPresenter = new DisplayDecksContract.presenter();
  }


}

