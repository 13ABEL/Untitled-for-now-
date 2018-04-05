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
  private final String FRAGMENT_TAG = "DISPLAY_CARDS_FRAGMENT";
  DisplayCardsContract.Presenter displayCardsPresenter;

  private RecyclerView mRecyclerView;
  private RecyclerView.LayoutManager mLayoutManager;

  private DisplayCardAdapter mLayoutAdapter;

  /**
   * This method is called only when the fragment is created
   * @param savedInstanceState
   */
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    //creates a new instance of the presenter
    this.displayCardsPresenter = new DisplayCardsPresenter(this);
  }

  public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
    View rootView = inflater.inflate(R.layout.display_cards, container, false);
    // sets tag to allow manager to recognise this fragment
    rootView.setTag(FRAGMENT_TAG);

    if (savedInstanceState != null) {
      Toast.makeText(this.getContext(), "RESTORED", Toast.LENGTH_SHORT).show();
    }
    // initializes the instance of the recycler view using the newly inflated view
    mRecyclerView = rootView.findViewById(R.id.display_cards_recyclerview);
    mRecyclerView.setHasFixedSize(true);

    // initialize and sets the layout manager for the recycler view
    mLayoutManager = new GridLayoutManager(rootView.getContext(), 2);
    mRecyclerView.setLayoutManager(mLayoutManager);

    // initializes and sets the adapter for the recycler view (not the root)
    mLayoutAdapter = new DisplayCardAdapter(displayCardsPresenter, rootView.getContext());
    mRecyclerView.setAdapter(mLayoutAdapter);
    mRecyclerView.addOnScrollListener(new RecyclerScrollListener());

    return rootView;
  }

  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    // fetches the first batch of cards to be displayed
    displayCardsPresenter.fetchCardBatch();
  }



  public void displayCardBatch(List <Card> cardBatch) {
    TextView test = getActivity().findViewById(R.id.tester_id);
    String testText = cardBatch.size() + " BIG MANS ";
    test.setText(testText);
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


  /**
   *  Used to load more cards once the bottom of the recyclerview is "reached"
   */
  class RecyclerScrollListener extends RecyclerView.OnScrollListener {
    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
      // TODO: implement boolean currentlyLoading to display loading animation later
      // if the bottom of the recyclerview is reached (cannot scroll downwards)
      if (!recyclerView.canScrollVertically(1)) {
        Toast.makeText(recyclerView.getContext(), " HAHA ", Toast.LENGTH_SHORT);
        displayCardsPresenter.fetchCardBatch();
      }

    }
  }
}
