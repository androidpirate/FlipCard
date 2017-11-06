package com.github.androidpirate.flipcard;

import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.github.androidpirate.flipcard.data.DeckDbHelper;
import com.github.androidpirate.flipcard.fragment.CreateDeckFragment;
import com.github.androidpirate.flipcard.fragment.DeckDetailFragment;
import com.github.androidpirate.flipcard.fragment.DeckListFragment;
import com.github.androidpirate.flipcard.fragment.PracticeFragment;
import com.github.androidpirate.flipcard.model.Deck;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends SingleFragmentActivity
    implements DeckListFragment.OnFragmentInteractionListener,
                DeckDetailFragment.OnFragmentInteractionListener,
                CreateDeckFragment.OnFragmentInteractionListener,
                PracticeFragment.OnFragmentInteractionListener{

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
        if(deck.getTitle() != null && !deck.getTitle().equals("")) {
            mDbHelper.addDeck(deck);
            replaceFragment(DeckListFragment.newInstance((ArrayList<Deck>) mDbHelper.getAllDecks()));
        } else {
            Toast.makeText(this, getString(R.string.empty_deck_title_warning), Toast.LENGTH_SHORT).show();
        }
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