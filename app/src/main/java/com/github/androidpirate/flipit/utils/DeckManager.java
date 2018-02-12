package com.github.androidpirate.flipit.utils;

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
        ArrayList<FlipCard> visibles = deck.getVisibleCards();
        ArrayList<FlipCard> favorites = deck.getFavoriteCards();
        int deckSize = deck.getSize();
        int queueSize = (int) (deckSize * 0.8f);
        // TODO: Why did I used a LinkedList here ???
        Queue<FlipCard> randomCards = new LinkedList<>();
        // TODO: Change the loop sizes to array sizes above and
        // TODO: create two separate loops to combine the lists
        for(int i = 0; i < queueSize; i++) {
            if(i <= favorites.size()) {
                addRandomFavoriteCards(favorites, randomCards);
            } else if(i <= visibles.size()) {
                addRandomVisibleCards(visibles, randomCards);
            } else {
                if(i < queueSize - 1) {
                    // Break if the size is still lower than
                    // estimated 80%, after adding those two decks
                    break;
                }
            }
        }
        return randomCards;
    }

    private void addRandomFavoriteCards(ArrayList<FlipCard> favorites,
                                        Queue<FlipCard> randomCards) {
        Random randomGenerator = new Random();
        int index = randomGenerator.nextInt(favorites.size());
        randomCards.add(favorites.get(index));
        // Remove the random card from the favorites
        // to prevent adding the same card to practice deck
        favorites.remove(favorites.get(index));
    }

    private void addRandomVisibleCards(ArrayList<FlipCard> visibles,
                                       Queue<FlipCard> randomCards) {
        Random randomGenerator = new Random();
        int index = randomGenerator.nextInt(visibles.size());
        // Remove the random card from the favorites
        // to prevent adding the same card to practice deck
        randomCards.add(visibles.get(index));
        visibles.remove(visibles.get(index));
    }
}