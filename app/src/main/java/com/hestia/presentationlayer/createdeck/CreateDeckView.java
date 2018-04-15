package com.hestia.presentationlayer.createdeck;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.hestia.R;

public class CreateDeckView extends Fragment implements CreateDeckContract.View{
  private final String TAG = "CREATE_DECK";
  CreateDeckContract.Presenter cPresenter;

  private Boolean isStandard = true;
  private Boolean isSaved = false;
  FloatingActionButton showDeckFAB;

  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    // initializes the presenter for this view
    cPresenter = new CreateDeckPresenter(this);

    // get the argument bundle and retrieve its data
    Bundle dialogInput = this.getArguments();
    getActivity().setTitle(dialogInput.getCharSequence("deckName"));

    String deckClass = (String) dialogInput.getCharSequence("deckClass");

    if (!dialogInput.getBoolean("formatStandard")) {
      isStandard = false;
    }

    Toast.makeText(this.getContext(), "Class : " + deckClass + ", Standard : " + isStandard,
        Toast.LENGTH_SHORT).show();
  }

  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    // initializes the reference the current floating action button and hide it
    showDeckFAB = getActivity().findViewById(R.id.fab);
    if (showDeckFAB != null) {
      showDeckFAB.setVisibility(View.INVISIBLE);
    }

    return null;
  }


  public void onDestroyView () {
    super.onDestroyView();
    // only shows the fab if the deck is not saved
    if (!isSaved) {
      if (showDeckFAB != null) {
        // adds the listener to the fab
        showDeckFAB.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            Snackbar.make(view, "haha yeet", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
          }
        });
        // show the Floating Acitivyt Button
        showDeckFAB.setVisibility(View.VISIBLE);
      }
    }
  }

}
