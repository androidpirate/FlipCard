/*
 * <!--
 *  Copyright (C) 2016 The Android Open Source Project
 *
 *      Licensed under the Apache License, Version 2.0 (the "License");
 *      you may not use this file except in compliance with the License.
 *      You may obtain a copy of the License at
 *
 *           http://www.apache.org/licenses/LICENSE-2.0
 *
 *      Unless required by applicable law or agreed to in writing, software
 *      distributed under the License is distributed on an "AS IS" BASIS,
 *      WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *      See the License for the specific language governing permissions and
 *      limitations under the License.
 * -->
 */

package com.github.androidpirate.flipcard.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.github.androidpirate.flipcard.R;
import com.github.androidpirate.flipcard.model.FlipCard;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FrontCardFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FrontCardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FrontCardFragment extends Fragment {
    private static final String ARG_CARD = "card";
    private FlipCard mCard;
    private EditText mUserInput;
    private OnFragmentInteractionListener mListener;

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface OnFragmentInteractionListener {
        void displayCorrectAnswerAnimation();
        void flipToBack();
    }

    public FrontCardFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment FrontCardFragment.
     */
    public static FrontCardFragment newInstance(FlipCard card) {
        FrontCardFragment fragment = new FrontCardFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_CARD, card);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            mCard = (FlipCard) getArguments().getSerializable(ARG_CARD);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_front_card, container, false);
        TextView frontText = view.findViewById(R.id.tv_front_text);
        if(mCard != null) {
            frontText.setText(mCard.getFrontSide());
        }
        ImageButton flipCard = view.findViewById(R.id.ib_flip_card);
        flipCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.flipToBack();
            }
        });
        mUserInput = view.findViewById(R.id.et_user_input);
        mUserInput.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                checkAnswer(mUserInput.getText().toString(), mCard.getRearSide());
                return false;
            }
        });
        ImageButton inputButton = view.findViewById(R.id.ib_input_button);
        inputButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(mUserInput.getText().toString(), mCard.getRearSide());
            }
        });
        return view;
    }

    private void checkAnswer(String userInput, String cardRearSide) {
        View view = null;
        if(getActivity() != null) {
            view = getActivity().getCurrentFocus();
        }
        if(view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity()
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
        if(!userInput.isEmpty()) {
            if(userInput.equalsIgnoreCase(cardRearSide)) {
                mListener.displayCorrectAnswerAnimation();
            } else {
                mListener.flipToBack();
            }
        }
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
