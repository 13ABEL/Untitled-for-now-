package com.hestia.presentationlayer.createdeck;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CreateDeckView extends Fragment implements CreateDeckContract.View{
  CreateDeckContract.Presenter cPresenter;

  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    // initializes the presenter for this view
    cPresenter = new CreateDeckPresenter(this);

  }

  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    return null;
  }




}
