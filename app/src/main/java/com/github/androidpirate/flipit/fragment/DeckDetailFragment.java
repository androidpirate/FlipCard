package com.github.androidpirate.flipit.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
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
import com.github.androidpirate.flipit.PracticeActivity;
import com.github.androidpirate.flipit.R;
import com.github.androidpirate.flipit.adapter.DeckDetailAdapter;
import com.github.androidpirate.flipit.data.DeckDbHelper;
import com.github.androidpirate.flipit.model.Deck;

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
    private static final boolean EDIT_MODE_ON = true;
    private Deck mDeck;
    private DeckDetailAdapter mAdapter;
    private DeckDbHelper mDeckDbHelper;
    private OnFragmentInteractionListener mListener;

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface OnFragmentInteractionListener {
        List<Deck> getDecksFromDatabase();
        Deck getDeckFromDatabase(int deckId);
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
        mDeckDbHelper = DeckDbHelper.newInstance(getContext());
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_deck_detail, container, false);
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle("");
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            activity.setSupportActionBar(toolbar);
        }
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView title = view.findViewById(R.id.tv_deck_title);
        title.setText(mDeck.getTitle());

        TextView category = view.findViewById(R.id.tv_deck_category);
        category.setText(mDeck.getCategory());

        TextView size = view.findViewById(R.id.tv_deck_size);
        size.setText(String.format(getString(R.string.header_deck_size), mDeck.getSize()));

        RecyclerView recyclerView = view.findViewById(R.id.rv_deck_detail);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        if(mAdapter == null) {
            mAdapter = new DeckDetailAdapter(this, mDeck);
        }
        recyclerView.setAdapter(mAdapter);
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
            case R.id.ic_practice:
                // Start PracticeActivity
                Intent intent = new Intent(getContext(), PracticeActivity.class);
                intent.putExtra(EXTRA_DECK, mDeck);
                startActivity(intent);
                return true;
            case R.id.ic_edit:
                // Deck deck = mListener.getDeckFromDatabase(mDeck.getId());
                Deck deck = mDeckDbHelper.getDeck(String.valueOf(mDeck.getId()));
                Fragment fragment = EditDeckFragment.newInstance(deck, EDIT_MODE_ON);
                mListener.replaceFragment(fragment);
                return  true;
            case R.id.ic_pin:
                // Handle pinning a deck on top of the list here
                Toast.makeText(getContext(),
                        getString(R.string.pin_button_toast),
                        Toast.LENGTH_SHORT).show();
                return true;
            case R.id.ic_delete:
                confirmDelete();
                return true;
            case android.R.id.home:
                // Return back to DeckListFragment here
                returnToList();
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

    private void confirmDelete() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                .setTitle(R.string.delete_dialog_title)
                .setMessage(R.string.delete_dialog_message)
                .setPositiveButton(R.string.delete_dialog_positive_button_text,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                DeckDbHelper deckDbHelper = DeckDbHelper.newInstance(getContext());
                                deckDbHelper.deleteDeck(mDeck.getId());
                                returnToList();
                            }
                        })
                .setNegativeButton(R.string.delete_dialog_negative_button_text,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
        builder.create().show();
    }

    private void returnToList() {
        ArrayList<Deck> decks = (ArrayList<Deck>) mDeckDbHelper.getAllDecks();
        mListener.replaceFragment(DeckListFragment.newInstance(decks));
    }
}