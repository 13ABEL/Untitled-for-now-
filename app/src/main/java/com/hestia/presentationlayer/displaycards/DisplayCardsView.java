package com.hestia.presentationlayer.displaycards;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.content.Context;
import android.os.Bundle;
import android.os.TestLooperManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.hestia.R;
import com.hestia.datalayer.Card.CardDecorator;
import com.hestia.datalayer.CardRepository;
import com.hestia.datalayer.CardRepositoryImpl;
import com.hestia.domainlayer.Card;
import com.hestia.presentationlayer.customadapter.DisplayCardAdapter;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Richard on 3/30/2018.
 */

public class DisplayCardsView extends Fragment implements DisplayCardsContract.View{
  private final String FRAGMENT_TAG = "DISPLAY_CARDS_FRAGMENT";

  private View rootView;
  private RecyclerView mRecyclerView;
  private RecyclerView.LayoutManager mLayoutManager;

  private DisplayCardAdapter mLayoutAdapter;

  DisplayCardsVM viewModel;
  String created = "NEW";


  /**
   * This method is called only when the fragment is created
   * @param savedInstanceState
   */
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    if (created != "NEW") {
      Toast.makeText(this.getContext(), "RESTORED " + created, Toast.LENGTH_SHORT).show();
    }
    else {
      Toast.makeText(this.getContext(), "NEW", Toast.LENGTH_SHORT).show();
    }

    // gets the ViewModel instance associated with the fragment
    viewModel = ViewModelProviders.of(this).get(DisplayCardsVM.class);
  }

  public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
    if (rootView == null) {
      rootView = inflater.inflate(R.layout.display_cards, container, false);
      // sets tag to allow manager to recognise this fragment
      rootView.setTag(FRAGMENT_TAG);

      created = "NOT NEW";
      // initializes the instance of the recycler view using the newly inflated view
      mRecyclerView = rootView.findViewById(R.id.display_cards_recyclerview);
      mRecyclerView.setHasFixedSize(true);

      // initialize and sets the layout manager for the recycler view
      if (mLayoutManager == null) {
        mLayoutManager = new GridLayoutManager(rootView.getContext(), 2);
        mRecyclerView.setLayoutManager(mLayoutManager);
      }

      // initializes and sets the adapter for the recycler view (not the root)
      if (mLayoutAdapter == null) {
        mLayoutAdapter = new DisplayCardAdapter();
        mRecyclerView.setAdapter(mLayoutAdapter);

        viewModel.getCards(this.getContext(), 4).observe(this, liveCardList ->
            mLayoutAdapter.submitList(liveCardList));
      }
    }
    

    return rootView;
  }

  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    // Just doing some extra testing
    CardRepositoryImpl cardRepo = new CardRepositoryImpl(this.getContext());
  }


  /**
   * implemented this method to allow the repository to access this context
   * Room needs the context to generate an instance
   * @return the context for this view
   */
  public Context getViewContext () {
    return this.getContext();
  }

  // TODO initialize a new actionbar with custom menus (filtering, sorting)
  // TODO allow custom OnClicks to be applied to this view (it'll be reused in a variety of scenarios later on



}
