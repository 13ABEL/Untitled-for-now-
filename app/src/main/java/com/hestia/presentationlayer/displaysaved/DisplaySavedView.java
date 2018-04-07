package com.hestia.presentationlayer.displaysaved;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hestia.R;
import com.hestia.presentationlayer.customadapter.DisplayDeckAdapter;

public class DisplaySavedView extends Fragment implements Contract.View {
  Contract.Presenter displayMyDecksPresenter;
  RecyclerView mRecyclerView;
  RecyclerView.LayoutManager mLayoutManager;
  DisplayDeckAdapter mAdapter;

  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    // create the instance of the presenter
    displayMyDecksPresenter = new DisplaySavedPresenter(this);
  }


  public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    // inflate the layout for this view
    View rootView = inflater.inflate(R.layout.display_decks, container, false);

    // initialize the instance of the recycler view
    mRecyclerView = rootView.findViewById(R.id.recycler_list);
    mRecyclerView.setHasFixedSize(true);

    // initializes and sets the layout manager plus adapter
    mLayoutManager = new GridLayoutManager(rootView.getContext(), 2);
    mRecyclerView.setLayoutManager(mLayoutManager);
    mAdapter = new DisplayDeckAdapter(rootView.getContext());

    // creates a new onclick and passes it to the adapter
    //DisplayDecksView.AdapterListener listener = new DisplayDecksView.AdapterListener();
    //mAdapter.addOnClickListener(listener);

    mRecyclerView.setAdapter(mAdapter);

    return rootView;  }


}
