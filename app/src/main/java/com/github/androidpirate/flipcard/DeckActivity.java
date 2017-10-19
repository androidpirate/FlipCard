package com.github.androidpirate.flipcard;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.github.androidpirate.flipcard.data.DeckDbHelper;
import com.github.androidpirate.flipcard.fragment.DeckListFragment;
import com.github.androidpirate.flipcard.model.Deck;

import java.util.ArrayList;

public class DeckActivity extends SingleFragmentActivity
    implements DeckListFragment.OnFragmentInteractionListener {

    @Override
    protected Fragment createFragment() {
        ArrayList<Deck> decks = (ArrayList<Deck>) DeckDbHelper.newInstace(this).getAllDecks();
        return DeckListFragment.newInstance(decks);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onBackPressed() {
        ArrayList<Deck> decks = (ArrayList<Deck>) DeckDbHelper.newInstace(this).getAllDecks();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, DeckListFragment.newInstance(decks))
                .commit();
    }
}
