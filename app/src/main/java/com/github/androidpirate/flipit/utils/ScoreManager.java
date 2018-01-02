package com.github.androidpirate.flipit.utils;

/**
 * Utility class handles score management.
 */
public class ScoreManager {
    private static final int THREE_IN_A_ROW = 3;
    private static final int FOUR_IN_A_ROW = 4;
    private static final int FIVE_IN_A_ROW = 5;
    private int mScore;
    private int mBonus;
    private int mCorrectAnswerInARow;

    public ScoreManager() {
        mScore = 0;
        mBonus = 0;
        mCorrectAnswerInARow = 0;
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
