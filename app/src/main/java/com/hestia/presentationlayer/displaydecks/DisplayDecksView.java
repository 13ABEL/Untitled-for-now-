package com.hestia.presentationlayer.displaydecks;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hestia.R;

/**
 * Created by Richard on 3/6/2018.
 *
 * I'm gonna forget so here it is:
 * > This Fragment contains -- ONLY -- the display logic for the fragment
 * > The instance of the Presenter for this fragment has functions that
 * are called to perform the logic for user interaction
 *
 *
 */
public class DisplayDecksView extends Fragment implements DisplayDecksContract.View{
  private DisplayDecksContract.Presenter displayDeckPresenter;

  @Override
  public View onCreateView (LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState){

    // create an instance of the presenter
    displayDeckPresenter = new DisplayDecksPresenter(this);

    // inflate the layout for the view
    return inflater.inflate(R.layout.display_decks, container, false);
  }


}

