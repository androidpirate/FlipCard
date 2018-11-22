package com.github.androidpirate.flipit.utils;

import android.widget.Toast;

import com.github.androidpirate.flipit.model.Deck;
import com.github.androidpirate.flipit.model.FlipCard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

/**
 * Utility class handles deck management.
 */
public class DeckManager {
    private static final int DECK_PINNED = 1;

    public DeckManager() {
    }

    public int sortByCategories(ArrayList<Deck> decks) {
        Collections.sort(decks, new Comparator<Deck>() {
            @Override
            public int compare(Deck deck, Deck t1) {
                return deck.getCategory().compareToIgnoreCase(t1.getCategory());
            }
        });
        return 0;
    }

    public ArrayList<Deck> sortByStatus(ArrayList<Deck> decks) {
        ArrayList<Deck> pinnedDecks = new ArrayList<>();
        ArrayList<Deck> regularDecks = new ArrayList<>();
        for(Deck deck: decks) {
            if(deck.getIsPinned() == DECK_PINNED) {
                pinnedDecks.add(deck);
            } else {
                regularDecks.add(deck);
            }
        }
        // Sort both decks by category
        sortByCategories(pinnedDecks);
        sortByCategories(regularDecks);
        // Rearrange decks
        decks.clear();
        decks.addAll(pinnedDecks);
        decks.addAll(regularDecks);
        return decks;
    }

    public ArrayList<Object> getListItems(ArrayList<Deck> decks) {
        ArrayList<Object> items = new ArrayList<>();
        int deckIndex = 0;
        String currentCategory = "";
        while(deckIndex < decks.size()) {
            if(deckIndex == 0) {
                currentCategory = decks.get(deckIndex).getCategory();
                items.add(currentCategory);
            }

            if(currentCategory.equals(decks.get(deckIndex).getCategory())) {
                items.add(decks.get(deckIndex));
                deckIndex++;
            } else {
                currentCategory = decks.get(deckIndex).getCategory();
                items.add(currentCategory);
            }
        }
        return items;
    }

    public ArrayList<Object> getEditListItems(Deck deck) {
        ArrayList<Object> items = new ArrayList<>();
        int itemIndex = 0;
        while (itemIndex < deck.getSize()) {
            if(itemIndex == 0) {
                items.add(deck);
            }
            items.add(deck.getCards().get(itemIndex));
            itemIndex++;
        }
        return items;
    }

    public Queue<FlipCard> getRandomCards(Deck deck) {
        ArrayList<FlipCard> visibleCards = deck.getVisibleCards();
        ArrayList<FlipCard> favoriteCards = deck.getFavoriteCards();
        int deckSize = deck.getSize();
        int queueSize = (int) (deckSize * 0.8f);
        if(queueSize == 0) {
            queueSize = 1;
        }
        ArrayList<FlipCard> tempDeck = new ArrayList<>();

        if(visibleCards.size() >= queueSize) {
            addRandomVisibleCards(visibleCards, tempDeck, queueSize);
        }

        // Shuffle deck
        Collections.shuffle(tempDeck);
        // Create a queue from the array
        Queue<FlipCard> randomCards = new LinkedList<>();
        randomCards.addAll(tempDeck);
        return randomCards;
    }

    private void addRandomFavoriteCards(ArrayList<FlipCard> favorites,
                                        ArrayList<FlipCard> randomDeck,
                                        int cardsToAdd) {
        // Shuffle favorites first
        Collections.shuffle(favorites);
        for(int i = 0; i < cardsToAdd; i++) {
            randomDeck.add(favorites.get(i));
        }
    }

    private void addRandomVisibleCards(ArrayList<FlipCard> visibles,
                                       ArrayList<FlipCard> randomDeck,
                                       int cardsToAdd) {
        // Shuffle visibles first
        Collections.shuffle(visibles);
        for(int i = 0; i < cardsToAdd; i++) {
            randomDeck.add(visibles.get(i));
        }
    }
}