package com.hestia.presentationlayer.singledeck;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hestia.R;
import com.hestia.domainlayer.Deck;
import com.hestia.presentationlayer.displaydecks.DisplayDecksPresenter;

/**
 * Created by Richard on 3/14/2018.
 */

public class SingleDeckView extends Fragment implements SingleDeckContract.View {
  private SingleDeckContract.Presenter singleDeckPresenter;

  private String [] tabNames = {"INFO", "LIST", "COMMENTS"};

  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);


    // gets the current Deck ID and uses it to get the deck from the repository
    String currentDeckID = this.getArguments().getString("deck_id");

    // create instance of the presenter
    singleDeckPresenter = new SingleDeckPresenter(this, currentDeckID);
  }

  /**
   * Inflates the xml file into a view
   *
   * @param inflater
   * @param container
   * @param savedInstanceState
   * @return
   */
  public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    // inflate the layout for this view
    View rootView = inflater.inflate(R.layout.single_deck, container, false);

    initializeTabs();

    return rootView;
  }

  public void initializeTabs () {
    TabLayout tabLayout = new TabLayout(getContext());
    tabLayout.addTab(tabLayout.newTab().setText(tabNames[0]));
    tabLayout.addTab(tabLayout.newTab().setText(tabNames[1]));
    tabLayout.addTab(tabLayout.newTab().setText(tabNames[2]));
  }


  public void onBackPressed() {
  }

}
