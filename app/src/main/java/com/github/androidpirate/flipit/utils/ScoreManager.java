package com.github.androidpirate.flipit.utils;

/**
 * Utility class handles score management.
 */
public class ScoreManager {
    private static final int THREE_IN_A_ROW = 3;
    private static final int FOUR_IN_A_ROW = 4;
    private static final int FIVE_IN_A_ROW = 5;
    private int mDeckSize;
    private int mScore;
    private int mBonus;
    private int mCorrectAnswerInARow;

    public ScoreManager(int deckSize) {
        mDeckSize = deckSize;
        mScore = 0;
        mBonus = 0;
        mCorrectAnswerInARow = 0;
    }

    public int getDeckSize() {
        return mDeckSize;
    }

    public int getScore() {
        return mScore;
    }

    public int getBonus() {
        return mBonus;
    }

    public void increaseScore() {
        mCorrectAnswerInARow++;
        mScore++;
        if(mCorrectAnswerInARow > 2) {
            calculateBonus();
        }
    }

    public float getPercentageScore() {
        return ((float)mScore / (float)mDeckSize) * 100;
    }

    public float getPercentBonus() {
        int maxBonus = 0;
        int remainder = mDeckSize % 5;
        switch (remainder) {
            case 0:
                maxBonus = (mDeckSize / 5) * 3;
                break;
            case 3:
                maxBonus = (mDeckSize / 5) * 3 + 1;
                break;
            case 4:
                maxBonus = (mDeckSize / 5) * 3 + 2;
                break;
        }
        return ((float) mBonus / (float) maxBonus) * 100;
    }

    private void calculateBonus() {
        if(mCorrectAnswerInARow == THREE_IN_A_ROW) {
            mBonus++;
        } else if(mCorrectAnswerInARow == FOUR_IN_A_ROW) {
            mBonus++;
        } else if(mCorrectAnswerInARow == FIVE_IN_A_ROW) {
            mBonus++;
            resetCounter();
        }
    }

    private void resetCounter() {
        mCorrectAnswerInARow = 0;
    }
}
