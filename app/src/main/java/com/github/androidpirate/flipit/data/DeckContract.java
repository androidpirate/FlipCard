package com.github.androidpirate.flipit.data;

import android.provider.BaseColumns;

/**
 * Contract class for deck table.
 */
public final class DeckContract {

    private DeckContract() {
    }

    static final class DeckEntry implements BaseColumns {
        static final String TABLE_NAME = "decks";
        static final String COLUMN_TITLE = "deck_title";
        static final String COLUMN_CATEGORY = "deck_category";
        static final String COLUMN_DESCRIPTION = "deck_description";
        static final String COLUMN_CARDS = "deck_cards";
        static final String COLUMN_SIZE = "deck_size";
    }
}