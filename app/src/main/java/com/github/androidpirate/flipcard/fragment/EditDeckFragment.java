package com.github.androidpirate.flipcard.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.github.androidpirate.flipcard.MainActivity;
import com.github.androidpirate.flipcard.R;
import com.github.androidpirate.flipcard.adapter.EditDeckAdapter;
import com.github.androidpirate.flipcard.model.Deck;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EditDeckFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EditDeckFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditDeckFragment extends Fragment {
    private static final String ARG_DECK = "deck";
    private static final String ARG_EDIT_MODE = "edit_mode";
    private boolean mIsEditing;
    private Deck mDeck;
    private RecyclerView mRecyclerView;
    private FloatingActionButton mFab;
    private EditDeckAdapter mAdapter;
    private OnFragmentInteractionListener mListener;

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface OnFragmentInteractionListener {
        void saveDeck(Deck deck);
        void updateDeck(Deck deck);
        List<Deck> getDecks();
        Deck getDeck(int deckId);
        void replaceFragment(Fragment fragment);
    }

    public EditDeckFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     */
    public static EditDeckFragment newInstance(Deck deck, boolean isEditing) {
        EditDeckFragment fragment = new EditDeckFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_DECK, deck);
        args.putBoolean(ARG_EDIT_MODE, isEditing);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mIsEditing = getArguments().getBoolean(ARG_EDIT_MODE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_deck, container, false);

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        if(activity.getSupportActionBar() != null) {
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        mFab = view.findViewById(R.id.fab);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAdapter.addEmptyCard();
                mAdapter.refresh();
                mRecyclerView.smoothScrollToPosition(mAdapter.getItemCount());
            }
        });
        mRecyclerView = view.findViewById(R.id.rv_deck_create);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mDeck = (Deck) getArguments().getSerializable(ARG_DECK);
        if(mAdapter == null) {
            mAdapter = new EditDeckAdapter(mDeck);
        }
        mRecyclerView.setAdapter(mAdapter);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity activity = (MainActivity) getActivity();
        if(activity != null) {
            activity.showUpButton();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if (mIsEditing) {
            inflater.inflate(R.menu.edit_deck_menu, menu);
        } else {
            inflater.inflate(R.menu.create_deck_menu, menu);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_deck:
                // Save deck to database here
                mListener.saveDeck(mAdapter.getDeck());
                return true;
            case R.id.update_deck:
                // Update deck in database here
                mListener.updateDeck(mDeck);
                return true;
            case android.R.id.home:
                if(mIsEditing) {
                    // Return back to DeckDetailFragment here
                    Deck deck = mListener.getDeck(mDeck.getId());
                    mListener.replaceFragment(DeckDetailFragment.newInstance(deck));
                    return true;
                } else {
                    // Return back to DeckListFragment here
                    ArrayList<Deck> decks = (ArrayList<Deck>) mListener.getDecks();
                    mListener.replaceFragment(DeckListFragment.newInstance(decks));
                    return true;
                }
        }
        return super.onOptionsItemSelected(item);
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