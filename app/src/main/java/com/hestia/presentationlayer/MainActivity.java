package com.hestia.presentationlayer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.hestia.R;
import com.hestia.presentationlayer.displaydecks.DisplayDecksFragment;

/**
 * Created by Richard on 3/6/2018.
 */

public class MainActivity extends AppCompatActivity{
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_main);

    // checks if app is not being restored from another state (savedInstanceState is null)
    if (savedInstanceState == null ) {
      // create the Display Decks fragment to be inserted into the activity
      DisplayDecksFragment displayDecksFrag = new DisplayDecksFragment();
    }

  }


}
