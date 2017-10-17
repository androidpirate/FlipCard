package com.github.androidpirate.flipcard.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Model class for deck.
 */
public class Deck implements Serializable {
    private int mId;
    private String mTitle;
    private ArrayList<FlipCard> mCards;
    private String mCategory;
    private int mSize;

    public Deck() {
        mCards = new ArrayList<>();
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
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

    public String getCategory() {
        return mCategory;
    }

    public void setCategory(String category) {
        mCategory = category;
    }

    public int getSize() {
        return mSize;
    }

    public void setSize(int size) {
        mSize = size;
    }
}