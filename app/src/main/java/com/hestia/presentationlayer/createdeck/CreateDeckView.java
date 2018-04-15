package com.hestia.presentationlayer.createdeck;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class CreateDeckView extends Fragment implements CreateDeckContract.View{
  private final String TAG = "CREATE_DECK";
  private Boolean isStandard = true;
  CreateDeckContract.Presenter cPresenter;

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

    String data = getArguments().getCharSequence("deckName").toString();





    return null;
  }

}
