package com.github.androidpirate.flipcard.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.androidpirate.flipcard.R;
import com.github.androidpirate.flipcard.adapter.DeckDetailAdapter;
import com.github.androidpirate.flipcard.model.Deck;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CreateDeckFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CreateDeckFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateDeckFragment extends Fragment
    implements DeckDetailAdapter.OnAdapterInteractionListener {
    private static final String FRAGMENT_NAME = CreateDeckFragment.class.getSimpleName();
    private static final String ARG_DECK = "deck";
    private RecyclerView mRecyclerView;
    private FloatingActionButton mFab;
    private DeckDetailAdapter mAdapter;
    private OnFragmentInteractionListener mListener;

    public CreateDeckFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     */
    public static CreateDeckFragment newInstance(Deck deck) {
        CreateDeckFragment fragment = new CreateDeckFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_DECK, deck);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_deck, container, false);
        // Inflate the layout for this fragment
        mFab = view.findViewById(R.id.fab);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Add a new card here
            }
        });

        mRecyclerView = view.findViewById(R.id.rv_deck_create);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        Deck deck = (Deck) getArguments().getSerializable(ARG_DECK);
        if(mAdapter == null) {
            mAdapter = new DeckDetailAdapter(this, deck, FRAGMENT_NAME);
        }
        mRecyclerView.setAdapter(mAdapter);
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface OnFragmentInteractionListener {
    }

    @Override
    public void onEditIconClick(Deck deck) {
        // Do nothing here
    }
}