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

    // temp testing
    Toast.makeText(this.getContext(), "HI", Toast.LENGTH_SHORT);

    return rootView;
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

  public void displayCardBatch(List <CardDecorator> cardBatch) {
    TextView test = getActivity().findViewById(R.id.tester_id);
    test.setText(cardBatch.size() + " WOIW BIG MANS ");
  }
}
