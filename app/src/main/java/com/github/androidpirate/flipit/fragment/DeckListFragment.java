package com.github.androidpirate.flipit.fragment;

import android.content.Context;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
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
import android.widget.Toast;

import com.github.androidpirate.flipit.MainActivity;
import com.github.androidpirate.flipit.R;
import com.github.androidpirate.flipit.adapter.DeckListAdapter;
import com.github.androidpirate.flipit.model.Deck;

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
    private static final boolean EDIT_MODE_ON = false;
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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_deck_list, container, false);
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if(activity != null) {
            activity.setSupportActionBar(toolbar);
        }
        mEmptyListText = view.findViewById(R.id.tv_empty_list);
        mRecyclerView = view.findViewById(R.id.rv_deck_list);
        if(mDecks.size() == 0) {
            displayEmptyListText();
        } else {
            displayDeckList();
        }
        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),
                        "Replace this action with the toolbar action.",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        });
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_deck_list_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_deck:
                if(getActivity() != null) {
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    fm.beginTransaction()
                            .replace(R.id.fragment_container,
                                    EditDeckFragment.newInstance(new Deck(), EDIT_MODE_ON))
                            .commit();
                }
                return true;
            case R.id.sort_by_date:
                // Sort the list by date here
                return true;
            case R.id.sort_by_category:
                // Sort the list by category here
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

    private void displayEmptyListText(){
        mRecyclerView.setVisibility(View.INVISIBLE);
        mEmptyListText.setVisibility(View.VISIBLE);
        mEmptyListText.setText(getString(R.string.empty_list_warning));
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
}