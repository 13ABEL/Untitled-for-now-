package com.hestia.presentationlayer.createdeck;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.hestia.R;
import com.hestia.datalayer.Card.CardDecorator;
import com.hestia.domainlayer.Card;
import com.hestia.presentationlayer.customadapter.DisplayCardAdapter;
import com.hestia.presentationlayer.displaycards.DisplayCardsVM;

public class CreateDeckView extends Fragment implements CreateDeckContract.View{
  private final String TAG = "CREATE_DECK";

  private View rootView;
  CreateDeckContract.Presenter cPresenter;
  private DisplayCardsVM viewModel;

  DisplayCardAdapter cardAdapter;

  String deckClass;
  private Boolean isStandard = true;
  private Boolean isSaved = false;

  FloatingActionButton showDeckFAB;

  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    // initializes the presenter for this view
    cPresenter = new CreateDeckPresenter(this);

    // get the argument bundle and retrieve the contained data
    Bundle dialogInput = this.getArguments();
    getActivity().setTitle(dialogInput.getCharSequence("deckName"));

    deckClass = (String) dialogInput.getCharSequence("deckClass");
    if (!dialogInput.getBoolean("formatStandard")) {
      isStandard = false;
    }
    Toast.makeText(this.getContext(), "Class : " + deckClass + ", Standard : " + isStandard,
        Toast.LENGTH_SHORT).show();

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

    // initializes the adapter for the recyclerview
    cardAdapter = new DisplayCardAdapter();
    cardAdapter.addOnClickListener(new cardOnClick());
    mRecyclerView.setAdapter(cardAdapter);

    // display the default screen for the cards
    resetList();
    return rootView;
  }

  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    // hide the FAB
    if (showDeckFAB != null) {
      showDeckFAB.setVisibility(View.INVISIBLE);
    }
  }


  public void resetList() {
    viewModel.getCreateCards(deckClass, isStandard).observe(this, liveCardList ->
        cardAdapter.submitList(liveCardList));
  }


  /**
   * Sets up the FAB if the deck has not been saved
   */
  public void onDestroyView () {
    super.onDestroyView();

    // a reference to the current fragment used by the onclick
    Fragment currentFragRef = this;

    // only shows the fab if the deck is not saved
    if (!isSaved) {
      // adds the listener to the fab
      showDeckFAB.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          Snackbar.make(view, "reopening deck", Snackbar.LENGTH_LONG)
              .setAction("Action", null).show();

          // create a reference to this instance of the create deck view
          FragmentTransaction transaction = getFragmentManager().beginTransaction();
          transaction.replace(R.id.content_frame, currentFragRef);
          transaction.commit();
        }
      });

      // show the FAB
      showDeckFAB.setVisibility(View.VISIBLE);
    }
  }

  // Onclick class for the adapter
  class cardOnClick implements View.OnClickListener {
    @Override
    public void onClick(View v) {
      CardDecorator currCard =  cardAdapter.getCard(v);

      // onclick calls the presenter to handle the logic
      cPresenter.addToNewDeck(currCard);
      Toast.makeText(rootView.getContext(), currCard.getName() + " added ", Toast.LENGTH_SHORT).show();

    }
  }






}
