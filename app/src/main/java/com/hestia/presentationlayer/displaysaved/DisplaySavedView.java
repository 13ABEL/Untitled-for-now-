package com.hestia.presentationlayer.displaysaved;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DisplaySavedView extends Fragment implements Contract.View {
  Contract.Presenter displayMyDecksPresenter;

  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    // create the instance of the presenter
    displayMyDecksPresenter = new DisplaySavedPresenter(this);
  }


  public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

    return null;
  }


}
