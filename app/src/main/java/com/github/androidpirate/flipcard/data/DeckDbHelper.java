package com.github.androidpirate.flipcard.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.github.androidpirate.flipcard.data.DeckContract.*;

/**
 * Helper class for deck database.
 */
public class DeckDbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "DeckDatabase.db";
    private static final String COMMA_SEPARATOR = ",";
    private static final String SQL_CREATE_TABLE = "CREATE TABLE " + DeckEntry.TABLE_NAME + "(" +
            DeckEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA_SEPARATOR +
            DeckEntry.COLUMN_TITLE + " TEXT NOT NULL" + COMMA_SEPARATOR +
            DeckEntry.COLUMN_CATEGORY + " TEXT" + COMMA_SEPARATOR +
            DeckEntry.COLUMN_CARDS + " TEXT" + COMMA_SEPARATOR +
            DeckEntry.COLUMN_SIZE + " INTEGER" + ");";
    private static final String SQL_DROP_TABLE = "DROP TABLE IF EXISTS " + DeckEntry.TABLE_NAME;
    private static DeckDbHelper singleton = null;

    synchronized static DeckDbHelper newInstace(Context context) {
        if(singleton == null) {
            singleton = new DeckDbHelper(context.getApplicationContext());
        }
        return singleton;
    }

    private DeckDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(SQL_DROP_TABLE);
        onCreate(db);
    }
}
