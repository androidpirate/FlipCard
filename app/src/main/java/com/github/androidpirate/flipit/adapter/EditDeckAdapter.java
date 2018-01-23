package com.github.androidpirate.flipit.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.androidpirate.flipit.R;
import com.github.androidpirate.flipit.model.Deck;
import com.github.androidpirate.flipit.model.FlipCard;
import com.github.androidpirate.flipit.utils.DeckManager;

import java.util.ArrayList;

/**
 * Adapter class for EditDeckFragment.
 */
public class EditDeckAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final static int DECK = 0;
    private final static int CARD = 1;
    private ArrayList<Object> mItems = new ArrayList<>();
    private Deck mDeck;

    public EditDeckAdapter(Deck deck) {
        if (deck != null) {
            mDeck = deck;
        }
        // If deck is empty add an empty card
        if(deck != null && deck.getSize() == 0) {
            mDeck.addEmptyCard();
        }
        DeckManager deckManager = new DeckManager();
        mItems = deckManager.getEditListItems(mDeck);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case DECK:
                View editableDeckHeader = inflater
                        .inflate(R.layout.editable_deck_header_list_item, parent, false);
                viewHolder = new EditableDeckHeader(editableDeckHeader);
                break;
            case CARD:
                View editableCard = inflater
                        .inflate(R.layout.editable_card_list_item, parent, false);
                viewHolder = new EditableCardHolder(editableCard);
                break;
            default:
                throw new NullPointerException("View holder can not be null.");
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case DECK:
                Deck deck = (Deck) mItems.get(position);
                EditableDeckHeader deckHeader = (EditableDeckHeader) holder;
                deckHeader.onBindDeckInfo(deck);
                break;
            case CARD:
                FlipCard card = (FlipCard) mItems.get(position);
                EditableCardHolder cardHolder = (EditableCardHolder) holder;
                cardHolder.onBindCard(card);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(mItems.get(position) instanceof Deck) {
            return DECK;
        } else if(mItems.get(position) instanceof FlipCard) {
            return CARD;
        } else {
            return -1;
        }
    }

    public void addEmptyCard() {
        mDeck.addEmptyCard();
        mItems.clear();
        mItems.add(mDeck);
        mItems.addAll(mDeck.getCards());
        notifyItemInserted(mItems.size() - 1);
    }

    public void refreshDataSet() {
        mItems.clear();
        mItems.add(mDeck);
        mItems.addAll(mDeck.getCards());
    }

    public Deck getDeck() {
        return mDeck;
    }

    /**
     * Private class creates editable deck header for EditDeckFragment/EditDeckFragment.
     */
    private class EditableDeckHeader extends RecyclerView.ViewHolder {
        private Deck mDeck;
        private EditText mDeckTitle;
        private EditText mCategory;
        private ImageButton mExpandDescription;
        private EditText mDescription;

        private EditableDeckHeader(View itemView) {
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

            mExpandDescription = itemView.findViewById(R.id.bt_expand_description);
            mExpandDescription.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mDescription.getVisibility() != View.VISIBLE) {
                        mExpandDescription.setVisibility(View.GONE);
                        mDescription.setVisibility(View.VISIBLE);
                        mDescription.setText(mDeck.getDescription());
                        notifyDataSetChanged();
                    }
                }
            });

            mDescription = itemView.findViewById(R.id.et_description);
            mDescription.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }
                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }
                @Override
                public void afterTextChanged(Editable editable) {
                    mDeck.setDescription(editable.toString());
                }
            });
        }
    }

    /**
     * Private class creates editable card holders for EditDeckFragment/EditableDeckFragment.
     */
    private class EditableCardHolder extends RecyclerView.ViewHolder {
        private FlipCard mCard;
        private ImageView mButtonUp;
        private ImageView mButtonDown;
        private ImageView mButtonDelete;
        private TextView mCardNumber;
        private EditText mFrontText;
        private EditText mRearText;

        private EditableCardHolder(View itemView) {
            super(itemView);
            setFieldViews();
        }

        private void onBindCard(FlipCard card) {
            mCard = card;
            final Context context = itemView.getContext();
            mCardNumber.setText(String.format(context.getString(R.string.card_number),
                                getAdapterPosition()));
            mFrontText.setText(mCard.getFrontSide());
            mRearText.setText(mCard.getRearSide());
        }

        private void setFieldViews() {
            mButtonUp = itemView.findViewById(R.id.bt_up);
            mButtonUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Since the adapter position holds on to the current position in mItems
                    // and the first item is the deck info
                    if(getAdapterPosition() != 1) {
                        int currentCardPosition = getAdapterPosition() - 1;
                        mDeck.moveCardUp(currentCardPosition, currentCardPosition - 1);
                        // notifyItemMoved(getAdapterPosition(), getAdapterPosition() - 1);
                        refreshDataSet();
                        notifyDataSetChanged();
                    }
                }
            });

            mButtonDown = itemView.findViewById(R.id.bt_down);
            mButtonDown.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Since the adapter position holds on to the current position in mItems
                    // and the first item is the deck info
                    if(getAdapterPosition() != mItems.size() - 1) {
                        int currentCardPosition = getAdapterPosition() - 1;
                        mDeck.moveCardDown(currentCardPosition, currentCardPosition + 1);
                        refreshDataSet();
                        // notifyItemMoved(getAdapterPosition(), getAdapterPosition() + 1);
                        notifyDataSetChanged();
                    }
                }
            });

            mButtonDelete = itemView.findViewById(R.id.bt_delete);
            mButtonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Since the adapter position holds on to the current position in mItems
                    // and the first item is the deck info
                    if(mItems.size() > 1) {
                        int currentCardPosition = getAdapterPosition() - 1;
                        mDeck.deleteCard(currentCardPosition);
                        refreshDataSet();
                        // notifyItemRemoved(getAdapterPosition());
                        notifyDataSetChanged();
                    }
                }
            });

            mCardNumber = itemView.findViewById(R.id.tv_card_number);

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
    }
}