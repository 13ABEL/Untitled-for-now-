package com.hestia.presentationlayer.displaysaved;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hestia.R;

public class NewDeckDialog extends DialogFragment {
  View rootView;

  public void onCreate (Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    // inflates the layout
    rootView = inflater.inflate(R.layout.new_deck_dialog, container, false);


    return rootView;
  }
}
