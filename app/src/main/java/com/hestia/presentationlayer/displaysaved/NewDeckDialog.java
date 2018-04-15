package com.hestia.presentationlayer.displaysaved;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.hestia.R;

public class NewDeckDialog extends DialogFragment {
  View rootView;

  // used for checking
  EditText deckName;
  RadioGroup classSelect;
  RadioGroup formatSelect;

  public void onCreate (Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    // inflates the layout
    rootView = inflater.inflate(R.layout.new_deck_dialog, container, false);

    // initialize references to input to check and collect later
    classSelect = rootView.findViewById(R.id.new_deck_class_select);
    deckName = rootView.findViewById(R.id.new_deck_enter_name);
    formatSelect = rootView.findViewById(R.id.new_deck_format);

    // find the create button and attach the appropriate listener
    Button submitButton = rootView.findViewById(R.id.new_deck_submit);
    submitButton.setOnClickListener(new submitDeckListener());

    return rootView;
  }

  class submitDeckListener implements View.OnClickListener{
    @Override
    public void onClick(View v) {
      // checks fields and tells user if there are any are unchecked
      String name = deckName.getText().toString();
      int deckClass = classSelect.getCheckedRadioButtonId();

      if (name.length() == 0) {
        Toast.makeText(getContext(), "Please give your deck a name!", Toast.LENGTH_SHORT).show();
      }
      else if (deckClass == -1) {
        Toast.makeText(getContext(), "Please select a class!", Toast.LENGTH_SHORT).show();
      }
      else {
        // open the new fragment for constructing the deck
        Toast.makeText(getContext(), name + " " + deckClass, Toast.LENGTH_SHORT).show();

      }
    }
  }


}
