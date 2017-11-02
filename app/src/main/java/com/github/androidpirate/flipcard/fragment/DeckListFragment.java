package com.github.androidpirate.flipcard.fragment;

import android.content.Context;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
import com.github.androidpirate.flipcard.R;
import com.github.androidpirate.flipcard.adapter.DeckListAdapter;
import com.github.androidpirate.flipcard.model.Deck;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DeckListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DeckListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DeckListFragment extends Fragment
    implements DeckListAdapter.OnAdapterInteractionListener {
    private static final String ARG_DECKS = "decks";
    private Toolbar mToolbar;
    private ArrayList<Deck> mDecks;
    private TextView mEmptyListText;
    private RecyclerView mRecyclerView;
    private DeckListAdapter mAdapter;
    private OnFragmentInteractionListener mListener;

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface OnFragmentInteractionListener {
        void replaceFragment(Fragment fragment);
    }

    public DeckListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment DeckListFragment.
     */
    public static DeckListFragment newInstance(ArrayList<Deck> decks) {
        DeckListFragment fragment = new DeckListFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_DECKS, decks);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            mDecks = (ArrayList<Deck>) getArguments().getSerializable(ARG_DECKS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_deck_list, container, false);
        mToolbar = view.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(mToolbar);
        if(activity.getSupportActionBar() != null) {
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        mEmptyListText = view.findViewById(R.id.tv_empty_list);
        mRecyclerView = view.findViewById(R.id.rv_deck_list);
        if(mDecks.size() == 0) {
            displayEmptyListText();
        } else {
            displayDeckList();
        }
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity activity = (MainActivity) getActivity();
        if(activity != null) {
            activity.hideUpButton();
        }
    }

    private void displayEmptyListText(){
        mRecyclerView.setVisibility(View.INVISIBLE);
        mEmptyListText.setVisibility(View.VISIBLE);
        mEmptyListText.setText("No recent decks.");
    }

    private void displayDeckList(){
        mEmptyListText.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        if(mAdapter == null) {
            mAdapter = new DeckListAdapter(this, mDecks);
        }
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_deck_list_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_deck:
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.beginTransaction()
                        .replace(R.id.fragment_container,
                                CreateDeckFragment.newInstance(new Deck()))
                        .commit();
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
    public void onItemClick(Deck deck) {
        Fragment fragment = DeckDetailFragment.newInstance(deck);
        mListener.replaceFragment(fragment);
    }
}