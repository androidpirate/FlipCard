package com.github.androidpirate.flipcard.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.androidpirate.flipcard.R;
import com.github.androidpirate.flipcard.model.Deck;
import com.github.androidpirate.flipcard.model.FlipCard;

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

        private CardHolder(View itemView) {
            super(itemView);
            mFrontText = itemView.findViewById(R.id.tv_front);
            mRearText = itemView.findViewById(R.id.tv_rear);
        }

        void onBindCard(FlipCard card) {
            mCard = card;
            mFrontText.setText(mCard.getFrontSide());
            mRearText.setText(mCard.getRearSide());
        }
    }
}