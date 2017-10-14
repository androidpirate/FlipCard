package com.github.androidpirate.flipcard.model;

import java.util.ArrayList;

/**
 * Created by KF1500 on 10/14/17.
 */
public class Deck {
    private String mTitle;
    private ArrayList<FlipCard> mCards;

    public Deck(String title) {
        mTitle = title;
        mCards = new ArrayList<>();
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public ArrayList<FlipCard> getCards() {
        return mCards;
    }

    public void setCards(ArrayList<FlipCard> cards) {
        mCards = cards;
    }
}