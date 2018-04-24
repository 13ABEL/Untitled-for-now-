package com.hestia.presentationlayer.singledeck;

import android.arch.lifecycle.ViewModel;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hestia.R;
import com.hestia.domainlayer.Card;
import com.hestia.domainlayer.Deck;
import com.hestia.presentationlayer.DeckDecorator;
import com.hestia.presentationlayer.customadapter.DisplayCardAdapter;
import com.hestia.presentationlayer.customadapter.SingleDeckCardAdapter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by Richard on 3/17/2018.
 */

public class DeckFragment extends TabFragment {
  private DisplayCardAdapter deckListAdapter = null;
  private View rootView;
  public int test = 0;

  // new instance constructor
  public static DeckFragment newInstance(int page, String title, SingleDeckContract.Presenter presenter){
    // creates a new instance of the fragment and passes it the parent presenter
    DeckFragment newFragment = new DeckFragment();
    newFragment.setPresenter(presenter);

    Log.e("TEST TAG", newFragment.test + "");
    return newFragment;
  }

  public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    // inflates the layout into a view
    this.rootView = inflater.inflate(R.layout.single_deck_listtab, container, false);
    return rootView;
  }

  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    // adds the current fragment to the parent presenter once everything is loaded
    parentPresenter.addDeckTabFragment(this);
  }


  public void updateUI(List <Card> deckList) {
    //TextView textThing = getActivity().findViewById(R.id.listtab_test);

    String test = "";
    for (int i = 0; i < deckList.size(); i++) {
      test += deckList.get(i).getID() + " \n";
    }

    // creates the new adapter instance and sets it as the adapter for the listview
    //deckListAdapter = new SingleDeckCardAdapter(getContext(), deckList);
    deckListAdapter = new DisplayCardAdapter();
    RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(rootView.getContext(), 2);

    RecyclerView decklistView = rootView.findViewById(R.id.deck_card_list);
    decklistView.setLayoutManager(mLayoutManager);
    decklistView.setAdapter(deckListAdapter);

    parentPresenter.getAddableCards().observe(
        this, liveSearchList -> deckListAdapter.submitList(liveSearchList)
    );
  }





}


