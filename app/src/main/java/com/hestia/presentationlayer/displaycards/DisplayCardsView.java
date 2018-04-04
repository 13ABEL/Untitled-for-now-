package com.hestia.presentationlayer.displaycards;

import android.content.Context;
import android.os.Bundle;
import android.os.TestLooperManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.hestia.R;
import com.hestia.datalayer.Card.CardDecorator;
import com.hestia.domainlayer.Card;
import com.hestia.presentationlayer.customadapter.DisplayCardAdapter;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Richard on 3/30/2018.
 */

public class DisplayCardsView extends Fragment implements DisplayCardsContract.View{
  DisplayCardsContract.Presenter displayCardsPresenter;

  private RecyclerView mRecyclerView;
  private RecyclerView.LayoutManager mLayoutManager;

  private DisplayCardAdapter mLayoutAdapter;


  public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
    View rootView = inflater.inflate(R.layout.display_cards, container, false);

    // creates a new instance of the presenter
    displayCardsPresenter = new DisplayCardsPresenter(this);

    // initializes the instance of the recycler view using the newly inflated view
    mRecyclerView = rootView.findViewById(R.id.display_cards_recyclerview);
    mRecyclerView.setHasFixedSize(true);

    // initialize and sets the layout manager for the recycler view
    mLayoutManager = new GridLayoutManager(rootView.getContext(), 2);
    mRecyclerView.setLayoutManager(mLayoutManager);

    // initializes and sets the adapter for the recycler view (not the root)
    mLayoutAdapter = new DisplayCardAdapter(displayCardsPresenter, rootView.getContext());
    mRecyclerView.setAdapter(mLayoutAdapter);

    // fetches the first batch of cards to be displayed
    displayCardsPresenter.fetchCardBatch();

    return rootView;
  }



  public void displayCardBatch(List <Card> cardBatch) {
    TextView test = getActivity().findViewById(R.id.tester_id);
    String testText = cardBatch.size() + " BIG MANS ";
    test.setText(testText);


    //TODO need to move the adapter list to the presenter - the view should not be involved
  }


  /**
   * implemented this method to allow the repository to access this context
   * Room needs the context to generate an instance
   * @return
   */
  public Context getViewContext () {
    return this.getContext();
  }


  /**
   * Tells adapter that there are new items to display
   */
  public void notifyAdapter(int position) {
    mLayoutAdapter.notifyItemInserted(position);
  }
}
