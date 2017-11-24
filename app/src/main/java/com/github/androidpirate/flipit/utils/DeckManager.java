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
        ArrayList<FlipCard> cards = new ArrayList<>(deck.getCards());
        int deckSize = deck.getSize();
        int queueSize = (int) (deckSize * 0.8f);
        Random randomGenerator = new Random();
        Queue<FlipCard> randomCards = new LinkedList<>();
        for(int i = 0; i < queueSize; i++) {
            int index = randomGenerator.nextInt(deckSize);
            randomCards.add(cards.get(index));
            cards.remove(index);
            deckSize--;
        }
        return randomCards;
    }
}