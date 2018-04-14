package com.hestia.presentationlayer.displaysaved;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.hestia.R;
import com.hestia.presentationlayer.DeckDecorator;
import com.hestia.presentationlayer.customadapter.DisplayDeckAdapter;

import java.util.List;

public class DisplaySavedView extends Fragment implements DisplaySavedContract.View {
  DisplaySavedContract.Presenter displaySavedPresenter;
  RecyclerView mRecyclerView;
  RecyclerView.LayoutManager mLayoutManager;
  DisplayDeckAdapter mAdapter;

  /**
   * Overriede the DisplayDecksView to create a new saved presenter instead of a decks presenter
   * @param savedInstanceState
   */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    // checks to see if the user is signed in
    if (FirebaseAuth.getInstance().getCurrentUser() == null) {
      // TODO create a new screen to tell the user that they're not logged in
      Toast.makeText(this.getContext(), "Sorry, but you must sign in to save decks", Toast.LENGTH_SHORT);
    }
    else {
      // create the instance of the presenter
      displaySavedPresenter = new DisplaySavedPresenter(this);
    }
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

    return rootView;
  }


  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    // resets the name of the title every time this activity is resumed
    getActivity().setTitle(R.string.app_name);
    //mRecyclerView.addOnScrollListener(new RecyclerScrollListener());

    // enables the menu for this activity
    setHasOptionsMenu(true);
  }

  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    // inflate the menu layout into the Menu object
    inflater.inflate(R.menu.display_saved_menu, menu);
    super.onCreateOptionsMenu(menu, inflater);
  }


  @Override
  public void addDecks(List<DeckDecorator> decks) {
    mAdapter.addDecks(decks);
  }

  /**
   * used to load more decks once the "bottom of the page" is loaded
   */
  class RecyclerScrollListener extends RecyclerView.OnScrollListener {
    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
      // TODO: implement boolean currentlyLoading to display loading animation later
      // if the bottom of the recyclerview is reached (cannot scroll downwards)
      if (!mRecyclerView.canScrollVertically(1)) {
        Toast.makeText(mRecyclerView.getContext(), " HAHA ", Toast.LENGTH_SHORT);
        displaySavedPresenter.getDeckBatch();
      }
    }
  }
}
