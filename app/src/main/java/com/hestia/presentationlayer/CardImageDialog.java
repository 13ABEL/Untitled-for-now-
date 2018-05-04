package com.hestia.presentationlayer;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hestia.R;
import com.squareup.picasso.Picasso;

public class CardImageDialog extends DialogFragment {
  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    super.onCreateView(inflater, container, savedInstanceState);
    // inflates the full image dialog layout file to a view
    View rootview = inflater.inflate(R.layout.card_image_dialog, container, false);

    ImageView image = rootview.findViewById(R.id.card_image_dialog_image);

    // Retrieves the url for the card image from the bundle
    Bundle args = getArguments();
    if (args != null) {
      String cardURL = args.getString("url");
      // TODO add display for flavour text and artist name (create DAO for card metadata)
      // Toast.makeText(getContext(), cardURL, Toast.LENGTH_SHORT).show();

      Picasso.get().load(cardURL).resize(1000, 10000).centerInside().into(image);
    }

    return rootview;
  }
}
