package com.github.androidpirate.flipcard;

import android.support.v4.app.Fragment;

import com.github.androidpirate.flipcard.data.DeckDbHelper;
import com.github.androidpirate.flipcard.fragment.CreateDeckFragment;
import com.github.androidpirate.flipcard.fragment.DeckDetailFragment;
import com.github.androidpirate.flipcard.fragment.DeckListFragment;
import com.github.androidpirate.flipcard.model.Deck;
import com.github.androidpirate.flipcard.utils.DeckFactoryUtils;

import java.util.ArrayList;

public class MainActivity extends SingleFragmentActivity
    implements DeckListFragment.OnFragmentInteractionListener,
                DeckDetailFragment.OnFragmentInteractionListener,
                CreateDeckFragment.OnFragmentInteractionListener {

    private DeckFactoryUtils mDeckFactoryUtils;
    private DeckDbHelper mDbHelper;

    @Override
    protected Fragment createFragment() {
        // Add the fragment instance to show up here
        // buildDeck();
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

    private void getDeckDbHelperInstance() {
        mDbHelper = DeckDbHelper.newInstace(getApplicationContext());
    }

   /** private void buildDeck() {
        mDeckFactoryUtils = new DeckFactoryUtils(DeckDbHelper.newInstace(this));
        Deck deck = new Deck();
        deck.setTitle("Test");
        ArrayList<FlipCard> cards = new ArrayList<>();
        cards.add(new FlipCard("La pizarra", "Blackboard"));
        cards.add(new FlipCard("El borrador", "Eraser"));
        cards.add(new FlipCard("La tiza", "Chalk"));
        cards.add(new FlipCard("La luz", "Light"));
        cards.add(new FlipCard("El calendario", "Calendar"));
        cards.add(new FlipCard("La pantalla", "Screen"));
        cards.add(new FlipCard("El mapa", "Map"));
        cards.add(new FlipCard("El reloj", "Clock"));
        cards.add(new FlipCard("El marcador", "Marker"));
        cards.add(new FlipCard("La computadora", "Computer"));
        deck.setCards(cards);
        deck.setSize(cards.size());
        mDeckFactoryUtils.addDeck(deck);
    } */

    public void showUpButton() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void hideUpButton() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }
}