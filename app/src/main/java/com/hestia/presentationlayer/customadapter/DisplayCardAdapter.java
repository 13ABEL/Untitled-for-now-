package com.hestia.presentationlayer.customadapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.hestia.presentationlayer.displaycards.DisplayCardsContract;

/**
 * Created by Richard on 3/30/2018.
 */

public class DisplayCardAdapter extends RecyclerView.Adapter<DisplayCardAdapter.ViewHolder> {

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return null;
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {

  }

  @Override
  public int getItemCount() {
    return 0;
  }

  class ViewHolder extends RecyclerView.ViewHolder {
    ViewHolder (View view) {
      super(view);



    }

  }




}
