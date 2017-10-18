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
import java.util.Collections;
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

    public void refresh(Deck deck, ArrayList<FlipCard> cards) {
        mItems.removeAll(mItems);
        mItems.add(deck);
        mItems.addAll(cards);
        notifyDataSetChanged();
    }

    class DeckInfoHolder extends RecyclerView.ViewHolder {
        private Deck mDeck;
        private EditText mDeckTitle;
        private EditText mCategory;

        public DeckInfoHolder(View itemView) {
            super(itemView);
            setFieldViews();
        }

        private void onBindDeckInfo(Deck deck) {
            mDeck = deck;
            mDeckTitle.setText(mDeck.getTitle());
            mCategory.setText(mDeck.getCategory());
        }

        private void setFieldViews() {
            mDeckTitle = itemView.findViewById(R.id.et_title);
            mDeckTitle.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    mDeck.setTitle(editable.toString());
                }
            });

            mCategory = itemView.findViewById(R.id.et_category);
            mCategory.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    mDeck.setCategory(editable.toString());
                }
            });
        }
    }

    class CardHolder extends RecyclerView.ViewHolder {
        private ImageView mButtonUp;
        private ImageView mButtonDown;
        private ImageView mButtonDelete;
        private EditText mFrontText;
        private EditText mRearText;
        private FlipCard mCard;

        public CardHolder(View itemView) {
            super(itemView);
            setFieldViews();
        }

        private void onBindCard(FlipCard card) {
            mCard = card;
            mFrontText.setText(mCard.getFrontSide());
            mRearText.setText(mCard.getRearSide());
        }

        private void setFieldViews(){
            mButtonUp = itemView.findViewById(R.id.bt_up);
            mButtonUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    moveUp(getAdapterPosition(), getAdapterPosition() - 1);
                }
            });

            mButtonDown = itemView.findViewById(R.id.bt_down);
            mButtonDown.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    moveDown(getAdapterPosition(), getAdapterPosition() + 1);
                }
            });

            mButtonDelete = itemView.findViewById(R.id.bt_delete);
            mButtonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deleteCard(getAdapterPosition());
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
                    mCard.setFrontSide(editable.toString());
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
                    mCard.setRearSide(editable.toString());
                }
            });
        }

        private void moveUp(int currentPosition, int previousPosition) {
            if(currentPosition > 1) {
                Collections.swap(mItems, currentPosition, previousPosition);
                notifyDataSetChanged();
            }
        }

        private void moveDown(int currentPosition, int nextPosition) {
            if(nextPosition != mItems.size()) {
                Collections.swap(mItems, currentPosition, nextPosition);
                notifyDataSetChanged();
            }
        }

        private void deleteCard(int cardPosition) {
            mItems.remove(cardPosition);
            notifyDataSetChanged();
        }
    }
}
