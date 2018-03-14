//package com.hestia.presentationlayer.customadapter;
//
//import android.content.Context;
//import android.support.v7.widget.RecyclerView;
//import android.text.Layout;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Adapter;
//import android.widget.ArrayAdapter;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.hestia.R;
//import com.hestia.domainlayer.Deck;
//import com.hestia.presentationlayer.displaydecks.DisplayDecksPresenter;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by Richard on 3/9/2018.
// *
// * Uses viewholder pattern to cache views for later item lookup
// */
//
//public class ListDisplayDeckAdapter extends Adapter <ListDisplayDeckAdapter.ViewHolder> implements View.OnClickListener{
//    private ArrayList <Deck> dataset;
//    private Context mContext;
//
//    // inner class for the lookup cache
//    private static class ViewHolder {
//      TextView deckName;
//      TextView deckAuthor;
//      TextView itemNumber;
//    }
//
//    public ListDisplayDeckAdapter(List<Deck> data, Context context) {
//      // calls the parent constructor using our custom deck item layout
//      super(context, R.layout.item_deck, data);
//
//      this.dataset = (ArrayList) data;
//      this.mContext = context;
//    }
//
//    public void onClick(View view) {
//      Toast.makeText(getContext(), "HAHA YEET", Toast.LENGTH_LONG).show();
//    }
//
//  /**
//   * This method is used to generate and return the view for each row (item) in the list
//   * @param position
//   * @param convertView
//   * @param parent
//   * @return
//   */
//    @Override
//    public View getView (int position, View convertView, ViewGroup parent) {
//      // get the right deck for this item
//      Deck currentDeck = dataset.get(position);
//
//      // cache data stored in tag
//      ViewHolder viewHolder;
//
//      // if the view is not being reused
//      if (convertView == null) {
//        // inflate the view as it isn't being reused
//        LayoutInflater inflater = LayoutInflater.from(getContext());
//        convertView = inflater.inflate(R.layout.item_deck, parent, false);
//
//        // creates the item to be added as a tag to the view item
//        viewHolder = new ViewHolder();
//        viewHolder.deckName = convertView.findViewById(R.id.deck_name);
//        viewHolder.deckAuthor = convertView.findViewById(R.id.deck_author);
//        viewHolder.itemNumber = convertView.findViewById(R.id.deck_created_time);
//
//        // setting tags basically injects info into every item for access by methods like onclick
//        convertView.setTag(viewHolder);
//      }
//      else {
//        // if it already exists, get the tag data from the item
//        viewHolder = (ViewHolder) convertView.getTag();
//      }
//
//      // sets the new viewHolder item with data
//      viewHolder.deckName.setText(currentDeck.getDeckName());
//      viewHolder.deckAuthor.setText(currentDeck.getAuthor());
//      viewHolder.itemNumber.setText("meme big boy");
//      viewHolder.itemNumber.setOnClickListener(this);
//
//      return convertView;
//    }
//}