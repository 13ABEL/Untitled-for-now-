package com.hestia.presentationlayer.displaydecks;

import android.icu.text.UnicodeSetSpanner;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.hestia.R;
import com.hestia.domainlayer.Deck;
import com.hestia.presentationlayer.customadapter.DisplayDeckAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Richard on 3/6/2018.
 *
 * I'm gonna forget so here it is:
 * > This Fragment contains -- ONLY -- the display logic for the fragment
 * > The instance of the Presenter for this fragment has functions that
 * are called to perform the logic for user interaction
 *
 *
 */
public class DisplayDecksView extends ListFragment implements DisplayDecksContract.View, AdapterView.OnItemClickListener{

  private DisplayDecksContract.Presenter displayDeckPresenter;
  private DisplayDeckAdapter mAdapter;

  private RecyclerView mRecyclerView;
  private RecyclerView.LayoutManager mLayoutManager;

  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    // create an instance of the presenter
    displayDeckPresenter = new DisplayDecksPresenter(this);

    // initialize the instance of the recucler view
    mRecyclerView = (RecyclerView) getActivity().findViewById(R.id.recycler_list);
    mRecyclerView.setHasFixedSize(true);

    // specify a layout manager and adapter
    mLayoutManager = new LinearLayoutManager(getActivity());
    mRecyclerView.setLayoutManager(mLayoutManager);

    // initializes the layout and adapter
    ArrayList <Deck> newList = new ArrayList<>();
    mAdapter = new DisplayDeckAdapter(getActivity(), newList);
    mRecyclerView.setAdapter(mAdapter);

    // sets the listeners for the newly created list items
    getListView().setOnItemClickListener(this);
  }

  @Override
  public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
    // inflate the layout for this view
    return inflater.inflate(R.layout.display_decks, container, false);
  }


  public void displayMultiUsers(List <Deck> decks) {
    // add the list of decks to the adapter
    //mAdapter.addAll(decks);
  }

  @Override
  public void onItemClick (AdapterView adapterView, View view, int position, long id) {
    Toast.makeText(getContext(), position +"", Toast.LENGTH_SHORT).show();
  }

}

