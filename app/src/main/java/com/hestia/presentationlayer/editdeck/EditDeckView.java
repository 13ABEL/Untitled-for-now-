package com.hestia.presentationlayer.editdeck;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.hestia.R;
import com.hestia.datalayer.CardRepository;
import com.hestia.datalayer.CardRepositoryImpl;
import com.hestia.domainlayer.Card;
import com.hestia.domainlayer.Deck;
import com.hestia.presentationlayer.customadapter.DisplayCardAdapter;
import com.hestia.presentationlayer.displaycards.DisplayCardsVM;

import java.util.List;

public class EditDeckView extends Fragment implements EditDeckContract.View{
  private final String TAG = "CREATE_DECK";

  private View rootView;

  private CardRepository cardRepo;
  private EditDeckContract.Presenter cPresenter;
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
    Toast.makeText(getContext(), newDeck.getDeckName(), Toast.LENGTH_SHORT).show();
    deckClassID = newDeck.getDeckClass();

    // initializes the presenter for this view
    cPresenter = new EditDeckPresenter(this, newDeck);

    // initializes the reference the current floating action button
    showDeckFAB = getActivity().findViewById(R.id.fab);
  }


  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    // inflate the same layout used for the display cards view
    rootView = inflater.inflate(R.layout.display_cards_tab, container, false);
    // initializes the recycler view
    RecyclerView mRecyclerView  = rootView.findViewById(R.id.display_cards_tab_recycler);
    mRecyclerView.setHasFixedSize(true);

    // initializes the layout manager and attaches it to the recyclerview
    RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(rootView.getContext(), 2);
    mRecyclerView.setLayoutManager(mLayoutManager);

    // initializes the adapter for the recyclerviews
    cardAdapter = new DisplayCardAdapter();
    cardAdapter.addOnClickListener(new cardOnClick());
    mRecyclerView.setAdapter(cardAdapter);

    // inject the repo dependency into the presenter and asks it to retreive the cards
    cPresenter.injectDependencies(new CardRepositoryImpl(this.getContext()));
    cPresenter.retrieveCards(deckClassID);

    return rootView;
  }


  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    // hide the FAB and bottom navigation
    if (showDeckFAB != null) {
      showDeckFAB.setVisibility(View.INVISIBLE);
    }
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

  public void displayCards(List<Card> cardList) {
    cardAdapter.setCardList(cardList);
    cardAdapter.notifyDataSetChanged();
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
    public void onClick(View view) {
      // gets the card from the adapter and sends it to the presenter to handle it
      cPresenter.addToNewDeck(cardAdapter.getCard(view));
    }
  }
}
