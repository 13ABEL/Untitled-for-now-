package com.hestia.presentationlayer.displaysaved;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hestia.R;
import com.hestia.domainlayer.Deck;
import com.hestia.presentationlayer.DeckDecorator;
import com.hestia.presentationlayer.editdeck.EditDeckView;

public class NewDeckDialog extends DialogFragment {
  private final String TAG = "CREATE_DECK_DIALOG";

  View rootView;

  String currentUID;

  // used for checking
  EditText deckName;
  RadioGroup classSelect;
  RadioGroup formatSelect;

  public void onCreate (Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    // gets the user id
    currentUID = FirebaseAuth.getInstance().getCurrentUser().getUid();
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
        // checks to see if the user has changed to wild for the deck format
        Boolean isStandard = true;
        if (formatSelect.getCheckedRadioButtonId() == R.id.standard) {
          isStandard = false;
        }

        // get the index of the selected child and add 1 to get the class ID
        RadioButton selectedClass = rootView.findViewById(deckClass);
        int classId = classSelect.indexOfChild(selectedClass) + 1;

        // Create a new deck with additional properties and passes it to the fragment to edit
        DeckDecorator newDeck = new DeckDecorator("test_id",
            currentUID, classId);
        newDeck.setDeckName(deckName.getText().toString());
        newDeck.setFormat(isStandard);
        
        // create the new fragment for editing a deck and the bundle for the new deck
        EditDeckView createDeckFragment = new EditDeckView();
        Bundle args = new Bundle();
        args.putParcelable("deck", newDeck);

        createDeckFragment.setArguments(args);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        // add fragment on top of the root tab fragment in backstack
        transaction.replace(R.id.content_frame, createDeckFragment)
          // adds the replaced fragment to the backstack to allow user to navigate back to it
          .addToBackStack(null)
          .commit();

        // close the dialog
        dismiss();
      }
    }


  }


}
