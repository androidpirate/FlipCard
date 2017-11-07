package com.github.androidpirate.flipcard.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import android.widget.TextView;

import com.github.androidpirate.flipcard.MainActivity;
import com.github.androidpirate.flipcard.PracticeActivity;
import com.github.androidpirate.flipcard.R;
import com.github.androidpirate.flipcard.adapter.DeckDetailAdapter;
import com.github.androidpirate.flipcard.model.Deck;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DeckDetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DeckDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DeckDetailFragment extends Fragment
    implements DeckDetailAdapter.OnAdapterInteractionListener {
    private static final String ARG_DECK = "deck";
    private static final String EXTRA_DECK = "extra_deck";
    private Deck mDeck;
    private TextView mTitle;
    private TextView mCategory;
    private TextView mSize;
    private RecyclerView mRecyclerView;
    private DeckDetailAdapter mAdapter;
    private OnFragmentInteractionListener mListener;

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface OnFragmentInteractionListener {
        List<Deck> getDecks();
        void replaceFragment(Fragment fragment);
    }

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
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_deck_detail, container, false);
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle("");
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);

        mTitle = view.findViewById(R.id.tv_deck_title);
        mTitle.setText(mDeck.getTitle());

        mCategory = view.findViewById(R.id.tv_deck_category);
        mCategory.setText(mDeck.getCategory());

        mSize = view.findViewById(R.id.tv_deck_size);
        mSize.setText(String.format(getString(R.string.header_deck_size), mDeck.getSize()));

        mRecyclerView = view.findViewById(R.id.rv_deck_detail);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        if(mAdapter == null) {
            mAdapter = new DeckDetailAdapter(this,
                    mDeck);
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
        inflater.inflate(R.menu.fragment_deck_detail_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.practice:
                // Start PracticeActivity
                Intent intent = new Intent(getContext(), PracticeActivity.class);
                intent.putExtra(EXTRA_DECK, mDeck);
                startActivity(intent);
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

    @Override
    public void onEditIconClick(Deck deck) {
        Fragment fragment = EditDeckFragment.newInstance(deck);
        mListener.replaceFragment(fragment);
    }
}