package com.github.androidpirate.flipit.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Model class for deck.
 */
public class Deck implements Serializable {
    private static final int DECK_NOT_PINNED = 0;
    private int mId;
    private String mTitle;
    private ArrayList<FlipCard> mCards;
    private String mCategory;
    private String mDescription;
    private int mIsPinned;
    private int mSize;

    public Deck() {
        mCards = new ArrayList<>();
        mIsPinned = DECK_NOT_PINNED;
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

    public ArrayList<FlipCard> getVisibleCards() {
        ArrayList<FlipCard> visibleCards = new ArrayList<>();
        for(FlipCard card: mCards) {
            if(card.isVisible() && !card.isFavorite()) {
                visibleCards.add(card);
            }
        }
        return visibleCards;
    }

    public ArrayList<FlipCard> getFavoriteCards() {
        ArrayList<FlipCard> favoriteCards = new ArrayList<>();
        for (FlipCard card: mCards) {
            if(card.isVisible() && card.isFavorite()) {
                favoriteCards.add(card);
            }
        }
        return favoriteCards;
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

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public int getIsPinned() {
        return mIsPinned;
    }

    public void setIsPinned(int isPinned) {
        mIsPinned = isPinned;
    }

    public int getSize() {
        return mSize;
    }

    public void setSize(int size) {
        mSize = size;
    }

    public void addEmptyCard() {
        mCards.add(new FlipCard("", ""));
        mSize++;
    }

    public void deleteCard(int cardPosition) {
        mCards.remove(cardPosition);
        mSize--;
    }

    public void moveCardUp(int currentPosition, int previousPosition) {
        swap(currentPosition, previousPosition);
    }

    public void moveCardDown(int currentPosition, int nextPosition) {
        swap(currentPosition, nextPosition);
    }

    private void swap(int firstPosition, int secondPosition) {
        FlipCard card1 = mCards.get(firstPosition);
        FlipCard card2 = mCards.get(secondPosition);
        mCards.set(secondPosition, card1);
        mCards.set(firstPosition, card2);
    }
}