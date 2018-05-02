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
    Picasso.get().load("https://www.gstatic.com/webp/gallery3/1.png").fit().into(image);


    return rootview;
  }
}
