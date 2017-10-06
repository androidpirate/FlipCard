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

import com.github.androidpirate.flipcard.model.FlipCard;

import java.util.ArrayList;

/**
 * Factory class for cards.
 */
public class CardFactoryUtils {
    private static CardFactoryUtils sCardFactoryUtils;
    private final ArrayList<FlipCard> mCards = new ArrayList<>();

    private CardFactoryUtils() {
        mCards.add(new FlipCard("La pizarra", "Blackboard"));
        mCards.add(new FlipCard("El borrador", "Eraser"));
        mCards.add(new FlipCard("La tiza", "Chalk"));
        mCards.add(new FlipCard("La luz", "Light"));
        mCards.add(new FlipCard("El calendario", "Calender"));
        mCards.add(new FlipCard("La pantalla", "Screen"));
        mCards.add(new FlipCard("El mapa", "Map"));
        mCards.add(new FlipCard("El reloj", "Clock"));
        mCards.add(new FlipCard("El marcador", "Marker"));
        mCards.add(new FlipCard("La computadora", "Computer"));
    }

    public static CardFactoryUtils getInstance() {
        if(sCardFactoryUtils == null) {
            sCardFactoryUtils = new CardFactoryUtils();
        }
        return sCardFactoryUtils;
    }

    public ArrayList<FlipCard> getCards() {
        return mCards;
    }
}
