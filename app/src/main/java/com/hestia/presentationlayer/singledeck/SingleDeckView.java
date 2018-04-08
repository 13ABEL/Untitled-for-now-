package com.hestia.presentationlayer.singledeck;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.hestia.R;
import com.hestia.presentationlayer.DeckDecorator;
import com.hestia.presentationlayer.customadapter.SingleDeckTabAdapter;

/**
 * Created by Richard on 3/14/2018.
 */

public class SingleDeckView extends Fragment implements SingleDeckContract.View {
  private SingleDeckContract.Presenter singleDeckPresenter;
  private String [] tabNames = {"INFO", "LIST", "COMMENTS"};
  private SingleDeckTabAdapter mSingleDeckTabAdapter;
  private ViewPager mPager;
  private DeckDecorator currentDeck;

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

    // gets the deckDecorator object from the bundle
    currentDeck = this.getArguments().getParcelable("deck");
    Toast.makeText(getContext(), "Single Deck Screen " + currentDeck.getDeckName(), Toast.LENGTH_SHORT).show();

    // create instance of the presenter
    singleDeckPresenter = new SingleDeckPresenter(this, currentDeck);

    // gets supportFragmentManager bc ViewPage uses support library fragments
    FragmentManager fragManager = getActivity().getSupportFragmentManager();
    mSingleDeckTabAdapter = new SingleDeckTabAdapter(fragManager, singleDeckPresenter);

    // sets the collection adapter with tabs as the pager adapter
    mPager = rootView.findViewById(R.id.single_deck_pager);
    mPager.setAdapter(mSingleDeckTabAdapter);
    TabLayout tabLayout = rootView.findViewById(R.id.sliding_tabs);
    tabLayout.setupWithViewPager(mPager);

    return rootView;
  }

  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    // gets the current Deck ID and uses it to get the deck from the repository
    //String deckName = this.getArguments().getString("deck_id");

    // sets the title of the navbar as the current deck name
    getActivity().setTitle(currentDeck.getDeckName());

    // attach listeners to buttons (TODO swap out buttons for actionbar icons)
    final Button addFav = getActivity().findViewById(R.id.single_deck_add_fav);
    addFav.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            singleDeckPresenter.saveDeck();
          }
        }
    );

  }

  public void displayInfo(String title) {
    // TODO more lines when more info is used for this fragment
  }

  //TODO implement a pull downwards refresh listener

}
