package com.hestia.presentationlayer.customadapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import android.widget.Toast;

import com.hestia.R;
import com.hestia.domainlayer.Card;
import java.util.List;

/**
 * Created by Richard on 3/29/2018.
 */

public class SingleDeckCardAdapter extends RecyclerView.Adapter <SingleDeckCardAdapter.ViewHolder> {
  private List <Card> deckList;
  private Context mContext;
  private ViewGroup parent;

  /**
   * Follows the viewholder pattern
   * Used to present items and allow item details to be changed easily
   */
  class ViewHolder extends RecyclerView.ViewHolder {
    TextView cardName;
    TextView cardCost;

    ViewHolder (View view) {
      super(view);
      // binds view to variables to allow their values to be replaced
      cardName = view.findViewById(R.id.item_card_name);
      cardCost = view.findViewById(R.id.item_card_cost);
    }
    public void setPosition(int position) {
      itemView.setTag(position);
    }
  }


  public SingleDeckCardAdapter(@NonNull Context context, @NonNull  List <Card> cardList) {
    super();
    // initializes variables for later adapter use
    this.mContext = context;
    this.deckList = cardList;
    //Log.d("TESTING ADAPTER", "TEST " +deckList.get(0).getName() + " " + deckList.get(1).getName() );
  }


  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    // inflates the view for each object using a layout inflater
    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.item_card, parent, false);

    this.parent = parent;
    // creates a new viewholder for the inflated item
    return new ViewHolder(view);
  }


  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    // get the card at the position
    Card currentCard = deckList.get(position);
    // replace the values of the vh with the card's values at this position
    holder.cardName.setText(currentCard.getName());
    String cardCost = currentCard.getCost() + "";
    holder.cardCost.setText(cardCost);
  }


  @Override
  public int getItemCount() {
    return deckList.size();
  }



  public View getView (int position, View convertView, ViewGroup parent) {
    // view to be returned
    View resultView;
    // retrieve the Card object based on position
    Card currentCard = deckList.get(position);

    ViewHolder viewHolder;
    // creates the new view if it doesn't already exist
    if (convertView == null) {
      LayoutInflater inflater = LayoutInflater.from(mContext);
      resultView = inflater.inflate(R.layout.deck_card_item, parent, false);

      // Initializes the viewholder and its references to the newly inflated layout
      viewHolder = new ViewHolder(convertView);
      viewHolder.cardName = resultView.findViewById(R.id.deck_card_name);
      viewHolder.cardCost = resultView.findViewById(R.id.deck_card_cost);

      // allows the view holder to be accessed later on
      resultView.setTag(viewHolder);
    }
    else {
      // retrieves the view holder object from the view
      viewHolder = (ViewHolder) convertView.getTag();
      resultView = convertView;
    }

    // use the view holder to update the layout values
    viewHolder.cardName.setText(currentCard.getName());
    //viewHolder.cardCost.setText(currentCard.getCost() + "");
    Log.d("viewholder set", "TEST " +currentCard.getName() + " " + currentCard.getCost() );

    // return completed view to be rendered
    return resultView;
  }



}
