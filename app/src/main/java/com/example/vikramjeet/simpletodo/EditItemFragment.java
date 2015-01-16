package com.example.vikramjeet.simpletodo;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EditItemFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EditItemFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditItemFragment extends DialogFragment implements TextView.OnEditorActionListener{

    private EditText mEditText;

    public interface EditItemFragmentListener {
        void onFinishEditDialog(String itemToEdit, int indexToUpdate);
    }

    public EditItemFragment() {
        // Empty constructor required for DialogFragment
    }

    public static EditItemFragment newInstance(String title, int index) {
        EditItemFragment frag = new EditItemFragment();

        // Creating arguments bundle to use
        Bundle args = new Bundle();

        // updating args bundle
        args.putString("item", title);
        args.putInt("index", index);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_item, container);

        mEditText = (EditText) view.findViewById(R.id.txt_your_name);
        String itemToEdit = getArguments().getString("item");
        mEditText.setText(itemToEdit);
        // Show soft keyboard automatically
        mEditText.requestFocus();
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        mEditText.setOnEditorActionListener(this);

        return view;
    }

    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (EditorInfo.IME_ACTION_DONE == actionId) {
            // Return input text to activity
            int indexToUpdate = getArguments().getInt("index");

            EditItemFragmentListener listener = (EditItemFragmentListener) getActivity();
            listener.onFinishEditDialog(mEditText.getText().toString(), indexToUpdate);
            dismiss();
            return true;
        }
        return false;
    }
}