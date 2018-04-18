package com.hestia.presentationlayer;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.data.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.hestia.R;
import com.hestia.datalayer.UserRepository;
import com.hestia.datalayer.UserRepositoryImpl;
import com.hestia.presentationlayer.displaycards.DisplayCardsView;
import com.hestia.presentationlayer.displaydecks.DisplayDecksContract;
import com.hestia.presentationlayer.displaydecks.DisplayDecksView;
import com.hestia.presentationlayer.displaysaved.DisplaySavedView;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Richard on 3/6/2018.
 */

public class MainActivity extends AppCompatActivity {
  // used to identify the tab fragments
  private static final String BACK_STACK_ROOT = "root_fragment";

  private ActionBarDrawerToggle mDrawerToggle;
  private static final int RC_SIGN_IN = 123;

  // for each view
  private Fragment displayDecksView;
  private Fragment displaySavedView;
  private Fragment displayCardsView;


  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_main);

    // starts the signin if the current user is null
    if (FirebaseAuth.getInstance().getCurrentUser() == null) {
      startSignIn();
    }

    // sets the toolbar as the actionbar for this activity
    Toolbar mToolBar = findViewById(R.id.my_toolbar);
    setSupportActionBar(mToolBar);

    // gets the layout for the navigation drawer and creates its toggle
    //DrawerLayout mDrawerLayout = findViewById(R.id.drawer_layout);
//    mDrawerToggle = new ActionBarDrawerToggle(
//        this,
//        mDrawerLayout,
//        mToolBar,
//        R.string.drawer_open,
//        R.string.drawer_close);
//    mDrawerLayout.addDrawerListener(mDrawerToggle);

    // checks if app is not being restored from another state (savedInstanceState is null)
    if (savedInstanceState == null ) {
      // create the Display Decks fragment to be inserted into the activity
      DisplayDecksView displayDecksFrag = new DisplayDecksView();
      // Add the fragment to the container (content frame) in this activity
      getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, displayDecksFrag).commit();
    }
    // sets up the app navigation
    setupNavigation();
  }

  protected void onPostCreate(Bundle savedInstanceState) {
    super.onPostCreate(savedInstanceState);
    // displays the "opening" icon in the drawer
    //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    //getSupportActionBar().setHomeButtonEnabled(true);

    // syncs the current state of the toggle icon
    //mDrawerToggle.syncState();

    // initialize each view
    if (displayDecksView == null) {
      displayDecksView = new DisplayDecksView();
    }
    if (displaySavedView == null) {
      displaySavedView = new DisplaySavedView();
    }
    if (displayCardsView == null) {
      displayCardsView = new DisplayCardsView();
    }
  }



  /**
   *  Sets up the navigation in general (onclicks)
   */
  public void setupNavigation () {
    BottomNavigationView navigationView = findViewById(R.id.bottom_navigation);
    // sets up the listener for the page items
    navigationView.setOnNavigationItemSelectedListener(
        item -> {
          // Fragment that will be displayed
          Fragment displayFragment;
          // switch to select the appropriate fragment to start based on item id
          int displayID = item.getItemId();

          if (displayID == R.id.bottom_navigation_1) {
            //Toast.makeText(getBaseContext(), "1 item ID ", Toast.LENGTH_SHORT).show();
            // creates a new display decks view if one doesn't already exist
            displayFragment = displayDecksView;
          }
          else if (displayID == R.id.bottom_navigation_2) {
            //Toast.makeText(getBaseContext(), "2 item ID ", Toast.LENGTH_SHORT).show();
            //displayFragment = new DisplayCardsView();
            displayFragment = displaySavedView;
          }
          else if (displayID == R.id.bottom_navigation_3) {
            //Toast.makeText(getBaseContext(), "3 item ID ", Toast.LENGTH_SHORT).show();
            // creates a new display cards view if one doesn't already exist
            displayFragment = displayCardsView;
          }
          else {
            //Toast.makeText(getBaseContext(), "None selected", Toast.LENGTH_SHORT).show();
            displayFragment = displayDecksView;
          }

          FragmentManager fragManager = getSupportFragmentManager();
          // pop everything off the current back stack (up to and including current tab fragment)
          fragManager.popBackStack(BACK_STACK_ROOT, FragmentManager.POP_BACK_STACK_INCLUSIVE);

          // replace the current screen with the appropriate fragment
          // do not add the current fragment to the stack - we dont want to pop it off
          // because it's the root of each tab
          fragManager.beginTransaction().replace(R.id.content_frame, displayFragment)
              .commit();

          return true;
    });
  }

  /**
   * Currently a quick way to sign in - will implement custom fragment without the UI dependency later
   * when there is some free time
   */
  public void startSignIn () {
    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

    UserRepository userRepository = new UserRepositoryImpl();
    userRepository.getCurrentUser("test");

    // check if the current user is not signed in
    if (firebaseUser == null) {
      // list of authentication providers
      List <AuthUI.IdpConfig> authProviders = Arrays.asList(
          new AuthUI.IdpConfig.EmailBuilder().build(),
          new AuthUI.IdpConfig.GoogleBuilder().build());
      //new AuthUI.IdpConfig.FacebookBuilder().build()
      startActivityForResult(
          AuthUI.getInstance()
              .createSignInIntentBuilder()
              .setAvailableProviders(authProviders)
              .build(), 123
      );
    }
    else {
      Toast.makeText(this, "Welcome back " + firebaseUser.getDisplayName(), Toast.LENGTH_SHORT)
      .show();
    }

  }


}
