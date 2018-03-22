package com.hestia.presentationlayer.singledeck;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.hestia.R;
import com.hestia.domainlayer.Deck;
import com.hestia.presentationlayer.displaydecks.DisplayDecksPresenter;

/**
 * Created by Richard on 3/14/2018.
 */

public class SingleDeckView extends Fragment implements SingleDeckContract.View {
  private SingleDeckContract.Presenter singleDeckPresenter;
  private String [] tabNames = {"INFO", "LIST", "COMMENTS"};
  private CollectionAdapter mCollectionAdapter;
  private ViewPager mPager;

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

    // gets the current Deck ID and uses it to get the deck from the repository
    String currentDeckID = this.getArguments().getString("deck_id");
    Toast.makeText(getContext(), "Single Deck Screen " + currentDeckID, Toast.LENGTH_SHORT).show();
    // create instance of the presenter
    singleDeckPresenter = new SingleDeckPresenter(this, currentDeckID);

    // gets supportFragmentManager bc ViewPage uses support library fragments
    FragmentManager fragManager = getActivity().getSupportFragmentManager();
    mCollectionAdapter = new CollectionAdapter(fragManager, singleDeckPresenter);

    // sets the collection adapter with tabs as the pager adapter
    mPager = rootView.findViewById(R.id.single_deck_pager);
    mPager.setAdapter(mCollectionAdapter);
    TabLayout tabLayout = rootView.findViewById(R.id.sliding_tabs);
    tabLayout.setupWithViewPager(mPager);

    return rootView;
  }


}
