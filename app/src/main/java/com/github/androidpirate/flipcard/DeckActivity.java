package com.github.androidpirate.flipcard;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.github.androidpirate.flipcard.data.DeckDbHelper;
import com.github.androidpirate.flipcard.fragment.BuildDeckFragment;
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
}
