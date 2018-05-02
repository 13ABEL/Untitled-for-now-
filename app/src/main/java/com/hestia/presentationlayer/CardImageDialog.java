package com.hestia.presentationlayer;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

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

    // Retreives the
    Bundle args = getArguments();
    if (args != null) {

      String cardURL = args.getString("url");
      Toast.makeText(getContext(), cardURL, Toast.LENGTH_SHORT).show();

      Picasso.get().load(cardURL).resize(200, 500).centerInside().into(image);
    }
    else {
      Toast.makeText(getContext(), "EMPTY AARGS", Toast.LENGTH_SHORT).show();
      // display the dummy image
    }




    return rootview;
  }
}
