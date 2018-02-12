package com.github.androidpirate.flipit;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.github.androidpirate.flipit.data.DeckDbHelper;
import com.github.androidpirate.flipit.fragment.EditDeckFragment;
import com.github.androidpirate.flipit.fragment.DeckDetailFragment;
import com.github.androidpirate.flipit.fragment.DeckListFragment;
import com.github.androidpirate.flipit.model.Deck;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity
    implements DeckListFragment.OnFragmentInteractionListener,
                DeckDetailFragment.OnFragmentInteractionListener,
                EditDeckFragment.OnFragmentInteractionListener {
    private ArrayList<Deck> mDecks;
    private DeckDbHelper mDbHelper;

    @Override
    protected void addView() {
        FrameLayout frameLayout = findViewById(R.id.base_container);
        View view = getLayoutInflater().inflate(R.layout.activity_main, null, false);
        frameLayout.addView(view);
        createFragment();
    }

    @Override
    protected void initialize() {
        mDbHelper = DeckDbHelper.newInstance(getApplicationContext());
        mDecks = (ArrayList<Deck>) mDbHelper.getAllDecks();
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
    public void updateDeckStatus(Deck deck) {
        mDbHelper.updateDeck(deck);
        replaceFragment(DeckListFragment.newInstance((ArrayList<Deck>) mDbHelper.getAllDecks()));
    }

    @Override
    public List<Deck> getDecksFromDatabase() {
        return mDbHelper.getAllDecks();
    }

    @Override
    public Deck getDeckFromDatabase(int deckId) {
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

    private void createFragment(){
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, DeckListFragment.newInstance(mDecks))
                .commit();
    }
}