package com.github.androidpirate.flipit;

import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.github.androidpirate.flipit.data.DeckDbHelper;
import com.github.androidpirate.flipit.fragment.EditDeckFragment;
import com.github.androidpirate.flipit.fragment.DeckDetailFragment;
import com.github.androidpirate.flipit.fragment.DeckListFragment;
import com.github.androidpirate.flipit.model.Deck;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends SingleFragmentActivity
    implements DeckListFragment.OnFragmentInteractionListener,
                DeckDetailFragment.OnFragmentInteractionListener,
                EditDeckFragment.OnFragmentInteractionListener {
    private static final String EXTRA_FRAGMENT_INFO = "extra_fragment_info";
    private static final String EXTRA_DECK = "extra_deck";
    private static final String FRAGMENT_DECK_DETAIL = DeckDetailFragment.class.getSimpleName();
    private DeckDbHelper mDbHelper;

    @Override
    protected Fragment createFragment() {
        mDbHelper = DeckDbHelper.newInstance(getApplicationContext());
        String fragment1 = getIntent().getStringExtra(EXTRA_FRAGMENT_INFO);
        Fragment fragment;
        if(fragment1 != null && fragment1.equals(FRAGMENT_DECK_DETAIL)) {
            Deck deck = (Deck) getIntent().getSerializableExtra(EXTRA_DECK);
            fragment = DeckDetailFragment.newInstance(deck);
        } else {
            ArrayList<Deck> decks = (ArrayList<Deck>) mDbHelper.getAllDecks();
            return DeckListFragment.newInstance(decks);
        }
        return fragment;
    }

    @Override
    public void replaceFragment(Fragment fragment) {
        if(fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
        }
    }

    @Override
    public void saveDeck(Deck deck) {
        if(deck.getTitle() != null && !deck.getTitle().equals("")) {
            mDbHelper.addDeck(deck);
            replaceFragment(DeckListFragment.newInstance((ArrayList<Deck>) mDbHelper.getAllDecks()));
        } else {
            Toast.makeText(this, getString(R.string.empty_deck_title_warning), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void updateDeck(Deck deck) {
        if(deck.getTitle() != null && !deck.getTitle().equals("")) {
            mDbHelper.updateDeck(deck);
            String deckIdString = String.valueOf(deck.getId());
            replaceFragment(DeckDetailFragment.newInstance((mDbHelper.getDeck(deckIdString))));
        } else {
            Toast.makeText(this, getString(R.string.empty_deck_title_warning), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public List<Deck> getDecks() {
        return mDbHelper.getAllDecks();
    }

    @Override
    public Deck getDeck(int deckId) {
        String deckIdString = String.valueOf(deckId);
        return mDbHelper.getDeck(deckIdString);
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = DeckListFragment
                .newInstance((ArrayList<Deck>) mDbHelper.getAllDecks());
        replaceFragment(fragment);
    }

    public void showUpButton() {
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    public void hideUpButton() {
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
    }
}