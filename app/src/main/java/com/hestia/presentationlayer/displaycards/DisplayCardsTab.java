package com.hestia.presentationlayer.displaycards;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hestia.R;
import com.hestia.presentationlayer.singledeck.TabFragment;

public class DisplayCardsTab extends TabFragment {
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    // inflate the view using the inflater
    return inflater.inflate(R.layout.display_cards, container, false);
  }



}



