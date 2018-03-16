package com.hestia.presentationlayer.singledeck;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hestia.R;
import com.hestia.presentationlayer.displaydecks.DisplayDecksPresenter;

/**
 * Created by Richard on 3/14/2018.
 */

public class SingleDeckView extends Fragment implements SingleDeckContract.View {

  private SingleDeckContract.Presenter singleDeckPresenter;

  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    // create instance of the presenter
    singleDeckPresenter = new SingleDeckPresenter(this);

    //getActivity().setTitle(savedInstanceState.;
    // get the actionbar and change it for this view
    Toolbar toolbar = getActivity().findViewById(R.id.my_toolbar);




  }

  public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    // inflate the layout for this view
    View rootView = inflater.inflate(R.layout.single_deck, container, false);

    return rootView;
  }


  public void onBackPressed() {

  }

}
