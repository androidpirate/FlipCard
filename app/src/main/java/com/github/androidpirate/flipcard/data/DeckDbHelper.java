package com.github.androidpirate.flipcard.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.github.androidpirate.flipcard.model.Deck;
import com.github.androidpirate.flipcard.model.FlipCard;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

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

    public List<Deck> getAllDecks() {
        List<Deck> decks = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(DeckEntry.TABLE_NAME, null, null, null, null, null, null);
        try {
            if (cursor.moveToNext()) {
                do {
                    int _id = cursor.getInt(cursor.getColumnIndex(DeckEntry._ID));
                    String title = cursor.getString(cursor.getColumnIndex(DeckEntry.COLUMN_TITLE));
                    String queryCards = cursor.getString(cursor.getColumnIndex(DeckEntry.COLUMN_CARDS));
                    String category = cursor.getString(cursor.getColumnIndex(DeckEntry.COLUMN_CATEGORY));
                    int size = cursor.getInt(cursor.getColumnIndex(DeckEntry.COLUMN_SIZE));
                    Deck deck = new Deck();
                    deck.setId(_id);
                    deck.setTitle(title);
                    Gson gson = new Gson();
                    Type flipCard = new TypeToken<ArrayList<FlipCard>>() {}.getType();
                    ArrayList<FlipCard> cards = gson.fromJson(queryCards, flipCard);
                    deck.setCards(cards);
                    deck.setCategory(category);
                    deck.setSize(size);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d("Error DeckDb GetAll: ", e.getMessage());
        }finally {
            if(cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return decks;
    }

    public void addDeck(Deck deck) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues cv = new ContentValues();
            cv.put(DeckEntry.COLUMN_TITLE, deck.getTitle());
            Gson gson = new Gson();
            String cards = gson.toJson(deck.getCards());
            cv.put(DeckEntry.COLUMN_CARDS, cards);
            cv.put(DeckEntry.COLUMN_CATEGORY, deck.getCategory());
            cv.put(DeckEntry.COLUMN_SIZE, deck.getSize());
            db.insert(DeckEntry.TABLE_NAME, null, cv);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d("Error DeckDb Insert: ", e.getMessage());
        } finally {
            db.endTransaction();
        }
    }

    public int deleteDeck(int deckId) {
        SQLiteDatabase db = getWritableDatabase();
        String where = DeckEntry._ID + " = ?";
        String[] whereArgs = new String[]{ String.valueOf(deckId) };
        return db.delete(DeckEntry.TABLE_NAME, where, whereArgs);
    }

    public int updateDeck(Deck deck) {
        String where = DeckEntry._ID + " = ?";
        String[] whereArgs = new String[] { String.valueOf(deck.getId()) };
        ContentValues cv = new ContentValues();
        cv.put(DeckEntry.COLUMN_TITLE, deck.getTitle());
        Gson gson = new Gson();
        String cards = gson.toJson(deck.getCards());
        cv.put(DeckEntry.COLUMN_CARDS, cards);
        cv.put(DeckEntry.COLUMN_CATEGORY, deck.getCategory());
        cv.put(DeckEntry.COLUMN_SIZE, deck.getSize());
        SQLiteDatabase db = getWritableDatabase();
        return db.update(DeckEntry.TABLE_NAME, cv, where, whereArgs);
    }
}
