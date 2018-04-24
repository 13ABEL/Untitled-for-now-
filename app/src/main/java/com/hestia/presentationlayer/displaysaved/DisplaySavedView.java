package com.hestia.presentationlayer.displaysaved;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.hestia.R;
import com.hestia.datalayer.DeckRepository;
import com.hestia.datalayer.DeckRepositoryImpl;
import com.hestia.presentationlayer.DeckDecorator;
import com.hestia.presentationlayer.customadapter.DisplayDeckAdapter;
import com.hestia.presentationlayer.singledeck.SingleDeckView;

import java.util.List;

public class DisplaySavedView extends Fragment implements DisplaySavedContract.View {
  DisplaySavedContract.Presenter displaySavedPresenter;

  RecyclerView mRecyclerView;
  RecyclerView.LayoutManager mLayoutManager;
  DisplayDeckAdapter mAdapter;

  /**
   * Initialize components that need to be retained through the lifecycle
   * @param savedInstanceState
   */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    // checks to see if the user is signed in
    if (FirebaseAuth.getInstance().getCurrentUser() == null) {
      // TODO create a new screen to tell the user if they're not logged in
      Toast.makeText(this.getContext(), "Sorry, but you must sign in to save decks", Toast.LENGTH_SHORT).show();
    }
    else if (displaySavedPresenter == null) {
      // create instance of repository to inject into the presenter
      DeckRepository deckRepo = new DeckRepositoryImpl(this.getContext());
      displaySavedPresenter = new DisplaySavedPresenter(this);
      displaySavedPresenter.injectDependencies(deckRepo);
    }
  }


  public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    // inflate the layout for this view
    View rootView = inflater.inflate(R.layout.display_decks, container, false);

    // enables the menu for this activity and clears the previous menu items
    setHasOptionsMenu(true);
    getActivity().invalidateOptionsMenu();

    // initialize the instance of the recycler view
    mRecyclerView = rootView.findViewById(R.id.recycler_list);
    mRecyclerView.setHasFixedSize(true);

    // initializes and sets the layout manager plus adapter
    mLayoutManager = new GridLayoutManager(rootView.getContext(), 2);
    mRecyclerView.setLayoutManager(mLayoutManager);

    if (mAdapter == null) {
      mAdapter = new DisplayDeckAdapter(rootView.getContext());
      mAdapter.addOnClickListener(new AdapterListener());
    }

    // creates a new onclick and passes it to the adapter
    //DisplayDecksView.AdapterListener listener = new DisplayDecksView.AdapterListener();
    //mAdapter.addOnClickListener(listener);
    mRecyclerView.setAdapter(mAdapter);
    getActivity().setTitle("Saved Deck");

    return rootView;
  }


  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    // resets the name of the title every time this activity is resumed
    //getActivity().setTitle("Saved Decks");
    //mRecyclerView.addOnScrollListener(new RecyclerScrollListener());
  }

  /**
   * inflates the menu on the toolbar for this activity
   * @param menu
   * @param inflater
   */
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    // inflate the menu layout into the Menu object
    inflater.inflate(R.menu.display_saved_menu, menu);
    super.onCreateOptionsMenu(menu, inflater);
  }


  public boolean onOptionsItemSelected(MenuItem item) {
    // routes the selection to the presenter to handle logic
    switch (item.getItemId()) {
      case R.id.item_create_deck:
        // creates a new fragment for deck creation
        createNewDeck();
      default:
        return super.onOptionsItemSelected(item);
    }
  }


  public void createNewDeck() {
    // gets the transaction to manage the dialog lifecycle
    FragmentTransaction transaction = getFragmentManager().beginTransaction();

    // creates and shows the new dialog fragment
    DialogFragment newDeckFragment = new NewDeckDialog();
    newDeckFragment.show(transaction, "Create New Deck");
  }


  @Override
  public void addDecks(List<DeckDecorator> decks) {
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
        displaySavedPresenter.getDeckBatch();
      }
    }
  }

}
