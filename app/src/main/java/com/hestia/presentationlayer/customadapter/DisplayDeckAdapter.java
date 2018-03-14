package com.hestia.presentationlayer.customadapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.hestia.R;
import com.hestia.domainlayer.Deck;
import com.hestia.presentationlayer.displaydecks.DisplayDecksContract;

import java.util.ArrayList;
import java.util.List;

public class DisplayDeckAdapter extends RecyclerView.Adapter<DisplayDeckAdapter.ViewHolder>
implements View.OnClickListener{
  private Context myContext;
  private ArrayList <Deck> deckSet;

  public DisplayDeckAdapter(Context context) {
    this.myContext = context;
    this.deckSet = new ArrayList<>();
  }

  // inflates new views from xml layout
  @Override
  public DisplayDeckAdapter.ViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
    // gets the context of the parent
    Context parentContext = parent.getContext();

    // create new view and caches it in the a ViewHolder
    View view = LayoutInflater.from(parentContext).inflate(R.layout.item_deck, parent, false);
    ViewHolder decksVHolder = new ViewHolder(view);

    view.setOnClickListener(this);

    return decksVHolder;
  }

  /**
   *  Invoked by layout manager to replace the contents of each item view
   * @param holder
   * @param position
   */
  @Override
  public void onBindViewHolder(@NonNull DisplayDeckAdapter.ViewHolder holder, int position) {
    // get the object to be bound from our array and bind it
    Deck deck = deckSet.get(position);
    holder.bindRestraint(deck);
  }

  /**
   * Also invoked by layout manager to get the size of the dataset
   * @return
   */
  public int getItemCount() {
    return deckSet.size();
  }

  public void addDecks (List<Deck> addedDecks){
    deckSet.addAll(addedDecks);
    // notifies the adapter
    this.notifyDataSetChanged();
  }

  public void onClick (View view) {
    // get the tag from the view
    view.getTag();
    Toast.makeText(myContext, "HAHA YEET", Toast.LENGTH_LONG).show();
  }

  // class for caching item data
  class ViewHolder extends RecyclerView.ViewHolder {
    private Context context;

    // cache the data
    TextView deckName;
    TextView deckAuthor;

    ViewHolder(View itemView) {
      super(itemView);
      context = itemView.getContext();

      // attaches the TextView objects to the item
      deckName =  itemView.findViewById(R.id.deck_name);
      deckAuthor =  itemView.findViewById(R.id.deck_author);
    }

    // binds a deck object to the view holder
    public void bindRestraint(Deck deck) {
      deckName.setText(deck.getDeckName());
      deckAuthor.setText(deck.getAuthor());
    }
  }

}