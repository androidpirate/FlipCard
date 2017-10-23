package com.github.androidpirate.flipcard.utils;

import com.github.androidpirate.flipcard.data.DeckDbHelper;
import com.github.androidpirate.flipcard.model.Deck;

import java.util.List;

/**
 * Created by Emre on 10/22/2017.
 */
public class DeckFactoryUtils {
    private DeckDbHelper mDbHelper;

    public DeckFactoryUtils(DeckDbHelper dbHelper) {
        mDbHelper = dbHelper;
    }

    public List<Deck> getDecks() {
        return mDbHelper.getAllDecks();
    }

    public void addDeck(Deck deck) {
        mDbHelper.addDeck(deck);
    }

    public void deleteDeck(int deckId) {
        mDbHelper.deleteDeck(deckId);
    }
}
