package com.github.androidpirate.flipcard;

import android.support.v4.app.Fragment;

import com.github.androidpirate.flipcard.data.DeckDbHelper;
import com.github.androidpirate.flipcard.fragment.CreateDeckFragment;
import com.github.androidpirate.flipcard.fragment.DeckDetailFragment;
import com.github.androidpirate.flipcard.fragment.DeckListFragment;
import com.github.androidpirate.flipcard.model.Deck;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends SingleFragmentActivity
    implements DeckListFragment.OnFragmentInteractionListener,
                DeckDetailFragment.OnFragmentInteractionListener,
                CreateDeckFragment.OnFragmentInteractionListener {

    private DeckDbHelper mDbHelper;

    @Override
    protected Fragment createFragment() {
        ArrayList<Deck> decks = (ArrayList<Deck>) mDbHelper.getAllDecks();
        return DeckListFragment.newInstance(decks);
    }

    @Override
    protected void getDatabaseHelper() {
        getDeckDbHelperInstance();
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
        mDbHelper.addDeck(deck);
        replaceFragment(DeckListFragment.newInstance((ArrayList<Deck>) mDbHelper.getAllDecks()));
    }

    @Override
    public List<Deck> getDecks() {
        return mDbHelper.getAllDecks();
    }

    private void getDeckDbHelperInstance() {
        mDbHelper = DeckDbHelper.newInstance(getApplicationContext());
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