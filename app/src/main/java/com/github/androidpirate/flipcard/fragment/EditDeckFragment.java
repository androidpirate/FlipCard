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
 * {@link EditDeckFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EditDeckFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditDeckFragment extends Fragment
    implements DeckDetailAdapter.OnAdapterInteractionListener {
    private static final String FRAGMENT_NAME = EditDeckFragment.class.getSimpleName();
    private static final String ARG_DECK = "deck";
    private Deck mDeck;
    private RecyclerView mRecyclerView;
    private DeckDetailAdapter mAdapter;

    private OnFragmentInteractionListener mListener;

    public EditDeckFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment EditDeckFragment.
     */
    public static EditDeckFragment newInstance(Deck deck) {
        EditDeckFragment fragment = new EditDeckFragment();
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
        View view = inflater.inflate(R.layout.fragment_edit_deck, container, false);
        // Inflate the layout for this fragment
        mRecyclerView = view.findViewById(R.id.rv_deck_edit);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        if(mAdapter == null) {
            mAdapter = new DeckDetailAdapter(this, mDeck, FRAGMENT_NAME);
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
