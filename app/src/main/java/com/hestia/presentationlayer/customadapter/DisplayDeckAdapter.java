package com.hestia.presentationlayer.customadapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.hestia.R;
import com.hestia.presentationlayer.DeckDecorator;
import com.hestia.presentationlayer.singledeck.SingleDeckView;

import java.util.ArrayList;
import java.util.List;

public class DisplayDeckAdapter extends RecyclerView.Adapter<DisplayDeckAdapter.ViewHolder> {//implements
   // View.OnClickListener{
  private View.OnClickListener listener;
  private Context myContext;
  private ArrayList <DeckDecorator> deckSet;

  public DisplayDeckAdapter(Context context) {
    super();
    this.myContext = context;
    this.deckSet = new ArrayList<>();
  }

  // inflates new views from xml layout
  @Override
  public ViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
    // gets the context of the parent
    Context parentContext = parent.getContext();

    // create new view and caches it in a ViewHolder
    View view = LayoutInflater.from(parentContext).inflate(R.layout.item_deck, parent, false);
    ViewHolder decksVHolder = new ViewHolder(view);

    return decksVHolder;
  }

  /**
   *  Invoked by layout manager to replace the contents of each item view
   * @param holder
   * @param position
   */
  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    // get the object to be bound from our array and bind it to the viewholder
    DeckDecorator deck = deckSet.get(position);
    holder.bindRestraint(deck, position);
  }

  /**
   * Also invoked by layout manager to get the size of the dataset
   * @return
   */
  public int getItemCount() {
    return deckSet.size();
  }

  public void addDecks (List<DeckDecorator> addedDecks){
    deckSet.addAll(addedDecks);
    // notifies the adapter
    this.notifyDataSetChanged();
  }

  public void onClick (View view) {
    // get the tag from the view
    String position = view.getTag().toString();
    Toast.makeText(myContext, position, Toast.LENGTH_SHORT).show();

    SingleDeckView singleDeckFragment = new SingleDeckView();
    Bundle args = new Bundle();
    args.putString("POSITION", position);
    singleDeckFragment.setArguments(args);

  }

  public void addOnClickListener (View.OnClickListener listener) {
    this.listener = listener;
  }

  // class for caching item data
  class ViewHolder extends RecyclerView.ViewHolder { //implements View.OnClickListener{
    private Context context;

    // cache the data
    TextView deckName;
    TextView deckAuthor;

    ViewHolder(View itemView) {
      super(itemView);
      context = itemView.getContext();

      // attaches the TextView objects to the item
      deckName = itemView.findViewById(R.id.deck_item_deckname);
      deckAuthor = itemView.findViewById(R.id.deck_item_author);

      // sets the onClick listener
      itemView.setOnClickListener(listener);
    }

    // binds a deck object to the view holder
    public void bindRestraint(DeckDecorator deck, int position) {
      deckName.setText(deck.getDeckName());
      deckAuthor.setText(deck.getAuthor());

      itemView.setTag(position);
    }

//    @Override
//    public void onClick(View view) {
//      // get the tag from the view
//      int position = (int) view.getTag();
//      Toast.makeText(myContext, position + " ", Toast.LENGTH_SHORT).show();
//
//      // get the deck at the position
//      DeckDecorator openingDeck = deckSet.get((int) view.getTag());
//
//      SingleDeckView singleDeckFragment = new SingleDeckView();
//      Bundle args = new Bundle();
//      // passes te object to the new fragment
//      args.putParcelable("deck", openingDeck);
//      singleDeckFragment.setArguments(args);
//    }

  }
  public DeckDecorator getDeck(int position) {
    // get the deck at the specified position
    return deckSet.get(position);
  }

}