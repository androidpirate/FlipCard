package com.github.androidpirate.flipit.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.github.androidpirate.flipit.R;
import com.github.androidpirate.flipit.model.Deck;
import com.github.androidpirate.flipit.model.FlipCard;

import java.util.ArrayList;

/**
 * Adapter class used to create views to create a heterogeneous layout for RecyclerView.
 */
public class DeckDetailAdapter extends RecyclerView.Adapter<DeckDetailAdapter.CardHolder> {
    private static final int CARD_POSITION_CONSTANT = 1;
    private ArrayList<FlipCard> mCards = new ArrayList<>();
    private OnAdapterInteractionListener mListener;

    public interface OnAdapterInteractionListener {
        void onVisibilityChanged(ArrayList<FlipCard> cards);
        void onFavoriteChanged(ArrayList<FlipCard> cards);
    }

    public DeckDetailAdapter(DeckDetailAdapter.OnAdapterInteractionListener listener, Deck deck) {
        mListener = listener;
        mCards = deck.getCards();
    }

    @Override
    public DeckDetailAdapter.CardHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_list_item, parent, false);
        return new CardHolder(view);
    }

    @Override
    public void onBindViewHolder(DeckDetailAdapter.CardHolder holder, int position) {
        FlipCard card = mCards.get(position);
        holder.onBindCard(card);
    }

    @Override
    public int getItemCount() {
        return mCards.size();
    }

    /**
     * Private class creates card holders for DetailDeckFragment.
     */
    class CardHolder extends RecyclerView.ViewHolder {
        private FlipCard mCard;
        private TextView mCardNumber;
        private TextView mFrontText;
        private TextView mRearText;
        private ImageView mVisible;
        private ImageView mFavorite;
        private ImageButton mOverflow;

        private CardHolder(View itemView) {
            super(itemView);
            mCardNumber = itemView.findViewById(R.id.tv_card_number);
            mFrontText = itemView.findViewById(R.id.tv_front);
            mRearText = itemView.findViewById(R.id.tv_rear);
            mOverflow = itemView.findViewById(R.id.ib_overflow);
            mVisible = itemView.findViewById(R.id.iv_visible_icon);
            mFavorite = itemView.findViewById(R.id.iv_favorite_icon);
        }

        void onBindCard(FlipCard card) {
            mCard = card;
            final Context context = itemView.getContext();
            mCardNumber.setText(String.format(context.getString(R.string.card_number),
                    getAdapterPosition() + CARD_POSITION_CONSTANT));
            mFrontText.setText(mCard.getFrontSide());
            mRearText.setText(mCard.getRearSide());
            setVisibility();
            setFavorite();
            mOverflow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showPopUpMenu(mOverflow);
                }
            });
        }

        private void showPopUpMenu(View view) {
            final PopupMenu popup = new PopupMenu(view.getContext(), view);
            MenuInflater inflater = popup.getMenuInflater();
            inflater.inflate(R.menu.card_menu, popup.getMenu());
            setMenuItems(popup);
            popup.show();
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.display_card:
                            mCard.setVisible(true);
                            setVisibility();
                            mListener.onVisibilityChanged(mCards);
                            setMenuItems(popup);
                            return true;
                        case R.id.hide_card:
                            mCard.setVisible(false);
                            setVisibility();
                            mListener.onVisibilityChanged(mCards);
                            setMenuItems(popup);
                            return true;
                        case R.id.favorite_card:
                            mCard.setFavorite(true);
                            setFavorite();
                            mListener.onFavoriteChanged(mCards);
                            return true;
                        case R.id.unfavorite_card:
                            mCard.setFavorite(false);
                            setFavorite();
                            mListener.onFavoriteChanged(mCards);
                            return true;
                    }
                    return false;
                }
            });
        }

        private void setVisibility() {
            if(mCard.isVisible()) {
                mVisible.setImageResource(R.drawable.ic_visible);
            } else {
                mVisible.setImageResource(R.drawable.ic_hidden);
            }
        }

        private void setFavorite() {
            if(mCard.isFavorite()) {
                mFavorite.setVisibility(View.VISIBLE);
            } else {
                mFavorite.setVisibility(View.GONE);
            }
        }

        private void setMenuItems(PopupMenu popup) {
            if(mCard.isVisible()) {
                MenuItem display = popup.getMenu().findItem(R.id.display_card);
                display.setVisible(false);
            } else {
                MenuItem hidden = popup.getMenu().findItem(R.id.hide_card);
                hidden.setVisible(false);
            }

            if(mCard.isFavorite()) {
                MenuItem favorite = popup.getMenu().findItem(R.id.favorite_card);
                favorite.setVisible(false);
            } else {
                MenuItem unfavorite = popup.getMenu().findItem(R.id.unfavorite_card);
                unfavorite.setVisible(false);
            }
        }
    }
}