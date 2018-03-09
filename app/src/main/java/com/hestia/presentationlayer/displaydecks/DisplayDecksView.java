package com.hestia.presentationlayer.displaydecks;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.hestia.R;
import com.hestia.domainlayer.Deck;

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
public class DisplayDecksView extends ListFragment implements DisplayDecksContract.View, AdapterView.OnItemClickListener{

  private DisplayDecksContract.Presenter displayDeckPresenter;
  private ArrayAdapter<Deck> adapter;

  @Override
  public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
    // inflate the layout for the view
    return inflater.inflate(R.layout.display_decks, container, false);
  }

  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    // create an instance of the presenter
    displayDeckPresenter = new DisplayDecksPresenter(this);
    // tells presenter to display all items
    displayDeckPresenter.getAllUsers();

  }

  public void displayMultiUsers(List <Deck> decks) {
    // initializes and sets the list adapter
    adapter = new ArrayAdapter<> (getActivity(), android.R.layout.simple_list_item_1, decks);
    setListAdapter(adapter);

    // sets the listeners for the list items
    getListView().setOnItemClickListener(this);

  }

  @Override
  public void onItemClick (AdapterView adapterView, View view, int position, long id) {

  }


}

