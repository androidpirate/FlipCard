package com.github.androidpirate.flipcard.data;

import android.provider.BaseColumns;

/**
 * Contract class for deck table.
 */
public final class DeckContract {

    private DeckContract() {
    }

    public static final class DeckEntry implements BaseColumns {
        public static final String TABLE_NAME = "decks";
        public static final String COLUMN_TITLE = "deck_title";
        public static final String COLUMN_CATEGORY = "deck_category";
        public static final String COLUMN_CARDS = "deck_cards";
        public static final String COLUMN_SIZE = "deck_size";
    }
}
