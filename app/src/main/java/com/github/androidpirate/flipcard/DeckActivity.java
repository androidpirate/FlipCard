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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_deck_list_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_deck:
                // Replace DeckListFragment with CreateDeckFragment here
                FragmentManager fm = getFragmentManager();
                fm.beginTransaction()
                        .replace(R.id.fragment_container, BuildDeckFragment.newInstance())
                        .commit();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
