package com.hestia.presentationlayer.displaydecks;

import android.icu.text.UnicodeSetSpanner;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.hestia.R;
import com.hestia.datalayer.DeckRepository;
import com.hestia.datalayer.DeckRepositoryImpl;
import com.hestia.domainlayer.Deck;
import com.hestia.presentationlayer.DeckDecorator;
import com.hestia.presentationlayer.customadapter.DisplayDeckAdapter;
import com.hestia.presentationlayer.singledeck.SingleDeckView;

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
public class DisplayDecksView extends Fragment implements DisplayDecksContract.View {

  private DisplayDecksContract.Presenter displayDeckPresenter;
  private DisplayDeckAdapter mAdapter;

  private RecyclerView mRecyclerView = null;
  private RecyclerView.LayoutManager mLayoutManager = null;

  private View rootView = null;

  //TODO implement a pull downwards refresh listener


  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    // create an instance of the presenter if it doesn't already exist
    if (displayDeckPresenter == null) {
      // new deck repository to be injected into the presenter
      DeckRepository deckRepo = new DeckRepositoryImpl(this.getContext());
      displayDeckPresenter = new DisplayDecksPresenter(this, deckRepo);
    }
  }

  @Override
  public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
    // recall: popping off backstack returns a fragment without a view
    if (rootView == null) {
      // inflate the layout for this view nad enables the menu
      rootView = inflater.inflate(R.layout.display_decks, container, false);
      setHasOptionsMenu(true);

      // initialize the instance of the recycler view
      mRecyclerView = rootView.findViewById(R.id.recycler_list);
      mRecyclerView.setHasFixedSize(true);

      // initializes and sets the layout manager plus adapter
      if (mLayoutManager == null) {
        mLayoutManager = new GridLayoutManager(rootView.getContext(), 2);
      }
      if (mAdapter == null) {
        mAdapter = new DisplayDeckAdapter(rootView.getContext());
      }

      mRecyclerView.setLayoutManager(mLayoutManager);

      // creates a new onclick and passes it to the adapter
      AdapterListener listener = new AdapterListener();
      mAdapter.addOnClickListener(listener);

      mRecyclerView.setAdapter(mAdapter);
    }
    return rootView;
  }


  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    // resets the name of the title everytime this activity is resumed
    getActivity().setTitle(R.string.app_name);
    mRecyclerView.addOnScrollListener(new RecyclerScrollListener());
  }


  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    // clear the previous menu
    // inflate the menu layout into the Menu object
    inflater.inflate(R.menu.display_decks_menu, menu);
    super.onCreateOptionsMenu(menu, inflater);
  }



  public void addDecks (List <DeckDecorator> decks) {
    // add the list of decks to the adapter
    mAdapter.addDecks(decks);
  }

  /**
   * Custom adapter listener class to circumvent RecyclerView's lack of an OnItemClickListener
   * Allows us to keep view code in this view class
   */
  class AdapterListener implements View.OnClickListener {
    @Override
    public void onClick(View view) {
      // get the tag from the view and the deck from the adapter
      int position = (int) view.getTag();
      DeckDecorator passedDeck = mAdapter.getDeck(position);

      Toast.makeText(getContext(), position + " POSITION", Toast.LENGTH_SHORT).show();

      SingleDeckView singleDeckFragment = new SingleDeckView();

      // create a bundle to pass the new fragment the deck object
      Bundle args = new Bundle();
      args.putParcelable("deck", passedDeck);
      singleDeckFragment.setArguments(args);

      FragmentTransaction transaction = getFragmentManager().beginTransaction();
      // replace contents of fragment container with this fragment
      transaction.replace(R.id.content_frame, singleDeckFragment)
        // adds the replaced fragment to back stack to allow user to navigate back to it
        .addToBackStack(null)
        // commit the transaction
        .commit();
    }
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
        displayDeckPresenter.getDeckBatch();
      }
    }
  }

}

