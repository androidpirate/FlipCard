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
import com.github.androidpirate.flipcard.adapter.CreateDeckAdapter;
import com.github.androidpirate.flipcard.model.Deck;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CreateDeckFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CreateDeckFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateDeckFragment extends Fragment {
    private static final String ARG_DECK = "deck";
    private RecyclerView mRecyclerView;
    private FloatingActionButton mFab;
    private CreateDeckAdapter mAdapter;
    private OnFragmentInteractionListener mListener;

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface OnFragmentInteractionListener {
        void saveDeck(Deck deck);
        List<Deck> getDecks();
        void replaceFragment(Fragment fragment);
    }

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
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_deck, container, false);
        // Inflate the layout for this fragment
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
        Deck deck = (Deck) getArguments().getSerializable(ARG_DECK);
        if(mAdapter == null) {
            mAdapter = new CreateDeckAdapter(deck);
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
        inflater.inflate(R.menu.fragment_deck_create_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_deck:
                // Save deck to database here
                mListener.saveDeck(mAdapter.getDeck());
                return true;
            case android.R.id.home:
                // Return back to DeckListFragment here
                ArrayList<Deck> decks = (ArrayList<Deck>) mListener.getDecks();
                mListener.replaceFragment(DeckListFragment.newInstance(decks));
                return true;
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