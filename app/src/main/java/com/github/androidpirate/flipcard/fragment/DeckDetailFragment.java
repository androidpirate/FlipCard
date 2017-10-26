package com.github.androidpirate.flipcard.fragment;

import android.content.Context;
import android.os.Bundle;
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
 * {@link DeckDetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DeckDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DeckDetailFragment extends Fragment {
    private static final String ARG_DECK = "deck";
    private Deck mDeck;
    private RecyclerView mRecyclerView;
    private DeckDetailAdapter mAdapter;
    private OnFragmentInteractionListener mListener;

    public DeckDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment DeckDetailFragment.
     */
    public static DeckDetailFragment newInstance(Deck deck) {
        DeckDetailFragment fragment = new DeckDetailFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_deck_detail, container, false);
        mRecyclerView = view.findViewById(R.id.rv_deck_detail);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        if(mAdapter == null) {
            mAdapter = new DeckDetailAdapter(mDeck,
                    mDeck.getCards(),
                    DeckDetailFragment.class.getSimpleName());
        }
        mRecyclerView.setAdapter(mAdapter);
        // Inflate the layout for this fragment
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
}