package com.hestia.presentationlayer.customadapter;

import android.arch.lifecycle.LiveData;
import android.arch.paging.PagedList;
import android.arch.paging.PagedListAdapter;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.hestia.R;
import com.hestia.datalayer.Card.CardDecorator;
import com.hestia.domainlayer.Card;
import com.hestia.presentationlayer.DeckDecorator;
import com.hestia.presentationlayer.displaycards.DisplayCardsContract;


/**
 * Created by Richard on 3/30/2018.
 */

public class DisplayCardAdapter extends PagedListAdapter<CardDecorator, DisplayCardAdapter.ViewHolder> {
  private View.OnClickListener cardListener;

  /**
   * Follows the viewholder pattern
   * Used to present items and allow item details to be changed easily
   */
  class ViewHolder extends RecyclerView.ViewHolder {
      TextView cardName;
      TextView cardCost;

    ViewHolder (View view) {
      super(view);
      // binds our variables to elements in the view
      cardName = view.findViewById(R.id.item_card_name);
      cardCost = view.findViewById(R.id.item_card_cost);

      // sets the onClick listener
      view.setOnClickListener(cardListener);
    }

    public void setPosition(int position) {
      itemView.setTag(position);
    }
  }

  public DisplayCardAdapter() {
    super(new DiffUtil.ItemCallback<CardDecorator>() {
      @Override
      public boolean areItemsTheSame(CardDecorator oldCard, CardDecorator newCard) {
        return (oldCard.getID() == newCard.getID());
      }

      @Override
      public boolean areContentsTheSame(CardDecorator oldCard, CardDecorator newCard) {
        return (oldCard.getCost() == newCard.getCost());
      }
    });
  }

  public void addOnClickListener (View.OnClickListener listener) {
    this.cardListener = listener;
  }

  /**
   * Layout manager calls this to create new view items
   * Inflates the view for each item in the recyclerview
   */
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    // inflates the view for each object using a layout inflater
    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.item_card, parent, false);

    // creates a new viewholder for the inflated item
    return new ViewHolder(view);
  }

  /**
   * Layout manager calls this to replace contents of view
   * @param holder: the viewholder with contents we want to replace
   * @param position: position of the item
   */
  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    // get element from room at this position
    CardDecorator currentCard = getItem(position);

    if (currentCard != null) {
      // bind the element to our viewholder
      holder.cardName.setText(currentCard.getName());
      String currentCost = currentCard.getCost()+ "";
      holder.cardCost.setText(currentCost);
    }
    // binds the position to the holders
    holder.setPosition(position);
  }



  public CardDecorator getCard(View view) {
    // gets the card from the item
    int cardPosition = (int) view.getTag();
    return getItem(cardPosition);
  }

}
