package com.hestia.presentationlayer.displaycards;

import android.content.Context;
import android.os.Bundle;
import android.os.TestLooperManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.hestia.R;
import com.hestia.datalayer.Card.CardDecorator;
import com.hestia.domainlayer.Card;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Richard on 3/30/2018.
 */

public class DisplayCardsView extends Fragment implements DisplayCardsContract.View{
  DisplayCardsContract.Presenter displayCardsPresenter;

  public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
    View rootView = inflater.inflate(R.layout.display_cards, container, false);

    // check if presenter doesn't already exist for this view
    if (displayCardsPresenter == null) {
      // creates a new instance of the presenter
      displayCardsPresenter = new DisplayCardsPresenter(this);
    }
    // fetches the first batch of cards to be displayed
    displayCardsPresenter.fetchCardBatch();

    return rootView;
  }



  public void displayCardBatch(List <CardDecorator> cardBatch) {
    Toast.makeText(this.getContext(), "MHM", Toast.LENGTH_SHORT);
    TextView test = getActivity().findViewById(R.id.tester_id);
    String testText = cardBatch.size() + " BIG MANS ";
    test.setText(testText);
  }

  /**
   * implemented this method to allow the repository to access this context
   * Room needs the context to generate an instance
   *
   * @return
   */
  public Context getViewContext () {
    return this.getContext();
  }

}
