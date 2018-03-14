package com.hestia.presentationlayer;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import android.support.v7.widget.Toolbar;

import com.hestia.R;
import com.hestia.presentationlayer.displaydecks.DisplayDecksView;

/**
 * Created by Richard on 3/6/2018.
 */

public class MainActivity extends AppCompatActivity{
  private ActionBarDrawerToggle mDrawerToggle;

  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_main);

//    // initializes the floating action button and its onlick
//    FloatingActionButton fab = findViewById(R.id.fab);
//    fab.setOnClickListener(new View.OnClickListener() {
//      @Override
//      public void onClick(View view) {
//        Snackbar.make(view, "haha yeet", Snackbar.LENGTH_LONG)
//            .setAction("Action", null).show();
//      }
//    });

    // sets the toolbar as the actionbar for this activity
    Toolbar mToolBar = findViewById(R.id.my_toolbar);
    setSupportActionBar(mToolBar);

    // gets the layout for the navigation drawer and creates its toggle
    DrawerLayout mDrawerLayout = findViewById(R.id.drawer_layout);
    mDrawerToggle = new ActionBarDrawerToggle(
        this,
        mDrawerLayout,
        mToolBar,
        R.string.drawer_open,
        R.string.drawer_close);
    mDrawerLayout.addDrawerListener(mDrawerToggle);

    // checks if app is not being restored from another state (savedInstanceState is null)
    if (savedInstanceState == null ) {
      // create the Display Decks fragment to be inserted into the activity
      DisplayDecksView displayDecksFrag = new DisplayDecksView();
      // Add the fragment to the container (content frame) in this activity
      getSupportFragmentManager().beginTransaction().add(R.id.content_frame, displayDecksFrag).commit();
    }
  }

  protected void onPostCreate(Bundle savedInstanceState) {
    super.onPostCreate(savedInstanceState);

    // displays the "opening" icon in the drawer
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setHomeButtonEnabled(true);

    // syncs the current state of the toggle icon
    mDrawerToggle.syncState();
  }



}
