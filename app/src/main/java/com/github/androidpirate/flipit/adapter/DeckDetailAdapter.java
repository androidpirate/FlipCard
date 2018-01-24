package com.github.androidpirate.flipit.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toolbar;

import com.github.androidpirate.flipit.R;
import com.github.androidpirate.flipit.model.Deck;
import com.github.androidpirate.flipit.model.FlipCard;
import com.github.androidpirate.flipit.utils.CardMenuItemClickListener;

import java.util.ArrayList;

/**
 * Adapter class used to create views to create a heterogeneous layout for RecyclerView.
 */
public class DeckDetailAdapter extends RecyclerView.Adapter<DeckDetailAdapter.CardHolder> {
    private ArrayList<FlipCard> mCards = new ArrayList<>();

    public interface OnAdapterInteractionListener {
    }

    public DeckDetailAdapter(DeckDetailAdapter.OnAdapterInteractionListener listener, Deck deck) {
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
        private TextView mFrontText;
        private TextView mRearText;
        private ImageButton mOverflow;

        private CardHolder(View itemView) {
            super(itemView);
            mFrontText = itemView.findViewById(R.id.tv_front);
            mRearText = itemView.findViewById(R.id.tv_rear);
            mOverflow = itemView.findViewById(R.id.ib_overflow);
        }

        void onBindCard(FlipCard card) {
            mCard = card;
            mFrontText.setText(mCard.getFrontSide());
            mRearText.setText(mCard.getRearSide());
            mOverflow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showPopUpMenu(mOverflow, getAdapterPosition());
                }
            });
        }

        private void showPopUpMenu(View view, int position) {
            PopupMenu popup = new PopupMenu(view.getContext(), view);
            MenuInflater inflater = popup.getMenuInflater();
            inflater.inflate(R.menu.card_menu, popup.getMenu());
            popup.setOnMenuItemClickListener(new CardMenuItemClickListener(position));
            popup.show();
        }
    }
}