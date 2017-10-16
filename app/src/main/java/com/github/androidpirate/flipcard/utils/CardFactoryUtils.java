/*
 * <!--
 *  Copyright (C) 2016 The Android Open Source Project
 *
 *      Licensed under the Apache License, Version 2.0 (the "License");
 *      you may not use this file except in compliance with the License.
 *      You may obtain a copy of the License at
 *
 *           http://www.apache.org/licenses/LICENSE-2.0
 *
 *      Unless required by applicable law or agreed to in writing, software
 *      distributed under the License is distributed on an "AS IS" BASIS,
 *      WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *      See the License for the specific language governing permissions and
 *      limitations under the License.
 * -->
 */

package com.github.androidpirate.flipcard.utils;

import com.github.androidpirate.flipcard.model.Deck;
import com.github.androidpirate.flipcard.model.FlipCard;

import java.util.ArrayList;

/**
 * Factory class for cards.
 */
public class CardFactoryUtils {
    private static CardFactoryUtils sCardFactoryUtils;
    private Deck mDeck;

    private CardFactoryUtils(String deckName) {
        mDeck = new Deck();
        mDeck.setTitle("Test");
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
        mDeck.setCards(cards);
        mDeck.setSize(cards.size());
    }

    public static CardFactoryUtils getInstance(String deckName) {
        if(sCardFactoryUtils == null) {
            sCardFactoryUtils = new CardFactoryUtils(deckName);
        }
        return sCardFactoryUtils;
    }

    public ArrayList<FlipCard> getCards() {
        return mDeck.getCards();
    }
}
