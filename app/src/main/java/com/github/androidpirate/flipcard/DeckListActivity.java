package com.github.androidpirate.flipcard;

import android.app.Fragment;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class DeckListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_deck_list_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_deck:
                // Replace DeckListFragment with CreateDeckFragment here
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
