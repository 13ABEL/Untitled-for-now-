package com.hestia.presentationlayer.editdeck;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.hestia.R;
import com.hestia.domainlayer.Deck;
import com.hestia.presentationlayer.customadapter.DisplayCardAdapter;
import com.hestia.presentationlayer.displaycards.DisplayCardsVM;

public class EditDeckView extends Fragment implements EditDeckContract.View{
  private final String TAG = "CREATE_DECK";

  private View rootView;
  EditDeckContract.Presenter cPresenter;
  private DisplayCardsVM viewModel;

  DisplayCardAdapter cardAdapter;

  int deckClassID;
  private Boolean isStandard = true;
  private Boolean isSaved = false;

  FloatingActionButton showDeckFAB;

  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    // get the argument bundle and retrieve the contained data
    Bundle dialogNewDeck = this.getArguments();
    // our presenter works with the deck interface as opposed to the deck itself
    Deck newDeck = (Deck) dialogNewDeck.get("deck");
    deckClassID = newDeck.getDeckClass();

    // initializes the presenter for this view
    cPresenter = new EditDeckPresenter(this, newDeck);

    // initializes the reference the current floating action button
    showDeckFAB = getActivity().findViewById(R.id.fab);

    // gets the ViewModel instance associated with the fragment
    viewModel = ViewModelProviders.of(this).get(DisplayCardsVM.class);
    viewModel.initializeRepo(this.getContext());
  }


  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    // inflate the same layout used for the display cards view
    rootView = inflater.inflate(R.layout.display_cards, container, false);
    // initializes the recycler view
    RecyclerView mRecyclerView  = rootView.findViewById(R.id.display_cards_recyclerview);
    mRecyclerView.setHasFixedSize(true);

    // initializes the layout manager and attaches it to the recyclerview
    RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(rootView.getContext(), 2);
    mRecyclerView.setLayoutManager(mLayoutManager);

    // initializes the adapter for the recyclerviews
    cardAdapter = new DisplayCardAdapter();
    cardAdapter.addOnClickListener(new cardOnClick());
    mRecyclerView.setAdapter(cardAdapter);

    // display the default screen for the cards
    resetList();
    return rootView;
  }

  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    // hide the FAB and bottom navigation
    if (showDeckFAB != null) {
      showDeckFAB.setVisibility(View.INVISIBLE);
    }
  }

  public void resetList() {
    viewModel.getCreateCards(deckClassID, isStandard).observe(this, liveCardList ->
        cardAdapter.submitList(liveCardList));
  }

  public void showCardAdded(boolean cardAdded) {
    // shows action based on whether the card has been added or not
    if (cardAdded) {
      // TODO add animations
    }
    else {
    }
    Toast.makeText(rootView.getContext(), " added : " + cardAdded, Toast.LENGTH_SHORT).show();
  }

  /**
   * Sets up the FAB if the deck has not been saved
   */
  public void onDestroyView () {
    super.onDestroyView();
    // automatically saves the deck - tells presenter to create and add it to firebase
    cPresenter.saveChanges();
  }

  // Onclick class for the adapter
  class cardOnClick implements View.OnClickListener {
    @Override
    public void onClick(View v) {
      // gets the card from the adapter and sends it to the presenter to handle it
      cPresenter.addToNewDeck(cardAdapter.getCard(v));
    }
  }
}
