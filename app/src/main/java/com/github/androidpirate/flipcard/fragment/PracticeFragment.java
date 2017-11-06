package com.github.androidpirate.flipcard.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.androidpirate.flipcard.R;
import com.github.androidpirate.flipcard.model.Deck;
import com.github.androidpirate.flipcard.model.FlipCard;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PracticeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PracticeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PracticeFragment extends Fragment {
    private static final String ARG_DECK = "deck";
    private Deck mDeck;
    private int mCardIndex;
    private int mScore;
    private ProgressBar mProgressBar;
    private CardView mCardView;
    private TextView mFrontText;
    private EditText mUserInput;
    private ImageButton mInputButton;
    private OnFragmentInteractionListener mListener;

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface OnFragmentInteractionListener {

    }

    public PracticeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment PracticeFragment.
     */
    public static PracticeFragment newInstance(Deck deck) {
        PracticeFragment fragment = new PracticeFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_DECK, deck);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mDeck = (Deck) getArguments().getSerializable(ARG_DECK);
        }
        mScore = 0;
        mCardIndex = 0;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_practice, container, false);
        // Inflate the layout for this fragment
        mProgressBar = view.findViewById(R.id.progress_bar);
        mCardView = view.findViewById(R.id.cv_front_card);
        mFrontText = view.findViewById(R.id.tv_front_text);
        mFrontText.setText(mDeck.getCards().get(mCardIndex).getFrontSide());
        mUserInput = view.findViewById(R.id.et_user_input);
        mInputButton = view.findViewById(R.id.ib_input_button);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
