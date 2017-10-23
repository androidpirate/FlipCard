package com.github.androidpirate.flipcard;

import android.support.v4.app.Fragment;

import com.github.androidpirate.flipcard.data.DeckDbHelper;
import com.github.androidpirate.flipcard.fragment.DeckListFragment;
import com.github.androidpirate.flipcard.model.Deck;
import com.github.androidpirate.flipcard.model.FlipCard;
import com.github.androidpirate.flipcard.utils.DeckFactoryUtils;

import java.util.ArrayList;

public class MainActivity extends SingleFragmentActivity
    implements DeckListFragment.OnFragmentInteractionListener {
    private DeckFactoryUtils mDeckFactoryUtils;

    @Override
    protected Fragment createFragment() {
        // Add the fragment instance to show up here
        buildDeck();
        ArrayList<Deck> decks = (ArrayList<Deck>) mDeckFactoryUtils.getDecks();
        return DeckListFragment.newInstance(decks);
    }

    private void buildDeck() {
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
    }
}
