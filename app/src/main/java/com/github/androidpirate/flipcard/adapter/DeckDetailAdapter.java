package com.github.androidpirate.flipcard.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.androidpirate.flipcard.R;
import com.github.androidpirate.flipcard.fragment.DeckDetailFragment;
import com.github.androidpirate.flipcard.fragment.EditDeckFragment;
import com.github.androidpirate.flipcard.model.Deck;
import com.github.androidpirate.flipcard.model.FlipCard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Adapter class used to create views to create a heterogeneous layout for RecyclerView.
 */
public class DeckDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final static int DECK = 0;
    private final static int CARD = 1;
    private final static String DECK_DETAIL_FRAGMENT = DeckDetailFragment.class.getSimpleName();
    private final static String EDIT_DECK_FRAGMENT = EditDeckFragment.class.getSimpleName();
    private final String mFragment;
    private List<Object> mItems = new ArrayList<>();
    private DeckDetailAdapter.OnAdapterInteractionListener mListener;

    public interface OnAdapterInteractionListener {
        void onEditIconClick(Deck deck);
    }

    public DeckDetailAdapter(DeckDetailAdapter.OnAdapterInteractionListener listener,
                             Deck deck, String parentFragment) {
        mListener = listener;
        mItems.add(deck);
        mItems.addAll(deck.getCards());
        mFragment = parentFragment;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if(mFragment.equals(DECK_DETAIL_FRAGMENT)) {
            switch (viewType) {
                case DECK:
                    View deckHeader = inflater.inflate(R.layout.deck_header_list_item, parent, false);
                    viewHolder = new DeckHeader(deckHeader);
                    break;
                case CARD:
                    View cardView = inflater.inflate(R.layout.card_list_item, parent, false);
                    viewHolder = new CardHolder(cardView);
                    break;
                default:
                    View genericView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
                    viewHolder = new EditableCardHolder(genericView);
                    break;
            }
        } else if(mFragment.equals(EDIT_DECK_FRAGMENT)) {
            switch (viewType) {
                case DECK:
                    View deckHeader = inflater.inflate(R.layout.editable_deck_header_list_item, parent, false);
                    viewHolder = new EditableDeckHeader(deckHeader);
                    break;
                case CARD:
                    View cardView = inflater.inflate(R.layout.editable_card_list_item, parent, false);
                    viewHolder = new EditableCardHolder(cardView);
                    break;
                default:
                    View genericView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
                    viewHolder = new EditableCardHolder(genericView);
                    break;
            }
        } else {
            throw new NullPointerException("DeckDetailAdapter - ViewHolder can not be empty.");
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(mFragment.equals(DECK_DETAIL_FRAGMENT)) {
            switch (holder.getItemViewType()) {
                case DECK:
                    Deck deck = (Deck) mItems.get(position);
                    DeckHeader deckHeaderHolder = (DeckHeader) holder;
                    deckHeaderHolder.onBindDeckHeader(deck);
                    break;
                case CARD:
                    FlipCard card = (FlipCard) mItems.get(position);
                    CardHolder cardHolder = (CardHolder) holder;
                    cardHolder.onBindCard(card);
                    break;
            }
        } else if(mFragment.equals(EDIT_DECK_FRAGMENT)) {
            switch (holder.getItemViewType()) {
                case DECK:
                    Deck deck = (Deck) mItems.get(position);
                    EditableDeckHeader editableHeaderHolder = (EditableDeckHeader) holder;
                    editableHeaderHolder.onBindDeckInfo(deck);
                    break;
                case CARD:
                    FlipCard card = (FlipCard) mItems.get(position);
                    EditableCardHolder editableCardHolder = (EditableCardHolder) holder;
                    editableCardHolder.onBindCard(card);
                    break;
            }
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
        mItems.clear();
        mItems.add(deck);
        mItems.addAll(cards);
        notifyDataSetChanged();
    }

    public Deck getDeck() {
        return (Deck) mItems.get(DECK);
    }

    /**
     * Private class creates deck header for DeckDetailFragment.
     */
    private class DeckHeader extends RecyclerView.ViewHolder {
        private Deck mDeck;
        private TextView mTitle;
        private TextView mCategory;
        private TextView mSize;
        private ImageView mEdit;

        private DeckHeader(View itemView) {
            super(itemView);
            setFieldViews();
        }

        public void onBindDeckHeader(Deck deck) {
            mDeck = deck;
            mTitle.setText(mDeck.getTitle());
            mCategory.setText(mDeck.getCategory());
            mSize.setText(String.valueOf(mDeck.getSize()));
        }

        private void setFieldViews() {
            mTitle = itemView.findViewById(R.id.tv_deck_title);
            mCategory = itemView.findViewById(R.id.tv_deck_category);
            mSize = itemView.findViewById(R.id.tv_deck_size);
            mEdit = itemView.findViewById(R.id.iv_edit);
            mEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Start EditDeckFragment here
                    mListener.onEditIconClick(mDeck);
                }
            });
        }
    }

    /**
     * Private class creates editable deck header for CreateDeckFragment/EditDeckFragment.
     */
    private class EditableDeckHeader extends RecyclerView.ViewHolder {
        private Deck mDeck;
        private EditText mDeckTitle;
        private EditText mCategory;

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
        }
    }

    /**
     * Private class creates card holders for DetailDeckFragment.
     */
    private class CardHolder extends RecyclerView.ViewHolder {
        private FlipCard mCard;
        private TextView mFrontText;
        private TextView mRearText;

        private CardHolder(View itemView) {
            super(itemView);
            setFieldViews();
        }

        public void onBindCard(FlipCard card) {
            mCard = card;
            mFrontText.setText(mCard.getFrontSide());
            mRearText.setText(mCard.getRearSide());
        }

        private void setFieldViews() {
            mFrontText = itemView.findViewById(R.id.tv_front);
            mRearText = itemView.findViewById(R.id.tv_rear);
        }
    }

    /**
     * Private class creates editable card holders for CreateDeckFragment/EditableDeckFragment.
     */
    private class EditableCardHolder extends RecyclerView.ViewHolder {
        private FlipCard mCard;
        private ImageView mButtonUp;
        private ImageView mButtonDown;
        private ImageView mButtonDelete;
        private EditText mFrontText;
        private EditText mRearText;

        private EditableCardHolder(View itemView) {
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
