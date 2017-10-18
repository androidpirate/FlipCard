package com.github.androidpirate.flipcard.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.androidpirate.flipcard.R;
import com.github.androidpirate.flipcard.adapter.DeckBuilderAdapter;
import com.github.androidpirate.flipcard.model.Deck;
import com.github.androidpirate.flipcard.model.FlipCard;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BuildDeckFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BuildDeckFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private DeckBuilderAdapter mAdapter;
    private FloatingActionButton mFab;
    private Deck mDeck;

    public BuildDeckFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment BuildDeckFragment.
     */
    public static BuildDeckFragment newInstance() {
        return new BuildDeckFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        initializeDeck();
    }

    private void initializeDeck() {
        mDeck = new Deck();
        mDeck.getCards().add(new FlipCard("", ""));
        mDeck.getCards().add(new FlipCard("", ""));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_build_deck, container, false);
        setViews(view);
        // Inflate the layout for this fragment
        return view;
    }

    private void setViews(View view) {
        mRecyclerView = view.findViewById(R.id.rv_card_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        if(mAdapter == null) {
            mAdapter = new DeckBuilderAdapter(mDeck, mDeck.getCards());
        }
        mRecyclerView.setAdapter(mAdapter);

        mFab = view.findViewById(R.id.fab);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
