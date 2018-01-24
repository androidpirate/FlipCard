package com.github.androidpirate.flipit.utils;

import android.view.MenuItem;
import android.widget.PopupMenu;

import com.github.androidpirate.flipit.R;

/**
 * Created by Emre on 1/24/2018.
 */
public class CardMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {
    private int position;

    public CardMenuItemClickListener(int position) {
        this.position = position;
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.hide_card:
                return true;
            case R.id.display_card:
                return true;
            case R.id.favorite_card:
                return true;
        }
        return false;
    }
}
