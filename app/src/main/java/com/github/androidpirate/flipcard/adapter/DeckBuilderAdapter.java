package com.github.androidpirate.flipcard.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.github.androidpirate.flipcard.R;
import com.github.androidpirate.flipcard.model.Deck;
import com.github.androidpirate.flipcard.model.FlipCard;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter class for card list.
 */
public class DeckBuilderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final static int DECK = 0;
    private final static int CARD = 1;
    private List<Object> mItems = new ArrayList<>();

    public DeckBuilderAdapter(Deck deck, ArrayList<FlipCard> cards) {
        mItems.add(deck);
        mItems.addAll(cards);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case DECK:
                View deckView = inflater.inflate(R.layout.deck_info_list_item, parent, false);
                viewHolder = new DeckInfoHolder(deckView);
                break;
            case CARD:
                View emptyCardView = inflater.inflate(R.layout.empty_card_list_item, parent, false);
                viewHolder = new CardHolder(emptyCardView);
                break;
            default:
                View genericView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
                viewHolder = new CardHolder(genericView);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case DECK:
                Deck deck = (Deck) mItems.get(position);
                DeckInfoHolder deckInfoHolder = (DeckInfoHolder) holder;
                deckInfoHolder.onBindDeckInfo(deck);
                break;
            case CARD:
                FlipCard card = (FlipCard) mItems.get(position);
                CardHolder cardHolder = (CardHolder) holder;
                cardHolder.onBindCard(card);
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mItems.get(position) instanceof Deck) {
            return DECK;
        } else if (mItems.get(position) instanceof FlipCard) {
            return CARD;
        }
        return -1;
    }

    class DeckInfoHolder extends RecyclerView.ViewHolder {
        private EditText mDeckTitle;
        private EditText mCategory;

        public DeckInfoHolder(View itemView) {
            super(itemView);
            mDeckTitle = itemView.findViewById(R.id.et_title);
            mCategory = itemView.findViewById(R.id.et_category);
        }

        private void onBindDeckInfo(Deck deck) {
            mDeckTitle.setText(deck.getTitle());
            mCategory.setText(deck.getCategory());
        }
    }

    class CardHolder extends RecyclerView.ViewHolder {
        private ImageView mButtonUp;
        private ImageView mButtonDown;
        private ImageView mButtonDelete;
        private EditText mFrontText;
        private EditText mRearText;

        public CardHolder(View itemView) {
            super(itemView);
            mButtonUp = itemView.findViewById(R.id.bt_up);
            mButtonUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

            mButtonDown = itemView.findViewById(R.id.bt_down);
            mButtonDown.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

            mButtonDelete = itemView.findViewById(R.id.bt_delete);
            mButtonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

            mFrontText = itemView.findViewById(R.id.et_front);
            mFrontText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

            mRearText = itemView.findViewById(R.id.et_rear);
            mRearText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        }

        private void onBindCard(FlipCard card) {
            mFrontText.setText(card.getFrontSide());
            mRearText.setText(card.getRearSide());
        }
    }
}
